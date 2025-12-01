USE SmartBuilding;

GO

CREATE OR ALTER PROCEDURE sp_Transformare_1N_in_11_Sys_1
    @TabelParinte NVARCHAR(128),
    @TabelCopil NVARCHAR(128)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SQL NVARCHAR(MAX);

    DECLARE @PK_Copil NVARCHAR(128);
    DECLARE @ColoanaFK NVARCHAR(128);

    -- aflam PK-ul Tabelului Copil
    SELECT TOP 1 @PK_Copil = c.name
    FROM sys.indexes i
    JOIN sys.index_columns ic ON i.object_id = ic.object_id AND i.index_id = ic.index_id
    JOIN sys.columns c ON ic.object_id = c.object_id AND ic.column_id = c.column_id
    WHERE i.is_primary_key = 1 AND i.object_id = OBJECT_ID(@TabelCopil);

    -- alam numele coloanei FK din Copil care arata spre Parinte
    SELECT TOP 1 @ColoanaFK = c.name
    FROM sys.foreign_keys fk
    JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
    JOIN sys.columns c ON fkc.parent_object_id = c.object_id AND fkc.parent_column_id = c.column_id
    WHERE fk.parent_object_id = OBJECT_ID(@TabelCopil) 
      AND fk.referenced_object_id = OBJECT_ID(@TabelParinte);

    IF @ColoanaFK IS NULL BEGIN PRINT 'Eroare: Nu exista relatie!'; RETURN; END
    
    PRINT 'Identificat: PK_Copil=' + @PK_Copil + ' | FK_Coloana=' + @ColoanaFK;

    -------------------------------------------------------------------------------------------------------

    -- logam ce urmeaza sa stergem
    -- gasim "duplicatele"
    SET @SQL = 'INSERT INTO Legaturi_Eliminate (NumeTabelSt, IdSt, NumeTabelDr, IdDr, Mesaj)
                SELECT ''' + @TabelCopil + ''', TC.' + @PK_Copil + ', ''' + @TabelParinte + ''', TC.' + @ColoanaFK + ', ''Sters pt transformare 1:N in 1:1 (Duplicat FK)''
                FROM ' + QUOTENAME(@TabelCopil) + ' TC
                WHERE TC.' + @PK_Copil + ' NOT IN (
                    SELECT MAX(' + @PK_Copil + ')
                    FROM ' + QUOTENAME(@TabelCopil) + '
                    GROUP BY ' + @ColoanaFK + '
                )';
    EXEC sp_executesql @SQL;

    -- stergem duplicatele efectiv 
    -- "Delete from Acutator WHERE id_acutator NOT IN (SELECT MAX(id) GROUP BY id_camera)"
    SET @SQL = 'DELETE FROM ' + QUOTENAME(@TabelCopil) + '
                WHERE ' + @PK_Copil + ' NOT IN (
                    SELECT MAX(' + @PK_Copil + ')
                    FROM ' + QUOTENAME(@TabelCopil) + '
                    GROUP BY ' + @ColoanaFK + ' -- Grupam dupa FK ca sa gasim max pt fiecare parinte
                )';
    EXEC sp_executesql @SQL;

    -- adaugam constrangerea UNIQUE pe FK
    DECLARE @NumeConstrangereUQ NVARCHAR(128) = 'UQ_' + @TabelCopil + '_' + @ColoanaFK + '_' + REPLACE(CAST(NEWID() AS NVARCHAR(36)), '-', '');
    
    SET @SQL = 'ALTER TABLE ' + QUOTENAME(@TabelCopil) + 
               ' ADD CONSTRAINT ' + @NumeConstrangereUQ + ' UNIQUE (' + @ColoanaFK + ')';
    EXEC sp_executesql @SQL;

    PRINT 'Relatia a devenit 1:1';
END

GO

EXECUTE sp_Transformare_1N_in_11_Sys_1 'Camera', 'Acutator'