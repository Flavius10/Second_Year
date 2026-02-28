-- Inserare in Sectiuni
INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 1', 'descriere_sectiune_1')

INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 2', 'descriere_sectiune_2')

INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 3', 'descriere_sectiune_3')


INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 4', 'descriere_sectiune_4')

INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 5', 'descriere_sectiune_5')

INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 6', 'descriere_sectiune_6')

INSERT INTO Sectiuni(nume, descriere) VALUES
('sectiunea 7', 'descriere_sectiune_7')




-- Inserare in Atractii
INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('Tobogan', 'descriere_tobogan', 5, 1)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('Masiunte', 'descriere_masiunte', 10, 3)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('AquaPark', 'descriere_aquaPark', 12, 4)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('Tobogan1', 'descriere_tobogan_1', 5, 1)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('Tobogan2', 'descriere_tobogan_2', 5, 1)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('LaserPark', 'descriere', 5, 7)

INSERT INTO Atractii(nume, descriere, varsta_minima, cod_s) VALUES
('Fotbal', 'descriere_messi', 5, 6)


--Inserare in Categorii


INSERT INTO Categorii(nume) VALUES
('Fotbal')

INSERT INTO Categorii(nume) VALUES
('Baschet')

INSERT INTO Categorii(nume) VALUES
('Volei')

INSERT INTO Categorii(nume) VALUES
('Inot')

INSERT INTO Categorii(nume) VALUES
('Rugby')

INSERT INTO Categorii(nume) VALUES
('Box')

INSERT INTO Categorii(nume) VALUES
('American football')


--Inserare in Vizitatori

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Flavius', 'email', 1)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Ovidiu', 'email_ovidiu', 2)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Bardan', 'email_bardan', 2)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Radu_brainroot', 'email_radu', 3)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Mathy', 'email_mathy', 4)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Mihai', 'email_mihai', 6)

INSERT INTO Vizitatori(nume, email, cod_c) VALUES
('Luca', 'email_luca', 5)


--Insert in Nota

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(1, 1, 5)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(2, 3, 3)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(7, 4, 4)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(6, 5, 9)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(1, 4, 8)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(1, 5, 7)

INSERT INTO Note(cod_a, cod_v, nota) VALUES
(2, 7, 10)
