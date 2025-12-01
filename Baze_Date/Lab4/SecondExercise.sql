USE SmartBuilding;

GO

CREATE OR ALTER PROCEDURE sp_Transformare_1N_MN_Sys_2
	@Table1 NVARCHAR(128),
	@TableN NVARCHAR(128)
AS
BEGIN
	--aici nu mai arata la fiecare modificare cate o comanda modificata pe rand
	SET NOCOUNT ON;
	DECLARE @SQL NVARCHAR(MAX);

	DECLARE @PK_T1 NVARCHAR(128), @Tip_PK_T1 NVARCHAR(128);
	DECLARE @PK_TN NVARCHAR(128), @Tip_PK_TN NVARCHAR(128);
	DECLARE @ColoanaFK_Veche NVARCHAR(128), @NumeConstrangereFK NVARCHAR(128);

	--Aflam Pk si tipul acestuia pentru prima tabela(tabela 1)
	--cauta intr-un registru in care s-au salvat toate modificarile pe care le-am facut in DB
	SELECT TOP 1 @PK_T1 = c.name,
			@Tip_PK_T1 = t.name +
			CASE WHEN t.name IN ('char', 'varchar', 'nchar', 'nvarchar')
				THEN '(' + CAST(c.max_length AS VARCHAR) + ')' ELSE '' END
	FROM sys.indexes i
	JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
	JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
	JOIN sys.types t ON c.user_type_id = t.user_type_id
	WHERE i.is_primary_key = 1 AND i.object_id = OBJECT_ID(@Table1)

	--Aflam Pk si tipul acestuia pentru a doua tabela(tabela N)
	SELECT TOP 1 @PK_TN = c.name,
			@Tip_PK_TN = t.name +
			CASE WHEN t.name IN ('char', 'varchar', 'nchar', 'nvarchar')
				THEN '(' + CAST(c.max_length AS VARCHAR) + ')' ELSE '' END
	FROM sys.indexes i
	JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
	JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
	JOIN sys.types t ON c.user_type_id = t.user_type_id
	WHERE i.is_primary_key = 1 AND i.object_id = OBJECT_ID(@TableN)

	-- Aflam FK-ul existent in Tabelul N
	SELECT TOP 1 @ColoanaFK_Veche = c.name , @NumeConstrangereFK = fk.name
	FROM sys.foreign_keys fk
	JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
	JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
	WHERE fk.parent_object_id = OBJECT_ID(@TableN)
		AND fk.referenced_object_id = OBJECT_ID(@Table1);

	IF @ColoanaFK_Veche IS NULL BEGIN PRINT 'Eroare: Nu s-a gasit relatia'; RETURN; END

	PRINT 'Metadate: PK1=' + @PK_T1 + ' | PKN=' + @PK_TN + ' | FK=' + @ColoanaFK_Veche;

	-------------------------------------------------------------------------------------------------

	DECLARE @NumeTabelLink NVARCHAR(128) = 'Link_' + @Table1 + '_' + @TableN;

	-- aici duamna am creat o tabela de legatura ca sa puteti spune ca e legatura M:N
	SET @SQL = 'CREATE TABLE ' + QUOTENAME(@NumeTabelLink) + ' (
				' + @PK_T1 + ' ' + @Tip_PK_T1 + ' NOT NULL,
				' + @PK_TN + ' ' + @Tip_PK_TN + ' NOT NULL,
				PRIMARY KEY (' + @PK_T1 + ', ' + @PK_TN + ')
			)';
	EXEC sp_executesql @SQL;

	-- aci asa am inserat in tabela respectiva perechile de primary key
	SET @SQL = 'INSERT INTO ' + QUOTENAME(@NumeTabelLink) + ' (' + @PK_T1 + ', ' + @PK_TN + ')
                SELECT ' + @ColoanaFK_Veche + ', ' + @PK_TN + '
                FROM ' + QUOTENAME(@TableN) + '
                WHERE ' + @ColoanaFK_Veche + ' IS NOT NULL';
    EXEC sp_executesql @SQL;

	-- aici salvam datele care s-ar pierde dupa modificarea tabelelor
	SET @SQL = 'INSERT INTO Legaturi_Eliminate (NumeTabelSt, IdSt, NumeTabelDr, IdDr, Mesaj)
                SELECT ''' + @TableN + ''', ' + @PK_TN + ', ''' + @Table1 + ''', ' + @ColoanaFK_Veche + ', ''Transformat 1:N in M:N'''
                + ' FROM ' + QUOTENAME(@TableN) + ' WHERE ' + @ColoanaFK_Veche + ' IS NOT NULL';
    EXEC sp_executesql @SQL;

    -- aici stergem vechea relatie de 1:N
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TableN) + ' DROP CONSTRAINT ' + QUOTENAME(@NumeConstrangereFK);
    EXEC sp_executesql @SQL;
    
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TableN) + ' DROP COLUMN ' + QUOTENAME(@ColoanaFK_Veche);
    EXEC sp_executesql @SQL;

    -- aici adaugam foreign key-uri pentru noul tabel
    DECLARE @FK1 NVARCHAR(128) = 'FK_Link_' + @Table1 + '_' + REPLACE(CAST(NEWID() AS NVARCHAR(36)), '-', '');
    DECLARE @FKN NVARCHAR(128) = 'FK_Link_' + @TableN + '_' + REPLACE(CAST(NEWID() AS NVARCHAR(36)), '-', '');

    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@NumeTabelLink) + ' ADD CONSTRAINT ' + @FK1 + 
               ' FOREIGN KEY (' + @PK_T1 + ') REFERENCES ' + QUOTENAME(@Table1) + '(' + @PK_T1 + ')';
    EXEC sp_executesql @SQL;

    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@NumeTabelLink) + ' ADD CONSTRAINT ' + @FKN + 
               ' FOREIGN KEY (' + @PK_TN + ') REFERENCES ' + QUOTENAME(@TableN) + '(' + @PK_TN + ')';
    EXEC sp_executesql @SQL;

    PRINT 'Numa bun, merge treaba!' +  @NumeTabelLink;
 
END

GO

EXECUTE sp_Transformare_1N_MN_Sys_2 'Camera', 'Senzor';