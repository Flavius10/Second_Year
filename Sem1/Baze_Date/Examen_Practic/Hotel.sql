/*
Enunț Problemă: Gestiune Hotelieră

Creați o bază de date pentru un lanț hotelier.

a) Entitățile de interes sunt: Hoteluri, TipuriCamere, Camere, Clienți,
Rezervări și ArhivăAnulări. b) Un Hotel are un nume, număr de stele
și o adresă. c) Există mai multe Tipuri de Camere (ex: Single, Double, Suite),
caracterizate prin denumire și descriere. d) O Cameră are un număr (ex: 101),
un preț pe noapte, aparține unui singur hotel și este de un anumit tip. 
e) Un Client are nume, prenume și telefon. 
f) Rezervările fac legătura între clienți și camere. 
O rezervare are o dată de check-in și check-out. 
g) Tabela ArhivăAnulări va stoca automat rezervările care sunt șterse.

Cerințe:

    Scrieți scriptul SQL pentru crearea modelului relațional (5-6 tabele). (4p)

    Creați o procedură stocată care primește datele pentru o rezervare (id_client, id_camera, check-in, check-out).
    Procedura trebuie să verifice dacă data de check-in este mai
    mică decât cea de check-out. Dacă datele sunt greșite, afișează eroare.
    Dacă sunt corecte, inserează rezervarea. (3p)

    Creați un TRIGGER care se activează la ștergerea unei rezervări.
    Acesta va muta datele rezervării șterse în tabela ArhivăAnulări,
    împreună cu data curentă la care s-a făcut anularea. (2p)

*/


CREATE DATABASE GestiuneHotel;
GO
USE GestiuneHotel;
GO

---------------------------------------------------------
-- CERINȚA 1: Modelul Relațional (Structura complexă)
---------------------------------------------------------

-- 1. Tabela TIPURI CAMERE (Entitate simplă)
CREATE TABLE TipuriCamere (
    id_tip INT PRIMARY KEY IDENTITY(1,1),
    denumire VARCHAR(50), -- ex: 'Double', 'Apartament'
    descriere VARCHAR(200)
);

-- 2. Tabela HOTELURI (Entitate simplă)
CREATE TABLE Hoteluri (
    id_hotel INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(100),
    stele INT,
    adresa VARCHAR(200)
);

-- 3. Tabela CAMERE (Legată de Hotel și Tip)
CREATE TABLE Camere (
    id_camera INT PRIMARY KEY IDENTITY(1,1),
    numar_camera VARCHAR(10), -- ex: '204B'
    pret_noapte DECIMAL(10, 2),
    id_hotel INT,
    id_tip INT,
    FOREIGN KEY (id_hotel) REFERENCES Hoteluri(id_hotel),
    FOREIGN KEY (id_tip) REFERENCES TipuriCamere(id_tip)
);

-- 4. Tabela CLIENȚI
CREATE TABLE Clienti (
    id_client INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(50),
    prenume VARCHAR(50),
    telefon VARCHAR(20)
);

-- 5. Tabela REZERVĂRI (Tabela de legătură Client <-> Cameră)
CREATE TABLE Rezervari (
    id_rezervare INT PRIMARY KEY IDENTITY(1,1),
    data_checkin DATE,
    data_checkout DATE,
    id_client INT,
    id_camera INT,
    FOREIGN KEY (id_client) REFERENCES Clienti(id_client),
    FOREIGN KEY (id_camera) REFERENCES Camere(id_camera)
);

-- 6. Tabela ARHIVĂ ANULĂRI (Pentru Trigger)
-- Aceasta nu are legături (FK), doar stochează istoric
CREATE TABLE ArhivaAnulari (
    id_arhiva INT PRIMARY KEY IDENTITY(1,1),
    id_rezervare_vechi INT,
    motiv VARCHAR(100) DEFAULT 'Anulare Client',
    data_anularii DATETIME
);
GO

---------------------------------------------------------
-- CERINȚA 2: Procedura cu Validare
---------------------------------------------------------

CREATE PROCEDURE AdaugaRezervare (
    @id_client INT,
    @id_camera INT,
    @in DATE,
    @out DATE
)
AS
BEGIN
    -- Validare: Nu poți pleca înainte să ajungi
    IF @in >= @out
    BEGIN
        PRINT 'Eroare: Data de Check-In trebuie sa fie inaintea datei de Check-Out!';
    END
    ELSE
    BEGIN
        INSERT INTO Rezervari (id_client, id_camera, data_checkin, data_checkout)
        VALUES (@id_client, @id_camera, @in, @out);
        
        PRINT 'Rezervarea a fost efectuata cu succes.';
    END
END;
GO

---------------------------------------------------------
-- CERINȚA 3: TRIGGER PENTRU ȘTERGERE
---------------------------------------------------------

CREATE TRIGGER trg_MutareInArhiva
ON Rezervari
FOR DELETE -- Se activează DOAR când dăm DELETE
AS
BEGIN
    -- Tabelul "deleted" conține rândurile care tocmai dispar
    INSERT INTO ArhivaAnulari (id_rezervare_vechi, data_anularii)
    SELECT id_rezervare, GETDATE()
    FROM deleted;

    PRINT 'Rezervarea stearsa a fost mutata in Arhiva.';
END;
GO