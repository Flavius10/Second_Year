
-- !!! NU UITA SA AI TABELA IN CARE SALVEZI CEEA CE PIERZI !!!! --

CREATE OR ALTER PROCEDURE sp_Transformare_MN_in_1N_Sys_1
    @TabelParinte NVARCHAR(128),   -- Tabelul care e M din M:N
    @TabelCopil NVARCHAR(128),     -- Tabelul care e N din M:N
    @TabelLegatura NVARCHAR(128)   -- Tabelul intermediar care trebuie sters (ex: Link_Camera_Senzor)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SQL NVARCHAR(MAX);

    DECLARE @PK_Parinte NVARCHAR(128), @Tip_PK_Parinte NVARCHAR(128);
    DECLARE @PK_Copil NVARCHAR(128); 

    -- aflam PK si Tipul pentru Parinte - 
    -- ca sa stim ce coloana cream in Copil si ce tip are coloana aia
    SELECT TOP 1 @PK_Parinte = c.name,
            @Tip_PK_Parinte = t.name + 
            CASE WHEN t.name IN ('char', 'varchar', 'nchar', 'nvarchar') 
                 THEN '(' + CAST(c.max_length AS VARCHAR) + ')' ELSE '' END
    FROM sys.indexes i
    JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
    JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
    JOIN sys.types t ON c.user_type_id = t.user_type_id
    WHERE i.is_primary_key = 1 AND i.object_id = OBJECT_ID(@TabelParinte);

    -- 2. Aflam PK Copil (Senzor) doar pentru JOIN-uri
    SELECT TOP 1 @PK_Copil = c.name
    FROM sys.indexes i
    JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
    JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
    WHERE i.is_primary_key = 1 AND i.object_id = OBJECT_ID(@TabelCopil);

    PRINT 'Metadate: Parinte=' + @PK_Parinte + ' (' + @Tip_PK_Parinte + ') | Copil=' + @PK_Copil;

    ------------------------------------------------------------------------------------------------------------

    -- adaugam coloana FK in Tabelu Copil
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TabelCopil) + ' ADD ' + @PK_Parinte + ' ' + @Tip_PK_Parinte;
    EXEC sp_executesql @SQL;

    -- mutam datele din Link in Copil
    -- "Update Senzor SET id_camera = (Select MAX(id_camera) FROM Link WHERE Link.id_senzor = Senzor.id_senzor)"
    SET @SQL = 'UPDATE T_Copil
                SET ' + @PK_Parinte + ' = (
                    SELECT MAX(' + @PK_Parinte + ')
                    FROM ' + QUOTENAME(@TabelLegatura) + ' Link
                    WHERE Link.' + @PK_Copil + ' = T_Copil.' + @PK_Copil + '
                )
                FROM ' + QUOTENAME(@TabelCopil) + ' T_Copil';
    EXEC sp_executesql @SQL;

    -- logam ce s-a pierdut
    -- ce ii smecher e ca punem numele automat, adica partea asta ''' + @TabelCopil + ''' si dupa cautam numai restul in Tabela de legatura
    SET @SQL = 'INSERT INTO Legaturi_Eliminate (NumeTabelSt, IdSt, NumeTabelDr, IdDr, Mesaj)
                SELECT ''' + @TabelCopil + ''', Link.' + @PK_Copil + ', ''' + @TabelParinte + ''', Link.' + @PK_Parinte + ', ''Transformat M:N in 1:N (Sters Link)''
                FROM ' + QUOTENAME(@TabelLegatura) + ' Link
                JOIN ' + QUOTENAME(@TabelCopil) + ' TC ON Link.' + @PK_Copil + ' = TC.' + @PK_Copil + '
                WHERE Link.' + @PK_Parinte + ' <> TC.' + @PK_Parinte; -- Unde nu e egal cu MAX-ul ales
    EXEC sp_executesql @SQL;

    -- stergem Tabelul de Legatura
    SET @SQL = 'DROP TABLE ' + QUOTENAME(@TabelLegatura);
    EXEC sp_executesql @SQL;

    -- adaugam Constrangerea FK in Tabelul Copil
    DECLARE @NumeFK NVARCHAR(128) = 'FK_' + @TabelCopil + '_' + @TabelParinte + '_' + REPLACE(CAST(NEWID() AS NVARCHAR(36)), '-', '');
    
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TabelCopil) + ' ADD CONSTRAINT ' + @NumeFK + 
               ' FOREIGN KEY (' + @PK_Parinte + ') REFERENCES ' + QUOTENAME(@TabelParinte) + '(' + @PK_Parinte + ')';
    EXEC sp_executesql @SQL;

    PRINT 'relatia este acum 1:N.';
END

GO

EXECUTE sp_Transformare_MN_in_1N_Sys_1 'Alarma', 'Camera', 'AlarmaCamera'