--CREATE DATABASE GestiuneActivitatiIarna

--GO

USE GestiuneActivitatiIarna

GO

CREATE TABLE Instructor(
	
	idInstructor INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	prenume VARCHAR(100) NOT NULL,
	varsta INT NOT NULL,
	experienta VARCHAR(100) NOT NULL,

);

CREATE TABLE Echipament(
	
	idEchipament INT PRIMARY KEY IDENTITY(1, 1),
	categorie VARCHAR(100) NOT NULL,
	brand VARCHAR(100) NOT NULL,
	model VARCHAR(100) NOT NULL,
	an INT

);

-- aici pun sa fie in relatie 1-N
CREATE TABLE Cursant(
	
	idCursant INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	prenume VARCHAR(100) NOT NULL,
	data_nastere DATE NOT NULL,
	gen VARCHAR(10) NOT NULL,
	restrictii VARCHAR(100) NOT NULL,

	idEchipament INT,
	idInstructor INT,

	FOREIGN KEY (idEchipament) REFERENCES Echipament(idEchipament),
	FOREIGN KEY (idInstructor) REFERENCES Instructor(idInstructor)
);

CREATE TABLE Partie(

	idPartie INT PRIMARY KEY IDENTITY(1, 1),
	denumire VARCHAR(10) NOT NULL,
	resort VARCHAR(10) NOT NULL,
	zona VARCHAR(10) NOT NULL,
	dificultate VARCHAR(10) NOT NULL,
	siguranta VARCHAR(10) NOT NULL,
	dificultati VARCHAR(10) NOT NULL

);

CREATE TABLE PartieInstructori(
	
	idPartie INT,
	idInstructor INT,

	data_inceput DATE NOT NULL,
	data_final DATE NOT NULL,
	costul INT NOT NULL,

	PRIMARY KEY(idPartie, idInstructor),
	FOREIGN KEY (idInstructor) REFERENCES Instructor(idInstructor),
	FOREIGN KEY (idPartie) REFERENCES Partie(idPartie)

);

GO

CREATE PROCEDURE GestioneazaAlocareInstructor (
	@idPartie INT,
	@idInstructor INT,
	@dataInceput DATE,
	@dataFinal DATE,
	@costul INT
)
AS
BEGIN
	-- Ne uitam daca exista asa ceva in baza noastra de date
	-- si primim 1 in cazul in care exista
	IF EXISTS (
		SELECT 1 
		FROM PartieInstructori 
		WHERE idPartie = @idPartie AND idInstructor = @idInstructor
	)
	BEGIN
		-- Daca exista, dam update
		UPDATE PartieInstructori
		SET data_inceput = @dataInceput,
			data_final = @dataFinal,
			costul = @costul
		WHERE idPartie = @idPartie AND idInstructor = @idInstructor;

		PRINT 'S-o dat update';
	END

	ELSE

	BEGIN
       
		INSERT INTO PartieInstructori (idPartie, idInstructor, data_inceput, data_final, costul)
		VALUES (@idPartie, @idInstructor, @dataInceput, @dataFinal, @costul);

		PRINT 'Instructorul merge pe partia data';
	END
END;

GO

CREATE VIEW View_PartiiTopInstructori AS
SELECT TOP 1 P.denumire
FROM Partie p
JOIN PartieInstructori PI ON P.idPartie = PI.idPartie
GROUP BY P.denumire
ORDER BY COUNT(PI.idInstructor) DESC;

GO

SELECT * FROM Cursant;
SELECT * FROM Instructor;
SELECT * FROM Partie;
SELECT * FROM Echipament;
SELECT * FROM PartieInstructori;

SELECT * FROM View_PartiiTopInstructori;

EXEC GestioneazaAlocareInstructor 
	@idPartie = 1, 
	@idInstructor = 2, 
	@dataInceput = '2025-01-01', 
	@dataFinal = '2025-02-01', 
	@costul = 9999;

SELECT * FROM PartieInstructori WHERE idPartie = 1 AND idInstructor = 2;