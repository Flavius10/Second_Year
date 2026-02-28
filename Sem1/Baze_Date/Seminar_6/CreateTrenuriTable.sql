USE GestiuneRuteTrenuri;



CREATE TABLE tipuriTrenuri(

	idTip INT NOT NULL IDENTITY,
	descriere VARCHAR(150) NOT NULL,

	CONSTRAINT pk_idTip PRIMARY KEY (idTip)

);

CREATE TABLE trenuri(
	idTren INT NOT NULL IDENTITY, 
	nume VARCHAR(50) NOT NULL,

	idTip INT NOT NULL,

	CONSTRAINT pk_trenuri PRIMARY KEY (idTren),
	CONSTRAINT fk_trenuri_tipuri FOREIGN KEY (idTip) REFERENCES tipuriTrenuri(idTip)
);


CREATE TABLE statii(

	idStatie INT NOT NULL IDENTITY,
	nume VARCHAR(50) NOT NULL,

	CONSTRAINT pk_statii PRIMARY KEY (idStatie)
);

CREATE TABLE rute(
	idRuta INT NOT NULL IDENTITY,
	nume VARCHAR(50) NOT NULL,

	idTren INT NOT NULL,

	CONSTRAINT fk_rute_trenuri FOREIGN KEY (idTren)
					REFERENCES trenuri(idTren),
	CONSTRAINT pk_ruta PRIMARY KEY (idRuta)

);

CREATE TABLE ruteStatii(

	idRuta INT NOT NULL,
	idStatie INT NOT NULL, 

	oraSosire TIME NOT NULL,
	oraPlecare TIME NOT NULL,

	CONSTRAINT pk_RuteStatii PRIMARY KEY (idRuta, idStatie),

	CONSTRAINT fk_ruteStatii_statie FOREIGN KEY (idStatie) REFERENCES statii(idStatie),
	CONSTRAINT fk_ruteStatii_rute FOREIGN KEY (idRuta) REFERENCES rute(idRuta)
);

GO

CREATE OR ALTER PROCEDURE adauga
	@idRuta INT,
	@idStatie INT, 
	@oraSosirii TIME,
	@oraPlecarii TIME
AS
BEGIN
	if EXISTS (SELECT * FROM RuteStatii WHERE idRuta = @idRuta AND idStatie = @idStatie)
	BEGIN
		
		UPDATE RuteStatii
			SET oraSosire = @oraSosirii, oraPlecare = @oraPlecarii
			WHERE idStatie = @idStatie AND idRuta = @idRuta;

			PRINT 'Orele au fost actualizate pentru aceasta statie.';
	END
	ELSE
	BEGIN

			INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare)
				VALUES (@idRuta, @idStatie, @oraSosirii, @oraPlecarii);
        
				PRINT 'Statia a fost adaugata pe ruta.';
		
	END
END

EXEC adauga 1, 2, '10:40:00', '10:55:00'

SELECT * FROM ruteStatii

























INSERT INTO TipuriTrenuri (descriere) VALUES ('InterRegio (IR)');
INSERT INTO TipuriTrenuri (descriere) VALUES ('Regio (R)');
INSERT INTO TipuriTrenuri (descriere) VALUES ('InterCity (IC)');

INSERT INTO Statii (nume) VALUES ('Bucuresti Nord');
INSERT INTO Statii (nume) VALUES ('Ploiesti Vest');
INSERT INTO Statii (nume) VALUES ('Sinaia');
INSERT INTO Statii (nume) VALUES ('Brasov');
INSERT INTO Statii (nume) VALUES ('Constanta');

INSERT INTO Trenuri (nume, idTip) VALUES ('IR 1633', 1);
INSERT INTO Trenuri (nume, idTip) VALUES ('R 3021', 2);
INSERT INTO Trenuri (nume, idTip) VALUES ('IC Tomis', 3);

INSERT INTO Rute (nume, idTren) VALUES ('Bucuresti - Brasov', 1);
INSERT INTO Rute (nume, idTren) VALUES ('Bucuresti - Constanta', 3);

INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (1, 1, NULL, '10:00');
INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (1, 2, '10:45', '10:50');
INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (1, 3, '11:40', '11:45');
INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (1, 4, '12:30', NULL);

INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (2, 1, NULL, '09:00');
INSERT INTO RuteStatii (idRuta, idStatie, oraSosire, oraPlecare) VALUES (2, 5, '11:20', NULL);