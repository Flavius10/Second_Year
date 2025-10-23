/*
----------------------INSERT INTO Cladiri------------------------------

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Spital', 'Adresa_Spital', 100, 1);

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Scoala', 'Adresa_Scoala', 110, 0);

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Gradinita', 'Adresa_Gradinita', 120, 1);

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Magazin', 'Adresa_Magazin', 130, 0);

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Bloc_locuinte', 'Adresa_Bloc_locuinte', 140, 1);

INSERT INTO Cladire(nume, adresa, suprafata_totala, certificat_verde) VALUES
('Universitate', 'Adresa_Universitate', 150, 0);


--------------------------INSERT INTO Camera-----------------------------------

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(1, 'Camera Spital', 'Salon', 10, 0);

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(2, 'Camera Scoala', 'Clasa', 30, 1);

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(3, 'Camera Gradinita', 'Sala_Jocuri', 20, 0);

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(4, 'Camera Magazin', 'Depozit', 15, 1);

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(5, 'Camera Bloc Locuinte', 'Apartament', 4, 0);

INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(6, 'Camera Universitate', 'Amfiteatru', 100, 1);


----------------------INSERT INTO Predictii_AI----------------------------

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(7, 'Temperatura', 22.5, '2025-10-20 08:00:00', '2025-10-20 12:00:00', 0.92);

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(8, 'Umiditate', 46.1, '2025-10-19 09:00:00', '2025-10-19 13:00:00', 0.88);

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(9, 'Numar_persoane', 18, '2025-10-18 10:00:00', '2025-10-18 14:00:00', 0.95);

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(10, 'Consum_energie', 127.4, '2025-10-17 07:30:00', '2025-10-17 11:30:00', 0.90);

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(11, 'Nivel_CO2', 415.8, '2025-10-16 12:00:00', '2025-10-16 16:00:00', 0.93);

INSERT INTO Predictii_AI(id_camera, tip_predictie, valoare_prevazuta, timp_start, timp_end, acuratete) VALUES
(12, 'Luminozitate', 310.6, '2025-10-15 14:00:00', '2025-10-15 18:00:00', 0.89);


----------------------INSERT INTO Energie_Consum---------------------------------

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(7, 12.5, 'Panouri_Solare', '2025-10-20 08:15:00');

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(8, 18.2, 'Retea_Electrica', '2025-10-19 09:45:00');

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(9, 9.8, 'Generator_Diesel', '2025-10-18 10:30:00');

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(10, 25.6, 'Retea_Electrica', '2025-10-17 07:50:00');

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(11, 15.4, 'Panouri_Solare', '2025-10-16 12:20:00');

INSERT INTO Energie_Consum(id_camera, energie_kWh, sursa, timp_inregistrare) VALUES
(12, 21.1, 'Energie_Eoliana', '2025-10-15 14:40:00');


------------------------------INSERT INTO Alarma--------------------------------------------

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(7, 'Incendiu', 'Critica', '2025-10-20 08:32:00', 'Activata');

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(8, 'Temperatura_Ridicata', 'Mare', '2025-10-19 09:10:00', 'Rezolvata');

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(9, 'Intrare_Neautorizata', 'Critica', '2025-10-18 22:45:00', 'Activata');

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(10, 'Scurgere_Gaz', 'Mare', '2025-10-17 11:25:00', 'Rezolvata');

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(11, 'Nivel_CO2_Crescut', 'Medie', '2025-10-16 14:05:00', 'Activata');

INSERT INTO Alarma(id_camera, tip_alarma, prioritate, timp_declansare, status_alarma) VALUES
(12, 'Pana_Curent', 'Scazuta', '2025-10-15 18:40:00', 'Rezolvata');


-----------------------------INSERT INTO AlarmaCamera-----------------------------

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(1, 7, 'Aripa_Nord', '2025-10-20 08:35:00');

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(2, 8, 'Etajul_1', '2025-10-19 09:15:00');

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(3, 9, 'Hol_Principal', '2025-10-18 22:50:00');

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(4, 10, 'Subsol', '2025-10-17 11:30:00');

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(5, 11, 'Laborator', '2025-10-16 14:10:00');

INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(6, 12, 'Sala_Servere', '2025-10-15 18:45:00');

------------------------INSERT INTO Utilizator-----------------

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Andrei Popescu', 'andrei.popescu@example.com', 'Administrator', 5);

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Maria Ionescu', 'maria.ionescu@example.com', 'Tehnician', 3);

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Ionel Dumitrescu', 'ionel.dumitrescu@example.com', 'Operator', 2);

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Elena Georgescu', 'elena.georgescu@example.com', 'Manager', 4);

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Radu Marinescu', 'radu.marinescu@example.com', 'Vizitator', 1);

INSERT INTO Utilizator(nume, email, rol, nivel_acces) VALUES
('Cristina Mihai', 'cristina.mihai@example.com', 'Analist_Securitate', 4);


---------------------------INSERT INTO Utilizatori_Camera--------------------------

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(1, 7, 5, '06:00-22:00');

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(2, 8, 3, '08:00-18:00');

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(3, 9, 2, '09:00-17:00');

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(4, 10, 4, '07:00-20:00');

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(5, 11, 1, '10:00-16:00');

INSERT INTO Utilizator_Camera(id_user, id_camera, nivel_acces, interval_orar) VALUES
(6, 12, 4, '06:00-23:00');


------------------------INSERT INTO Acutator----------------------------------------

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(7, 'Ventilator', 'Controleaza fluxul de aer din sala principala', 'Activ', 2.5);

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(8, 'Valva_Apa', 'Regleaza debitul apei pentru sistemul de racire', 'Inactiv', 1.2);

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(9, 'Motor_Usa', 'Controleaza usa automata de acces', 'Activ', 3.1);

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(10, 'Lumina_LED', 'Sistem de iluminat automat pentru zona de lucru', 'Activ', 4.3);

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(11, 'Pompa_Aer', 'Asigura ventilatia laboratorului', 'Inactiv', 2.0);

INSERT INTO Acutator(id_camera, tip_acutator, descriere, status_acutator, energie_consumata) VALUES
(12, 'Sistem_Incalzire', 'Regleaza temperatura in sala serverelor', 'Activ', 5.6);

-------------------------INSERT INTO Senzor-----------------------------------

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(7, 'Temperatura', '°C', 'Activ', '2025-01-15');

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(8, 'Umiditate', '%', 'Activ', '2025-02-10');

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(9, 'CO2', 'ppm', 'Inactiv', '2025-03-05');

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(10, 'Luminozitate', 'lux', 'Activ', '2025-01-20');

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(11, 'Presiune', 'Pa', 'Activ', '2025-02-28');

INSERT INTO Senzor(id_camera, tip_senzor, unitate_masura, status_senzor, data_instalare) VALUES
(12, 'Nivel_Apa', 'litri', 'Inactiv', '2025-03-15');

------------------------INSERT INTO AcutatorSenzor-------------------

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(1, 1, 'Automat');

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(2, 2, 'Automat');
uit
INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(3, 3, 'Manual');

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(4, 4, 'Automat');

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(5, 5, 'Manual');

INSERT INTO AcutatorSenzor(id_acutator, id_senzor, mod_control) VALUES
(6, 6, 'Automat');


--------------------------INSERT INTO Masuratori_mediu-------------------------------

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(1, 22.7, '°C', '2025-10-20 08:15:00');

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(2, 46.5, '%', '2025-10-19 09:30:00');

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(3, 415, 'ppm', '2025-10-18 10:45:00');

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(4, 305, 'lux', '2025-10-17 11:10:00');

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(5, 101325, 'Pa', '2025-10-16 12:20:00');

INSERT INTO Masuratori_mediu(id_senzor, valoare, unitate, timestamp_masuratori) VALUES
(6, 120, 'litri', '2025-10-15 14:40:00');


INSERT INTO AlarmaCamera(id_alarma, id_camera, zona_afectata, timp_asociere) VALUES 
(5, 12, 'Sala_Servere', '2025-10-15 18:45:00');


INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(1, 'Camera Spital', 'Salon', 10, 0);


INSERT INTO Camera(id_cladire, nume_camera, tip_camera, capacitate, ocupata) VALUES 
(1, 'Camera Gradinita', 'Salon', 10, 0);

*/