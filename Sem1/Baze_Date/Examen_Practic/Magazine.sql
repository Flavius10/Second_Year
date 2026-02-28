CREATE DATABASE GestiuneMagazine

GO

USE GestiuneMagazine

GO

CREATE TABLE Locatii(

	idLocatie INT PRIMARY KEY IDENTITY(1, 1),
	localitate VARCHAR(100) NOT NULL,
	strada VARCHAR(100) NOT NULL,
	numar INT NOT NULL,
	cod_postal INT NOT NULL

);

CREATE TABLE Magazine(
	
	idMagazin INT PRIMARY KEY IDENTITY(1, 1),
	denumire VARCHAR(100) NOT NULL,
	an_deschidere DATE NOT NULL,
	id_locatie INT,

	FOREIGN KEY (id_locatie) REFERENCES Locatii(idLocatie)

);

CREATE TABLE Clienti(
	
	idClient INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	prenume VARCHAR(100) NOT NULL,
	gen VARCHAR(10) NOT NULL,
	data_nastere DATE NOT NULL

);

CREATE TABLE ProduseFavorite (

	idProdus INT PRIMARY KEY IDENTITY(1, 1),
	denumire VARCHAR(100) NOT NULL,
	pret INT NOT NULL,
	reducere INT NOT NULL,

	id_client INT NOT NULL,

	FOREIGN KEY (id_client) REFERENCES Clienti(idClient)
);

CREATE TABLE Cumparaturi(

	id_client INT NOT NULL,
	id_magazin INT NOT NULL,

	data_cumparaturi DATE NOT NULL,
	pretul INT NOT NULL,

	PRIMARY KEY (id_client, id_magazin),
	FOREIGN KEY (id_client) REFERENCES Clienti(idClient),
	FOREIGN KEY (id_magazin) REFERENCES Magazine(idMagazin)

);


GO


CREATE PROCEDURE GestioneazaCumparaturi(
	@id_client INT,
	@id_magazin INT,
	@data_cumparaturi DATE,
	@pretul INT
)
AS
BEGIN

	IF EXISTS(
		SELECT 1
		FROM Cumparaturi
		WHERE id_client = @id_client AND id_magazin = @id_magazin
	)
	BEGIN

		UPDATE Cumparaturi
		SET data_cumparaturi = @data_cumparaturi,
			pretul = @pretul
		WHERE id_client = @id_client AND id_magazin = @id_magazin;

		PRINT 'Datele achizitiei au fost actualizate.';

	END

	ELSE

	BEGIN

	INSERT INTO Cumparaturi(id_client, id_magazin, data_cumparaturi, pretul)
		VALUES(@id_client, @id_magazin, @data_cumparaturi, @pretul);

	PRINT 'Achizitia a fost inregistrata.';

	END
END;


GO

CREATE VIEW View_ClientiCeleMaiPutineProduse AS
SELECT TOP 1 WITH TIES C.nume, C.prenume
FROM Clienti C
LEFT JOIN ProduseFavorite PF ON C.idClient = PF.id_client
GROUP BY C.idClient, C.nume, C.prenume
ORDER BY COUNT(PF.idProdus) ASC;
