CREATE DATABASE ParcDistractii;

GO
USE ParcDistractii;
GO

CREATE TABLE Sectiuni(

	cod_s INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,
	descriere VARCHAR(1000),

	CONSTRAINT PK_Sectiuni PRIMARY KEY (cod_s)
);

CREATE TABLE Atractii(

	cod_a INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL, 
	descriere VARCHAR(1000),
	varsta_minima INT NOT NULL,
	cod_s INT NOT NULL,

	CONSTRAINT PK_Atractii PRIMARY KEY (cod_a),
	CONSTRAINT FK_Atractii_Sectiune FOREIGN KEY (cod_s) REFERENCES Sectiuni(cod_s) ON DELETE CASCADE
	
);

CREATE TABLE Categorii(

	cod_c INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL,

	CONSTRAINT PK_Categorii PRIMARY KEY (cod_c)

);


CREATE TABLE Vizitatori(

	cod_v INT IDENTITY(1, 1),
	nume VARCHAR(100) NOT NULL, 
	email VARCHAR(100) NOT NULL, 
	cod_c INT NOT NULL,
	
	CONSTRAINT PK_VIzitatori PRIMARY KEY (cod_v),
	CONSTRAINT FK_Categorii FOREIGN KEY (cod_c) REFERENCES Categorii(cod_c) ON DELETE CASCADE

);

CREATE TABLE Note(

	cod_a INT NOT NULL,
	cod_v INT NOT NULL,

	nota INT NOT NULL CHECK(1 <= nota AND nota <= 10)

	CONSTRAINT PK_Atractii_Vizitatori PRIMARY KEY (cod_a, cod_v),

	CONSTRAINT FK_Note_Atractii FOREIGN KEY (cod_a) REFERENCES Atractii(cod_a) ON DELETE CASCADE,
	CONSTRAINT FK_Note_Vizitatori FOREIGN KEY (cod_v) REFERENCES Vizitatori(cod_v) ON DELETE CASCADE

);
