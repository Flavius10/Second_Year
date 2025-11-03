--create a new table
USE SmartBuilding;
GO

IF OBJECT_ID('do_proc_3', 'P') IS NOT NULL
    DROP PROCEDURE do_proc_3;
GO

IF OBJECT_ID('undo_proc_3', 'P') IS NOT NULL
    DROP PROCEDURE undo_proc_3;
GO


CREATE PROCEDURE do_proc_3 AS
BEGIN
	CREATE TABLE Cost(
		Id INT PRIMARY KEY IDENTITY,
		Price INT NOT NULL,
		Limit_cost INT NOT NULL
	);
	PRINT 'Executat: proc_do_v1 - Tabelul Cost a fost creat.';
END

GO


CREATE PROCEDURE undo_proc_3 AS
BEGIN
	DROP TABLE Cost;
	PRINT 'Executat: proc_undo_v1 - Tabelul Utilizatori a fost sters.';
END

GO
