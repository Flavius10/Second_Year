CREATE DATABASE GestiuneRestaurante;
GO
USE GestiuneRestaurante;
GO

CREATE TABLE Orase (
    id_oras INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(100) NOT NULL
);

CREATE TABLE TipuriRestaurante (
    id_tip INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(100) NOT NULL,
    descriere VARCHAR(255)
);

CREATE TABLE Utilizatori (
    id_utilizator INT PRIMARY KEY IDENTITY(1,1),
    nume_utilizator VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    parola VARCHAR(100) NOT NULL
);

CREATE TABLE Restaurante (
    id_restaurant INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(100) NOT NULL,
    adresa VARCHAR(200),
    telefon VARCHAR(20),
    id_oras INT NOT NULL,
    id_tip INT NOT NULL,
    FOREIGN KEY (id_oras) REFERENCES Orase(id_oras),
    FOREIGN KEY (id_tip) REFERENCES TipuriRestaurante(id_tip)
);

CREATE TABLE Note (
    id_utilizator INT,
    id_restaurant INT,
    nota DECIMAL(4, 2) NOT NULL,
    date_notare DATE DEFAULT GETDATE(),
    
    PRIMARY KEY (id_utilizator, id_restaurant),
    FOREIGN KEY (id_utilizator) REFERENCES Utilizatori(id_utilizator),
    FOREIGN KEY (id_restaurant) REFERENCES Restaurante(id_restaurant)
);

GO

CREATE PROCEDURE AdaugaSauActualizeazaNota (
    @id_restaurant INT,
    @id_utilizator INT,
    @nota DECIMAL(4, 2)
)
AS
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM Note 
        WHERE id_restaurant = @id_restaurant AND id_utilizator = @id_utilizator
    )
    BEGIN
       
        UPDATE Note
        SET nota = @nota,
            date_notare = GETDATE()
        WHERE id_restaurant = @id_restaurant AND id_utilizator = @id_utilizator;

        PRINT 'Nota a fost actualizata!';
    END
    ELSE
    BEGIN
        
        INSERT INTO Note (id_restaurant, id_utilizator, nota)
        VALUES (@id_restaurant, @id_utilizator, @nota);

        PRINT 'Nota a fost adaugata!';
    END
END;

GO


CREATE FUNCTION GasesteEvaluariDupaEmail (@email_cautat VARCHAR(100))
RETURNS TABLE
AS
RETURN
(
    SELECT 
        TR.nume AS TipRestaurant,
        R.nume AS NumeRestaurant,
        R.telefon AS TelefonRestaurant,
        O.nume AS Oras,
        N.nota AS NotaAcordata,
        U.nume_utilizator AS NumeUtilizator,
        U.email AS EmailUtilizator
    FROM Note N
   
    INNER JOIN Utilizatori U ON N.id_utilizator = U.id_utilizator
    INNER JOIN Restaurante R ON N.id_restaurant = R.id_restaurant
    INNER JOIN TipuriRestaurante TR ON R.id_tip = TR.id_tip
    INNER JOIN Orase O ON R.id_oras = O.id_oras
   
    WHERE U.email = @email_cautat
);
GO