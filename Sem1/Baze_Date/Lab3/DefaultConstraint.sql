USE SmartBuilding;
GO

IF OBJECT_ID('do_proc_2', 'P') IS NOT NULL
    DROP PROCEDURE do_proc_2;
GO

IF OBJECT_ID('undo_proc_2', 'P') IS NOT NULL
    DROP PROCEDURE undo_proc_2;
GO

CREATE PROCEDURE do_proc_2 AS
BEGIN
	
	ALTER TABLE Utilizator_Camera
	ADD CONSTRAINT DF_nivel_acces
	DEFAULT 1 FOR nivel_acces;
	PRINT 'Executat: do_proc_2 - Adauga DEFAULT la nivel_acces'

END
GO

CREATE PROCEDURE undo_proc_2 AS
BEGIN
    ALTER TABLE Utilizator_Camera
    DROP CONSTRAINT DF_nivel_acces;
    PRINT 'Executat: proc_undo_2 - Sters DEFAULT pentru Utilizator_Camera.nivel_acces';
END
GO
