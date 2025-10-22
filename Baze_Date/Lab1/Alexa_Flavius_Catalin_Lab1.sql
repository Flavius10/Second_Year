--CREATE DATABASE SmartBuilding;

USE SmartBuilding;

CREATE TABLE Cladire(
	id_cladire INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	adresa VARCHAR(500) NOT NULL,
	suprafata_totala DECIMAL(10, 2) NOT NULL,
	certificat_verde BIT,

	CONSTRAINT PK_Cladire PRIMARY KEY (id_cladire)
);

CREATE TABLE Camera(

	id_camera INT IDENTITY(1, 1),
	id_cladire INT NOT NULL,
	nume_camera VARCHAR(50) NOT NULL,
	tip_camera VARCHAR(50) NOT NULL,
	capacitate INT NOT NULL,
	ocupata BIT,

	CONSTRAINT PK_Camera PRIMARY KEY (id_camera),
	CONSTRAINT FK_Camera_Cladire FOREIGN KEY (id_cladire) REFERENCES Cladire(id_cladire)
);

CREATE TABLE Energie_Consum(

	id_consum  INT IDENTITY(1, 1),
	id_camera INT NOT NULL,
	energie_kWh INT NOT NULL, 
	sursa VARCHAR(50) NOT NULL,
	timp_inregistrare DATETIME NOT NULL,

	CONSTRAINT PK_Energie_Consum PRIMARY KEY (id_consum),
	CONSTRAINT FK_Energie_Consum_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera)

);

CREATE TABLE Predictii_AI(
	
	id_predictie INT IDENTITY(1, 1),
	id_camera INT NOT NULL, 
	tip_predictie VARCHAR(100) NOT NULL, 
	valoare_prevazuta INT NOT NULL,
	timp_start DATETIME2 NOT NULL,
	timp_end DATETIME2 NOT NULL,
	acuratete INT NOT NULL,

	CONSTRAINT PK_Predictii_AI PRIMARY KEY (id_predictie),
	CONSTRAINT FK_Predictii_AI_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera)
);

CREATE TABLE Utilizator(

	id_user INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	rol VARCHAR(100) NOT NULL,
	nivel_acces VARCHAR(100) NOT NULL,

	CONSTRAINT PK_Utilizator PRIMARY KEY (id_user)

);

CREATE TABLE Utilizator_Camera(

	id_user INT NOT NULL,
	id_camera INT NOT NULL, 
	nivel_acces VARCHAR(100) NOT NULL, 
	interval_orar VARCHAR(100) NOT NULL,

	CONSTRAINT PK_Utilizator_Camera PRIMARY KEY (id_user, id_camera),
	CONSTRAINT FK_Utilizator_Camera_Utilizator FOREIGN KEY (id_user) REFERENCES Utilizator(id_user),
    CONSTRAINT FK_Utilizator_Camera_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera)

);

CREATE TABLE Alarma(

	id_alarma INT IDENTITY(1, 1),
	id_camera INT NOT NULL,
	tip_alarma VARCHAR(50) NOT NULL,
	prioritate VARCHAR(50),
	timp_declansare DATETIME2 NOT NULL,
	status_alarma VARCHAR(50),

	CONSTRAINT PK_Alarma PRIMARY KEY (id_alarma)

);

CREATE TABLE AlarmaCamera(

	id_alarma INT NOT NULL, 
	id_camera INT NOT NULL,
	zona_afectata VARCHAR(100) NOT NULL,
	timp_asociere DATETIME2 NOT NULL,

	CONSTRAINT PK_Alarma_Camera PRIMARY KEY (id_alarma, id_camera),
	CONSTRAINT FK_Alarma_Camera_Utilizator FOREIGN KEY (id_alarma) REFERENCES Alarma(id_alarma),
    CONSTRAINT FK_Alarma_Camera_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera) 
	
);

CREATE TABLE Senzor(

	id_senzor INT IDENTITY(1, 1),
	id_camera INT NOT NULL, 
	tip_senzor VARCHAR(100) NOT NULL,
	unitate_masura VARCHAR(10) NOT NULL,
	status_senzor VARCHAR(100) NOT NULL, 
	data_instalare DATETIME2 NOT NULL, 

	CONSTRAINT PK_Senzor PRIMARY KEY (id_senzor),
	CONSTRAINT FK_Senzor_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera)

);

CREATE TABLE Masuratori_mediu(

	id_masurare INT IDENTITY(1, 1),
	id_senzor INT NOT NULL, 
	valoare INT NOT NULL,
	unitate VARCHAR(10) NOT NULL,
	timestamp_masuratori DATETIME2,

	CONSTRAINT PK_Masuratori_mediu PRIMARY KEY (id_masurare),
	CONSTRAINT FK_Masuratori_mediu_Senzor FOREIGN KEY (id_senzor) REFERENCES Senzor(id_senzor)

);

CREATE TABLE Acutator(

	id_acutator INT IDENTITY(1, 1),
	id_camera INT NOT NULL,
	tip_acutator VARCHAR(50) NOT NULL,
	descriere TEXT,
	status_acutator VARCHAR(50),
	energie_consumata INT NOT NULL,


	CONSTRAINT PK_Acutator PRIMARY KEY (id_acutator),
	CONSTRAINT FK_Acutator_Camera FOREIGN KEY (id_camera) REFERENCES Camera(id_camera)
);

CREATE TABLE AcutatorSenzor(

	id_acutator INT NOT NULL,
	id_senzor INT NOT NULL, 
	mod_control VARCHAR(50) NOT NULL,

	CONSTRAINT PK_Acutator_Senzor PRIMARY KEY (id_acutator, id_senzor),
	CONSTRAINT FK_Acutator_Senzor_Acutator FOREIGN KEY (id_acutator) REFERENCES Acutator(id_acutator),
    CONSTRAINT FK_Acutator_Senzor_Senzor FOREIGN KEY (id_senzor) REFERENCES Senzor(id_senzor)
	
);