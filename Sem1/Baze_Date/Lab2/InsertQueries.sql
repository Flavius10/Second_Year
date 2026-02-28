USE SmartBuilding;
GO

----------------------INSERT INTO Cladiri------------------------------

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Spital', 'Adresa_Spital', 100, 1),
('Scoala', 'Adresa_Scoala', 110, 0),
('Gradinita', 'Adresa_Gradinita', 120, 1),
('Magazin', 'Adresa_Magazin', 130, 0),
('Bloc_locuinte', 'Adresa_Bloc_locuinte', 140, 1),
('Universitate', 'Adresa_Universitate', 150, 0),
('Mall', 'Centru', 2000, 1),
('Biblioteca Judeteana', 'Strada Cartii', 450, 1),
('Stadion', 'Aleea Sportului', 5000, 0),
('Muzeu', 'Piata Centrala', 800, 1);


--------------------------INSERT INTO Camera-----------------------------------

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(1, 'Camera Spital', 'Salon', 10, 0),
(2, 'Camera Scoala', 'Clasa', 30, 1),
(3, 'Camera Gradinita', 'Sala_Jocuri', 20, 0),
(4, 'Camera Magazin', 'Depozit', 15, 1),
(5, 'Camera Bloc Locuinte', 'Apartament', 4, 0),
(6, 'Camera Universitate', 'Amfiteatru', 100, 1),
(7, 'Sala Cinema', 'Sala', 50, 0),
(8, 'Sala Lectura', 'Sala', 25, 1),
(9, 'Vestiar', 'Anexa', 15, 0),
(10, 'Sala Expozitie', 'Sala', 40, 1),
(5, 'Hol Intrare', 'Hol', 5, 0),
(1, 'Receptie', 'Hol', 5, 1);


----------------------INSERT INTO Predictii_AI----------------------------

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(7, 'Temperatura', 22.5, '2025-10-20 08:00:00', '2025-10-20 12:00:00', 92),
(8, 'Umiditate', 46, '2025-10-19 09:00:00', '2025-10-19 13:00:00', 88),
(9, 'Numar_persoane', 18, '2025-10-18 10:00:00', '2025-10-18 14:00:00', 95),
(10, 'Consum_energie', 127, '2025-10-17 07:30:00', '2025-10-17 11:30:00', 90),
(11, 'Nivel_CO2', 415, '2025-10-16 12:00:00', '2025-10-16 16:00:00', 93),
(12, 'Luminozitate', 310, '2025-10-15 14:00:00', '2025-10-15 18:00:00', 89),
(1, 'Temperatura', 21, '2025-10-21 08:00:00', '2025-10-21 12:00:00', 91),
(2, 'Zgomot', 50, '2025-10-21 09:00:00', '2025-10-21 11:00:00', 85),
(3, 'Calitate_Aer', 98, '2025-10-22 10:00:00', '2025-10-22 14:00:00', 94),
(4, 'Flux_Oameni', 100, '2025-10-23 08:00:00', '2025-10-23 20:00:00', 88);


----------------------INSERT INTO Energie_Consum---------------------------------

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(7, 12, 'Panouri_Solare', '2025-10-20 08:15:00'),
(8, 18, 'Retea_Electrica', '2025-10-19 09:45:00'),
(9, 9, 'Generator_Diesel', '2025-10-18 10:30:00'),
(10, 25, 'Retea_Electrica', '2025-10-17 07:50:00'),
(11, 15, 'Panouri_Solare', '2025-10-16 12:20:00'),
(12, 21, 'Energie_Eoliana', '2025-10-15 14:40:00'),
(1, 30, 'Retea_Electrica', '2025-10-21 10:00:00'),
(2, 10, 'Retea_Electrica', '2025-10-21 11:00:00'),
(3, 5, 'Baterii', '2025-10-21 12:00:00'),
(4, 40, 'Generator', '2025-10-21 13:00:00');


------------------------------INSERT INTO Alarma--------------------------------------------

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(7, 'Incendiu', 'Critica', '2025-10-20 08:32:00', 'Activata'),
(8, 'Temperatura_Ridicata', 'Mare', '2025-10-19 09:10:00', 'Rezolvata'),
(9, 'Intrare_Neautorizata', 'Critica', '2025-10-18 22:45:00', 'Activata'),
(10, 'Scurgere_Gaz', 'Mare', '2025-10-17 11:25:00', 'Rezolvata'),
(11, 'Nivel_CO2_Crescut', 'Medie', '2025-10-16 14:05:00', 'Activata'),
(12, 'Pana_Curent', 'Scazuta', '2025-10-15 18:40:00', 'Rezolvata'),
(1, 'Inundatie', 'Mare', '2025-10-21 05:00:00', 'Rezolvata'),
(2, 'Geam_Spart', 'Critica', '2025-10-22 02:00:00', 'Activata'),
(3, 'Fum', 'Medie', '2025-10-23 14:00:00', 'Falsa'),
(4, 'Usa_Deschisa', 'Scazuta', '2025-10-24 16:00:00', 'Rezolvata');


-----------------------------INSERT INTO AlarmaCamera-----------------------------

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(1, 7, 'Aripa_Nord', '2025-10-20 08:35:00'),
(2, 8, 'Etajul_1', '2025-10-19 09:15:00'),
(3, 9, 'Hol_Principal', '2025-10-18 22:50:00'),
(4, 10, 'Subsol', '2025-10-17 11:30:00'),
(5, 11, 'Laborator', '2025-10-16 14:10:00'),
(6, 12, 'Sala_Servere', '2025-10-15 18:45:00'),
(7, 1, 'Parter', '2025-10-21 05:05:00'),
(8, 2, 'Etajul_2', '2025-10-22 02:05:00'),
(9, 3, 'Loc_Joaca', '2025-10-23 14:05:00'),
(10, 4, 'Depozit', '2025-10-24 16:05:00');


------------------------INSERT INTO Utilizator-----------------

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Andrei Popescu', 'andrei.popescu@example.com', 'Administrator', 5),
('Maria Ionescu', 'maria.ionescu@example.com', 'Tehnician', 3),
('Ionel Dumitrescu', 'ionel.dumitrescu@example.com', 'Operator', 2),
('Elena Georgescu', 'elena.georgescu@example.com', 'Manager', 4),
('Radu Marinescu', 'radu.marinescu@example.com', 'Vizitator', 1),
('Cristina Mihai', 'cristina.mihai@example.com', 'Analist_Securitate', 4),
('George Vasile', 'george.v@example.com', 'Paznic', 2),
('Alina Tudor', 'alina.t@example.com', 'Vizitator', 1),
('Mihai Viteazu', 'mihai.v@example.com', 'Director', 5),
('Simona Halep', 'simona.h@example.com', 'VIP', 5);


---------------------------INSERT INTO Utilizatori_Camera--------------------------

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(1, 7, '5', '06:00-22:00'),
(2, 8, '3', '08:00-18:00'),
(3, 9, '2', '09:00-17:00'),
(4, 10, '4', '07:00-20:00'),
(5, 11, '1', '10:00-16:00'),
(6, 12, '4', '06:00-23:00'),
(7, 1, '2', '00:00-24:00'),
(8, 2, '1', '08:00-12:00'),
(9, 3, '5', '09:00-18:00'),
(10, 4, '5', 'Non-Stop');


------------------------INSERT INTO Acutator----------------------------------------

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(7, 'Ventilator', 'Controleaza fluxul de aer din sala principala', 'Activ', 2),
(8, 'Valva_Apa', 'Regleaza debitul apei pentru sistemul de racire', 'Inactiv', 1),
(9, 'Motor_Usa', 'Controleaza usa automata de acces', 'Activ', 3),
(10, 'Lumina_LED', 'Sistem de iluminat automat pentru zona de lucru', 'Activ', 4),
(11, 'Pompa_Aer', 'Asigura ventilatia laboratorului', 'Inactiv', 2),
(12, 'Sistem_Incalzire', 'Regleaza temperatura in sala serverelor', 'Activ', 6),
(1, 'Jaluzele', 'Jaluzele automate', 'Activ', 1),
(2, 'Proiector', 'Proiector clasa', 'Inactiv', 5),
(3, 'Boxe', 'Sistem audio', 'Activ', 3),
(4, 'Aer_Conditionat', 'Racire depozit', 'Activ', 10);


-------------------------INSERT INTO Senzor-----------------------------------

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(7, 'Temperatura', '°C', 'Activ', '2025-01-15'),
(8, 'Umiditate', '%', 'Activ', '2025-02-10'),
(9, 'CO2', 'ppm', 'Inactiv', '2025-03-05'),
(10, 'Luminozitate', 'lux', 'Activ', '2025-01-20'),
(11, 'Presiune', 'Pa', 'Activ', '2025-02-28'),
(12, 'Nivel_Apa', 'litri', 'Inactiv', '2025-03-15'),
(1, 'Miscare', 'bool', 'Activ', '2025-04-01'),
(2, 'Zgomot', 'dB', 'Activ', '2025-04-05'),
(3, 'Fum', 'bool', 'Activ', '2025-04-10'),
(4, 'Umiditate', '%', 'Activ', '2025-04-15');


------------------------INSERT INTO AcutatorSenzor-------------------

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(1, 1, 'Automat'),
(2, 2, 'Automat'),
(3, 3, 'Manual'),
(4, 4, 'Automat'),
(5, 5, 'Manual'),
(6, 6, 'Automat'),
(7, 7, 'Automat'),
(8, 8, 'Manual'),
(9, 9, 'Automat'),
(10, 10, 'Automat');


--------------------------INSERT INTO Masuratori_mediu-------------------------------

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(1, 22, '°C', '2025-10-20 08:15:00'),
(2, 46, '%', '2025-10-19 09:30:00'),
(3, 415, 'ppm', '2025-10-18 10:45:00'),
(4, 305, 'lux', '2025-10-17 11:10:00'),
(5, 101325, 'Pa', '2025-10-16 12:20:00'),
(6, 120, 'litri', '2025-10-15 14:40:00'),
(7, 1, 'bool', '2025-10-21 08:00:00'),
(8, 55, 'dB', '2025-10-21 09:00:00'),
(9, 0, 'bool', '2025-10-21 10:00:00'),
(10, 60, '%', '2025-10-21 11:00:00');