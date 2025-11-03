USE SmartBuilding;
GO

IF OBJECT_ID('do_proc_1', 'P') IS NOT NULL
    DROP PROCEDURE do_proc_1;
GO

IF OBJECT_ID('undo_proc_1', 'P') IS NOT NULL
    DROP PROCEDURE undo_proc_1;
GO

CREATE PROCEDURE do_proc_1 AS
BEGIN
	ALTER TABLE Utilizator
	ALTER COLUMN nivel_acces NVARCHAR(100);
	PRINT 'Executat: proc_do_1 - Utilizator are acum nivel_acces NVARCHAR';
END
GO

CREATE PROCEDURE undo_proc_1 AS
BEGIN
	ALTER TABLE Utilizator
	ALTER COLUMN nivel_acces VARCHAR(100);
	PRINT 'Executat: undo_proc_1 - Utilizator are nivel_acces VARCHAR';
END
GO