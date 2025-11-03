USE SmartBuilding;
GO

IF OBJECT_ID('do_proc_4', 'P') IS NOT NULL
    DROP PROCEDURE do_proc_4;
GO

IF OBJECT_ID('undo_proc_4', 'P') IS NOT NULL
    DROP PROCEDURE undo_proc_4;
GO

CREATE PROCEDURE do_proc_4 AS
BEGIN
	ALTER TABLE Senzor
	ADD data_instalarii_1 DATE;
	PRINT 'Executat: proc_do_4 - Coloana data_instalarii a fost adaugata in Senzor';
END
GO

CREATE PROCEDURE undo_proc_4 AS
BEGIN
	ALTER TABLE Senzor
	DROP COLUMN data_instalarii_1;
	PRINT 'Executat: proc_undo_4 - Coloana data_instalarii a fost stearsa din Senzor';
END
GO