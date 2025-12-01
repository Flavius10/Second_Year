CREATE OR ALTER PROCEDURE sp_Transformare_1N_in_N1
	@Tabel1 NVARCHAR (128),--tabela care e 1 in relatie
	@TabelN NVARCHAR (128) -- tabela care e N in relatie
AS
BEGIN
	
	--Nu mai pune 1 row affected in output
	SET NOCOUNT ON;

	DECLARE @SQL NVARCHAR (MAX);

	DECLARE @ColoanaPK_T1 NVARCHAR (128);
	DECLARE @ColoanaPK_TN NVARCHAR (128);
	DECLARE @ColoanaFK_Veche NVARCHAR(128);
	DECLARE @NumeConstrangereFK NVARCHAR (128);
	DECLARE @TipDatePK_T1 NVARCHAR (128);
	DECLARE @TipDatePK_TN NVARCHAR (128);

	--luam top 1 pentru ca cheia primara poate fi compusa din mai multe 
	-- chei primare si sa nu crape
	-- LOGICA: Informatia despre PK este sparta in 3 tabele de sistem care trebuie legate (JOIN):
    -- 1. sys.indexes: Gaseste ID-ul indexului care este marcat ca PRIMARY KEY (is_primary_key=1).
    -- 2. sys.index_columns: Face legatura (podul) intre Index_ID si Column_ID.
    -- 3. sys.columns: Traduce Column_ID (numar) in Numele Coloanei (text, ex: 'id_cladire').
    -- Filtrul OBJECT_ID(@Tabel1) transforma numele tabelului tau in ID-ul intern folosit de SQL.
	-- index_id e indexul din lista(adica e cea tabela principala, cea sortata dupa id)
	SELECT TOP 1 @ColoanaPK_T1 = c.name
    FROM sys.indexes i
    INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
    INNER JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
    WHERE i.is_primary_key = 1 
      AND i.object_id = OBJECT_ID(@Tabel1);

	--cautam numele si tipul coloanei primary key pentru tabela 1 
	--si vedem ce tip e mai exact, si daca e dintre dinastea de scris
	--ne punem si ii adaugam si lungimea (ex: NVARCHAR(50))
    SELECT TOP 1 
        @ColoanaPK_TN = c.name,
        @TipDatePK_TN = t.name + 
            CASE 
                WHEN t.name IN ('char', 'varchar', 'nchar', 'nvarchar') 
                THEN '(' + CAST(c.max_length AS VARCHAR) + ')' 
                ELSE '' 
            END
    FROM sys.indexes i
    INNER JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
    INNER JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
    INNER JOIN sys.types t ON c.user_type_id = t.user_type_id
    WHERE i.is_primary_key = 1 
      AND i.object_id = OBJECT_ID(@TabelN);

     -- Aflam Cheia Straina existenta
     SELECT TOP 1 
        @ColoanaFK_Veche = c.name,
        @NumeConstrangereFK = fk.name
    FROM sys.foreign_keys fk
    INNER JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
    INNER JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
    WHERE fk.parent_object_id = OBJECT_ID(@TabelN)       -- FK este in Tabelul N
      AND fk.referenced_object_id = OBJECT_ID(@Tabel1);  -- Si arata spre Tabelul 1


    -- Verificare
    IF @ColoanaFK_Veche IS NULL
    BEGIN
        PRINT 'Eroare: Nu s-a gasit relatia FK intre ' + @TabelN + ' si ' + @Tabel1;
        RETURN;
    END

    PRINT 'Metadate gasite: PK_T1=' + @ColoanaPK_T1 + ', FK_Veche=' + @ColoanaFK_Veche + ', PK_TN=' + @ColoanaPK_TN;

    -- punem coloana noua in Tabelul 1(cea care are id-ul tabelului
    -- nou)
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@Tabel1) + ' ADD New_' + @ColoanaPK_TN + ' ' + @TipDatePK_TN;
    EXEC sp_executesql @SQL;

    -- gasim id-ul maxim din T1 pentru fiecare TN
    SET @SQL = 'UPDATE T1
                SET New_' + @ColoanaPK_TN + ' = (
                    SELECT MAX(' + @ColoanaPK_TN + ')
                    FROM ' + QUOTENAME(@TabelN) + ' TN
                    WHERE TN.' + @ColoanaFK_Veche + ' = T1.' + @ColoanaPK_T1 + '
                )
                FROM ' + QUOTENAME(@Tabel1) + ' T1';
    EXEC sp_executesql @SQL;

    -- salvam in Log (Istoric) datele care se pierd
    SET @SQL = 'INSERT INTO Legaturi_Eliminate (NumeTabelSt, IdSt, NumeTabelDr, IdDr, Mesaj)
                SELECT ''' + @Tabel1 + ''', T1.' + @ColoanaPK_T1 + ', ''' + @TabelN + ''', TN.' + @ColoanaPK_TN + ', ''Relatie stearsa 1:N -> N:1''
                FROM ' + QUOTENAME(@Tabel1) + ' T1
                JOIN ' + QUOTENAME(@TabelN) + ' TN ON T1.' + @ColoanaPK_T1 + ' = TN.' + @ColoanaFK_Veche + '
                WHERE TN.' + @ColoanaPK_TN + ' <> ISNULL(T1.New_' + @ColoanaPK_TN + ', -1)';
    EXEC sp_executesql @SQL;

    -- stergem vechea relatie din Tabelul N (Camera)
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TabelN) + ' DROP CONSTRAINT ' + QUOTENAME(@NumeConstrangereFK);
    EXEC sp_executesql @SQL;

    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TabelN) + ' DROP COLUMN ' + QUOTENAME(@ColoanaFK_Veche);
    EXEC sp_executesql @SQL;

    -- cream noua constrangere FK in Tabelul 1 (Cladire)
    DECLARE @NumeFKNou NVARCHAR(128) = 'FK_' + @Tabel1 + '_' + @TabelN + '_NEW';
    
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@Tabel1) + 
               ' ADD CONSTRAINT ' + @NumeFKNou + 
               ' FOREIGN KEY (New_' + @ColoanaPK_TN + ') REFERENCES ' + QUOTENAME(@TabelN) + '(' + @ColoanaPK_TN + ')';
    EXEC sp_executesql @SQL;

    PRINT 'Transformare 1:N -> N:1 realizata cu succes!';
END

GO

EXEC sp_Transformare_1N_in_N1 'Cladire', 'Camera'
