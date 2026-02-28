USE ExercitiiPractic;

GO

-- Cerinta 1

CREATE TABLE TipuriCompetitii (
	
	id_tip INT PRIMARY KEY,
	denumire VARCHAR(100) NOT NULL,
	descriere VARCHAR(100) NOT NULL
);

CREATE TABLE Orase (
	id_oras INT PRIMARY KEY,
	nume_oras VARCHAR(100) NOT NULL,
	regiune VARCHAR(100) NOT NULL,
	tara VARCHAR(100) NOT NULL
);

CREATE TABLE Participanti(
	id_participant INT PRIMARY KEY,
	nume VARCHAR(100) NOT NULL,
	prenume VARCHAR(100) NOT NULL,
	gen CHAR(1),
	data_nastere DATE NOT NULL
);

CREATE TABLE Competitii(
	
	id_competitie INT PRIMARY KEY,
	denumire VARCHAR(100) NOT NULL,
	data_inceput DATE NOT NULL,
	data_final DATE NOT NULL,
	id_tip INT,
	id_oras INT,

	FOREIGN KEY (id_tip) REFERENCES TipuriCompetitii(id_tip),
	FOREIGN KEY (id_oras) REFERENCES Orase(id_oras)

);

CREATE TABLE Inscrieri (
		
	id_competitie INT NOT NULL,
	id_participanti INT NOT NULL,
	taxa_participare DECIMAL(10, 2),
	loc_obtinut INT,

	PRIMARY KEY (id_competitie, id_participanti),
	FOREIGN KEY (id_participanti) REFERENCES Participanti(id_participant),
	FOREIGN KEY (id_competitie) REFERENCES Competitii(id_competitie)

);

GO

-- Cerinta 2

CREATE PROCEDURE GestioneazaInscriere (
	@id_participant INT,
	@id_competitie INT,
	@taxa DECIMAL(10, 2),
	@loc INT
)
AS
BEGIN
	
	IF EXISTS (
			SELECT 1 FROM Inscrieri WHERE 
				id_participanti = @id_participant AND id_competitie = @id_competitie
		) 
		BEGIN

			UPDATE Inscrieri
			SET taxa_participare = @taxa, loc_obtinut = @loc
			WHERE id_participanti = @id_participant AND id_competitie = @id_competitie;

			PRINT 'Datele au fost actualizate.';

		END
	ELSE
		BEGIN

		INSERT INTO Inscrieri(id_competitie, id_participanti, taxa_participare, loc_obtinut)
		VALUES (@id_competitie, @id_participant, @taxa, @loc);

		PRINT 'Participantul a fost adaugat la competitie.';

		END
END;

GO

-- Cerinta 3

CREATE VIEW View_TopCompetitii
AS

SELECT C.denumire, COUNT(I.id_participanti) AS NumarParticipanti
FROM Competitii C
JOIN Inscrieri I ON C.id_competitie = I.id_competitie
GROUP BY C.id_competitie, C.denumire
HAVING COUNT(I.id_participanti) = (
	
	SELECT MAX(NumarInscrieri)
	FROM(
		SELECT COUNT(*) AS NumarInscrieri
		FROM Inscrieri
		GROUP BY id_competitie
		) AS ListaNumaratori
);