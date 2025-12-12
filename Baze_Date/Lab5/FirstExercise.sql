USE SmartBuilding

GO

CREATE FUNCTION ValidateName(@Name VARCHAR(100))
RETURNS BIT
AS
BEGIN
	IF @Name IS NOT NULL AND @Name LIKE '[a-zA-Z]%'
			RETURN 1;
	RETURN 0;
END

GO

CREATE FUNCTION ValidateID(@IDEntity INT)
RETURNS BIT
AS
BEGIN
	IF EXISTS (SELECT 1 FROM Cladire WHERE id_cladire = @IDEntity)
		RETURN 1;

	RETURN 0;

END
	
GO

CREATE FUNCTION ValidateForUtilizatorCamera(@IDUtilizator INT, @IDCamera INT)
RETURNS BIT
AS
BEGIN
	IF EXISTS(SELECT 1 FROM Utilizator WHERE id_user = @IDUtilizator) AND
				EXISTS(SELECT 1 FROM Camera WHERE id_camera = @IDCamera)
					RETURN 1;

	RETURN 0;

END

GO

-------------------------CREATE-----------------------------------

CREATE PROCEDURE Create_Utilizator
	@Table_name VARCHAR(50),
	@Nume VARCHAR(100),
	@Email VARCHAR(100),
	@Rol VARCHAR(100),
	@Nivel_Acces VARCHAR(100)
AS
BEGIN

	SET NOCOUNT ON;

	IF dbo.ValidateName(@Nume) = 0
	BEGIN
		PRINT 'Eroare: Numele trebuie sa contina numai litere';
		RETURN;
	END

	INSERT INTO Utilizator(nume, email, rol, nivel_acces)
		VALUES(@Nume, @Email, @Rol, @Nivel_Acces)

	PRINT 'Ba, am adaugat utilizatorul'

END

GO

CREATE PROCEDURE Create_Camera
	@Table_name VARCHAR(50),
	@Nume_camera VARCHAR(50),
	@IDCladire INT,
	@Tip_camera VARCHAR(50),
	@Capacitate INT,
	@Ocupata BIT
AS
BEGIN

	SET NOCOUNT ON;

	IF dbo.ValidateName(@Nume_camera) = 0
	BEGIN
		PRINT 'Eroare: Numele trebuie sa contina numai litere';
		RETURN;
	END

	IF dbo.ValidateID(@IDCladire) = 0
	BEGIN
		PRINT 'Nu exista cladirea cu id-ul dat';
		RETURN;
	END

	INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata)
			VALUES(@IDCladire, @Nume_camera, @Tip_camera, @Capacitate, @Ocupata)

	PRINT 'Inserare Camera'

END


GO

CREATE PROCEDURE Create_Utilizator_Camera
	@IdUser INT,
	@IdCamera INT,
	@NivelAcces VARCHAR(100),
	@IntervalOrar VARCHAR(100)
AS
BEGIN
	
	SET NOCOUNT ON;


	IF dbo.ValidateForUtilizatorCamera(@IdUser, @IdCamera) = 0
	BEGIN
		PRINT 'Nu exista camera sau utilizatorul';
		RETURN;
	END

	INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar)
			VALUES(@IdUser, @IdCamera, @NivelAcces, @IntervalOrar)

END

GO

--------------------------------READ----------------------------------------

CREATE PROCEDURE Read_Utilizator 
		@Table_name VARCHAR(50)
AS
BEGIN
			SELECT * FROM Utilizator;
			PRINT 'SELECT operation for table ' + @Table_name;
END

GO

CREATE PROCEDURE Read_Camera
		@Table_name VARCHAR(50)
AS
BEGIN
			SELECT * FROM Camera;
			PRINT 'SELECT operation for table ' + @Table_name;
END

GO

CREATE PROCEDURE Read_Utilizator_Camera
		@Table_name VARCHAR(50)
AS
BEGIN
			SELECT * FROM Utilizator_Camera;
			PRINT 'SELECT operation for table ' + @Table_name;
END

GO

------------------------------DELETE-----------------------------------------

CREATE PROCEDURE Delete_Utilizator
	@Table_name VARCHAR(50),
	@Nume VARCHAR(100),
	@Email VARCHAR(100),
	@Rol VARCHAR(100),
	@Nivel_Acces VARCHAR(100)

AS
BEGIN

	DELETE FROM Utilizator
	WHERE nume = @Nume OR email = @Email OR rol = @Rol OR nivel_acces = @Nivel_Acces

	PRINT 'DELETE operation for table ' + @Table_name

END

GO

CREATE PROCEDURE Delete_Camera
	@Table_name VARCHAR(50),
	@Nume_camera VARCHAR(50),
	@IDCladire INT,
	@Tip_camera VARCHAR(50),
	@Capacitate INT,
	@Ocupata BIT
AS
BEGIN

	DELETE FROM Camera
	WHERE id_cladire = @IDCladire OR nume_camera = @Nume_camera OR tip_camera = @Tip_camera OR capacitate = @Capacitate OR ocupata = @Ocupata

	PRINT 'DELETE operation for table ' + @Table_name

END

GO

CREATE PROCEDURE Delete_Utilizator_Camera
	@Table_name VARCHAR(100),
	@IdUser INT,
	@IdCamera INT,
	@NivelAcces VARCHAR(100),
	@IntervalOrar VARCHAR(100)
AS
BEGIN
	
	DELETE FROM Utilizator_Camera
	WHERE id_user = @IdUser OR id_camera = @IdCamera OR nivel_acces = @NivelAcces OR interval_orar = @IntervalOrar

	PRINT 'DELETE operation for table ' + @Table_name

END

GO


---------------------------UPDATE-----------------------------------------------

CREATE PROCEDURE Update_Utilizator
	@Table_name VARCHAR(50),
	@IdUtilizator INT,
	@Nume VARCHAR(100),
	@Email VARCHAR(100),
	@Rol VARCHAR(100),
	@Nivel_Acces VARCHAR(100)

AS
BEGIN
		UPDATE Utilizator
		SET nume = @Nume , email = @Email , rol = @Rol, nivel_acces = @Nivel_Acces
		WHERE id_user = @IdUtilizator
		PRINT 'UPDATE operation for table ' + @Table_name

END

GO

CREATE PROCEDURE Update_Camera
	@Table_name VARCHAR(50),
	@IdCamera INT,
	@Nume_camera VARCHAR(50),
	@IDCladire INT,
	@Tip_camera VARCHAR(50),
	@Capacitate INT,
	@Ocupata BIT
AS
BEGIN

	UPDATE Camera
	SET id_cladire = @IDCladire , nume_camera = @Nume_camera , tip_camera = @Tip_camera , capacitate = @Capacitate , ocupata = @Ocupata
	WHERE id_camera = @IdCamera
	PRINT 'UPDATE operation for table ' + @Table_name


END

GO

CREATE PROCEDURE Update_Utilizator_Camera
	@Table_name VARCHAR(100),
	@IdUser INT,
	@IdCamera INT,
	@NivelAcces VARCHAR(100),
	@IntervalOrar VARCHAR(100)
AS
BEGIN
	
	IF dbo.ValidateForUtilizatorCamera(@IdUser, @IdCamera) = 0
	BEGIN
		PRINT 'Nu exista id-urile astea'
		RETURN;
	END

	UPDATE Utilizator_Camera
	SET nivel_acces = @NivelAcces , interval_orar = @IntervalOrar
	WHERE id_user = @IdUser AND id_camera = @IdCamera

	PRINT 'UPDATE operation for table ' + @Table_name

END

/*
DROP PROCEDURE Update_Utilizator_Camera;
DROP PROCEDURE Update_Camera;
DROP PROCEDURE Update_Utilizator;
*/

EXEC Update_Utilizator 'TabelaTest', 1, 'NumeNou', 'mail@test.com', 'User', 'Low';
EXEC Delete_Utilizator_Camera 'TabelaTest', 1, 1, 'Full', '08-16';

SELECT * FROM Utilizator_Log;
SELECT * FROM Utilizator_Camera_Log;
