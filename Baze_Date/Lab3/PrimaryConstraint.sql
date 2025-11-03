USE SmartBuilding;
GO

IF OBJECT_ID('do_proc_5', 'P') IS NOT NULL
    DROP PROCEDURE do_proc_5;
GO

IF OBJECT_ID('undo_proc_5', 'P') IS NOT NULL
    DROP PROCEDURE undo_proc_5;
GO



CREATE PROCEDURE do_proc_5 AS
BEGIN
	
	ALTER TABLE Predictii_AI
	ADD CONSTRAINT FK_Predictii_Camera_1
	FOREIGN KEY (id_camera) REFERENCES Camera(id_camera);
	PRINT 'Executat: proc_do_5 - Adaugat FK de la Predictii_AI la Camera';

END
GO

CREATE PROCEDURE undo_proc_5 AS
BEGIN
	ALTER TABLE Predictii_AI
	DROP CONSTRAINT FK_Predictii_Camera_1;
	PRINT 'Executat: proc_undo_5 - Sters FK de la Predictii_AI la Camera';
END
GO