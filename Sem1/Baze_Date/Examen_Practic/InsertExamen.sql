USE GestiuneActivitatiIarna
GO

INSERT INTO Echipament(categorie, brand, model, an) VALUES
    ('cat1', 'brand1', 'mod1', 2020),
    ('cat2', 'brand2', 'mod2', 2021),
    ('cat3', 'brand3', 'mod3', 2022),
    ('cat4', 'brand4', 'mod4', 2023);

INSERT INTO Instructor(nume, prenume, varsta, experienta) VALUES
    ('numeI1', 'prenumeI1', 20, 'exp1'),
    ('numeI2', 'prenumeI2', 21, 'exp2'),
    ('numeI3', 'prenumeI3', 22, 'exp3'),
    ('numeI4', 'prenumeI4', 23, 'exp4');

INSERT INTO Partie (denumire, resort, zona, dificultate, siguranta, dificultati) VALUES 
    ('partia1', 'resort1', 'zona1', 'dif1', 'sig1', 'dif1'),
    ('partia2', 'resort2', 'zona2', 'dif2', 'sig2', 'dif2'),
    ('partia3', 'resort3', 'zona3', 'dif3', 'sig3', 'dif3');

INSERT INTO Cursant (nume, prenume, data_nastere, gen, restrictii, idEchipament, idInstructor) VALUES 
    ('nume1', 'prenume1', '2000-01-01', 'M', 'R1', 1, 1),
    ('nume2', 'prenume2', '2000-01-01', 'M', 'R2', 2, 1),
    ('nume3', 'prenume3', '2000-01-01', 'F', 'R3', 3, 2),
    ('nume4', 'prenume4', '2000-01-01', 'F', 'R4', 4, 3);

INSERT INTO PartieInstructori (idPartie, idInstructor, data_inceput, data_final, costul) VALUES 
    (1, 1, '2024-01-01', '2024-02-01', 100),
    (1, 2, '2024-01-01', '2024-02-01', 100), 
    (1, 3, '2024-01-01', '2024-02-01', 100), 
    (2, 4, '2024-01-01', '2024-02-01', 100);
GO