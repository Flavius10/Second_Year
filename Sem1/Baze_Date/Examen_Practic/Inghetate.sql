CREATE DATABASE GestiuneInghetate;
GO
USE GestiuneInghetate;
GO

---------------------------------------------------------
-- CERINȚA 1: Scrieți un script SQL care creează un model relațional (4p)
---------------------------------------------------------

-- b) O înghețată este caracterizată prin denumire, descriere, preț, dată de expirare și este dintr-un singur tip.
-- c) Un tip de înghețată are o denumire și o descriere.

-- 1. Tabelul TIPURI DE ÎNGHEȚATĂ
CREATE TABLE TipuriInghetate (
    id_tip INT PRIMARY KEY IDENTITY(1,1),
    denumire VARCHAR(100) NOT NULL,
    descriere VARCHAR(255)
);

-- 2. Tabelul ÎNGHEȚATE
CREATE TABLE Inghetate (
    id_inghetata INT PRIMARY KEY IDENTITY(1,1),
    denumire VARCHAR(100) NOT NULL,
    descriere VARCHAR(255),
    pret DECIMAL(10, 2) NOT NULL, -- Folosim Decimal pentru bani
    data_expirare DATE,
    id_tip INT,
    FOREIGN KEY (id_tip) REFERENCES TipuriInghetate(id_tip)
);

-- d) Fiecare copil are un nume, un prenume, un gen, o vârstă...
-- (Nota: Restricția "poate consuma cel mult o înghețată" este logică, dar structura o facem standard pentru a putea lega tabelele).

-- 3. Tabelul COPII
CREATE TABLE Copii (
    id_copil INT PRIMARY KEY IDENTITY(1,1),
    nume VARCHAR(50) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    gen CHAR(1), -- 'M' sau 'F'
    varsta INT
);

-- e) O comandă este caracterizată prin denumire, cantitate și preț.
-- (Presupunem că o comandă este făcută de un copil, deci adăugăm id_copil aici pentru a lega entitățile).

-- 4. Tabelul COMENZI
CREATE TABLE Comenzi (
    id_comanda INT PRIMARY KEY IDENTITY(1,1),
    denumire VARCHAR(100), -- ex: "Comanda Aniversare"
    cantitate INT,         -- cantitate totală (conform textului)
    pret DECIMAL(10, 2),   -- preț total (conform textului)
    id_copil INT,          -- Legătura cu tabela Copii
    FOREIGN KEY (id_copil) REFERENCES Copii(id_copil)
);

-- f) Corespunzător fiecărei comenzi și fiecărei înghețate se cunoaște data livrării și discount-ul aplicat.
-- Aceasta este tabela de legătură (Many-to-Many).

-- 5. Tabelul DETALII COMANDĂ (Legătura Comenzi <-> Înghețate)
CREATE TABLE DetaliiComenzi (
    id_comanda INT,
    id_inghetata INT,
    data_livrarii DATE DEFAULT GETDATE(),
    discount_aplicat DECIMAL(5, 2), -- ex: 10.00 pentru 10%
    
    PRIMARY KEY (id_comanda, id_inghetata),
    FOREIGN KEY (id_comanda) REFERENCES Comenzi(id_comanda),
    FOREIGN KEY (id_inghetata) REFERENCES Inghetate(id_inghetata)
);

GO

---------------------------------------------------------
-- CERINȚA 2: Creați o procedură stocată care primește o comandă, o înghețată, 
-- o data de livrare și un discount aplicat, și adaugă înghețata comenzii.
-- Dacă deja există, se actualizează data de livrare și discount-ul aplicat. (3p)
---------------------------------------------------------

CREATE PROCEDURE GestioneazaDetaliuComanda (
    @id_comanda INT,
    @id_inghetata INT,
    @data_livrarii DATE,
    @discount DECIMAL(5, 2)
)
AS
BEGIN
    -- Verificăm dacă există deja această pereche în tabela de legătură
    IF EXISTS (
        SELECT 1 
        FROM DetaliiComenzi 
        WHERE id_comanda = @id_comanda AND id_inghetata = @id_inghetata
    )
    BEGIN
        -- CAZUL UPDATE: Există, deci actualizăm
        UPDATE DetaliiComenzi
        SET data_livrarii = @data_livrarii,
            discount_aplicat = @discount
        WHERE id_comanda = @id_comanda AND id_inghetata = @id_inghetata;

        PRINT 'Detaliile comenzii au fost actualizate.';
    END
    ELSE
    BEGIN
        -- CAZUL INSERT: Nu există, deci adăugăm
        INSERT INTO DetaliiComenzi (id_comanda, id_inghetata, data_livrarii, discount_aplicat)
        VALUES (@id_comanda, @id_inghetata, @data_livrarii, @discount);

        PRINT 'Inghetata a fost adaugata la comanda.';
    END
END;
GO

---------------------------------------------------------
-- CERINȚA 3: Creați o funcție care afișează denumirea înghețatei ce se găsesc
-- în cel puțin n comenzi, unde n >= 1 este parametrul funcției. (2p)
---------------------------------------------------------

CREATE FUNCTION InghetatePopulare (@n INT)
RETURNS TABLE
AS
RETURN
(
    SELECT I.denumire
    FROM Inghetate I
    -- Facem JOIN cu tabela de legătură pentru a vedea în câte comenzi apare înghețata
    JOIN DetaliiComenzi DC ON I.id_inghetata = DC.id_inghetata
    GROUP BY I.id_inghetata, I.denumire
    -- Filtrăm grupurile care au numărul de comenzi (COUNT) mai mare sau egal cu parametrul @n
    HAVING COUNT(DC.id_comanda) >= @n
);
GO