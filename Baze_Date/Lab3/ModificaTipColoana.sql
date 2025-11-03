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
	ALTER TABLE Cost
	ALTER COLUMN Limit_cost FLOAT;
	PRINT 'Executat: proc_do_1 - Cost are acum Limit DOUBLE';
END
GO

CREATE PROCEDURE undo_proc_1 AS
BEGIN
	ALTER TABLE Cost
	ALTER COLUMN Limit_cost INT
	PRINT 'Executat: undo_proc_1 - Cost are Limit INT';
END
GO