USE SmartBuilding

GO

CREATE TABLE Utilizator_Log (
	
	LogId INT PRIMARY KEY IDENTITY,
	id_user INT,
	nume VARCHAR(100),
    email VARCHAR(100),
    rol VARCHAR(100),
    nivel_acces VARCHAR(100),

	OperationType VARCHAR(10),
	OperationDate DATETIME,
	UserLogin VARCHAR(100)
	
);

CREATE TABLE Utilizator_Camera_Log (
	
	LogID INT PRIMARY KEY IDENTITY,

	id_user INT,
	id_camera INT,
	nivel_acces VARCHAR(100),
	ineterval_orar VARCHAR(100),

	OperationType VARCHAR(10),
	OperationDate DATETIME,
	UserLogin VARCHAR(100)

);

CREATE TABLE Camera_Log(

	LogId INT PRIMARY KEY IDENTITY,

	id_camera INT,
	id_cladire INT,
	nume_camera VARCHAR(100),
	tip_camera VARCHAR(100),
	capacitate INT,
	ocupata BIT,

	OperationType VARCHAR(10),
	OperationDate DATETIME,
	UserLogin VARCHAR(100)
);

GO

CREATE OR ALTER TRIGGER trg_Audit_Utilizator
ON Utilizator
AFTER UPDATE, DELETE
AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @OpType VARCHAR(10);

	IF EXISTS(SELECT * FROM inserted)
		SET @OpType = 'UPDATE';
	ELSE
		SET @OpType = 'DELETE';

	INSERT INTO Utilizator_Log (
        id_user, nume, email, rol, nivel_acces, 
        OperationType, OperationDate, UserLogin
    )
    SELECT 
        d.id_user, d.nume, d.email, d.rol, d.nivel_acces,
        @OpType, GETDATE(), SYSTEM_USER
    FROM deleted d;

    PRINT 'Audit: Modificarea a fost salvata in Utilizator_Log.';

END

GO

CREATE OR ALTER TRIGGER trg_Audit_Camera
ON Camera
AFTER UPDATE, DELETE
AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @OpType VARCHAR(10);

	IF EXISTS(SELECT * FROM inserted)
		SET @OpType = 'UPDATE';
	ELSE
		SET @OpType = 'DELETE';

	INSERT INTO Camera_Log(
		id_camera, id_cladire, nume_camera,
		tip_camera, capacitate, ocupata,
		OperationType, OperationDate, UserLogin
	)
	SELECT 
		d.id_camera, d.id_camera, d.nume_camera, d.tip_camera, d.capacitate, d.ocupata,
		@OpType, GETDATE(), SYSTEM_USER

	FROM deleted d;

END

GO

CREATE OR ALTER TRIGGER trg_Audit_Utilizator_Camera
ON Utilizator_Camera
AFTER UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @OpType VARCHAR(10);

    IF EXISTS (SELECT * FROM inserted)
        SET @OpType = 'UPDATE';
    ELSE
        SET @OpType = 'DELETE';

    INSERT INTO Utilizator_Camera_Log (
        id_user, id_camera, nivel_acces, ineterval_orar,
        OperationType, OperationDate, UserLogin
    )
    SELECT 
        d.id_user, d.id_camera, d.nivel_acces, d.interval_orar,
        @OpType, GETDATE(), SYSTEM_USER
    FROM deleted d;
END
GO

SELECT * FROM Camera_Log