-- Cerinta 1 --

USE GestiuneTrenuri

GO

CREATE TABLE TipuriTrenuri(
	
	id_tip INT PRIMARY KEY IDENTITY(1, 1),
	descriere VARCHAR(255) NOT NULL
);

CREATE TABLE Trenuri(
	
	id_tren INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100) NOT NULL,
	id_tip INT NOT NULL,

	FOREIGN KEY (id_tip) REFERENCES TipuriTrenuri(id_tip)
	
);

CREATE TABLE Statii(
	id_statie INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL
);

CREATE TABLE Rute(
	
	id_ruta INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	id_tren INT,

	FOREIGN KEY (id_tren) REFERENCES Trenuri(id_tren)
);

CREATE TABLE StatiiRute(
	id_statie INT,
	id_ruta INT,

	ora_sosirii TIME,
	ora_plecarii TIME,

	PRIMARY KEY (id_statie, id_ruta),
	FOREIGN KEY (id_statie) REFERENCES Statii(id_statie),
	FOREIGN KEY (id_ruta) REFERENCES Rute(id_ruta)

);

GO


-- Cerinta 2 --

CREATE PROCEDURE AdaugaStatie(
	@id_rute INT,
	@id_statie INT,
	@ora_sosirii TIME,
	@ora_plecarii TIME
)
AS
BEGIN
	
	IF EXISTS (SELECT 1 FROM StatiiRute 
			WHERE id_ruta = @id_rute AND id_statie = @id_statie)
	BEGIN
			UPDATE StatiiRute 
			SET ora_sosirii = @ora_sosirii, ora_plecarii = @ora_plecarii
			WHERE id_ruta = @id_rute AND id_statie = @id_statie

			PRINT 'Datele au fost actualizate!'

	END
ELSE
	BEGIN
		
		INSERT INTO StatiiRute(id_statie, id_ruta, ora_sosirii, ora_plecarii)
		VALUES (@id_statie, @id_rute, @ora_sosirii, @ora_plecarii)

		PRINT 'Datele au fost introduse'

	END

END;

-- Cerinta 3 --

GO

CREATE VIEW View_RuteCuToateStatiile 
AS
SELECT R.nume
FROM Rute R
JOIN StatiiRute SR ON R.id_ruta = SR.id_ruta
GROUP BY R.id_ruta, R.nume
HAVING COUNT(SR.id_statie) = (
	SELECT COUNT(*)
	FROM Statii
)

GO

SELECT * FROM View_RuteCuToateStatiile;

SELECT * FROM StatiiRute
SELECT * FROM Statii
SELECT * FROM Rute

SELECT R.nume
FROM Rute R
JOIN StatiiRute SR ON R.id_ruta = SR.id_ruta
GROUP BY R.nume