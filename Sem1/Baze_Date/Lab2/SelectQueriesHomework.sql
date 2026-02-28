USE SmartBuilding;

/*

WHERE - 5
GROUP BY - 5
DISTINCT - 2
HAVING - 2

INTEROGARI - 10

*/


--VREM SA SELECTAM CATE ALARME ARE FIECARE CAMERA IN PARTE--
--am utilizat un WHERE si un GROUP BY--
/*
SELECT 
	c.nume_camera,
	COUNT(a.id_alarma) AS numar_alarme
FROM AlarmaCamera ac
INNER JOIN Camera c
	ON ac.id_camera = c.id_camera
INNER JOIN Alarma a
	ON ac.id_alarma = a.id_alarma
GROUP BY c.nume_camera;
*/



--VREM SA SELECTAM CATI ACUTATORI ARE UN SENZOR CARE ARE ID-UL MAI MARE CA 2--
/*
SELECT 
	s.id_senzor,
	COUNT(a.id_acutator) AS numar_acutator
FROM AcutatorSenzor aSenzor
INNER JOIN Senzor s
	ON s.id_senzor = aSenzor.id_senzor
INNER JOIN Acutator a
	ON a.id_acutator = aSenzor.id_acutator
WHERE s.id_senzor > 2
	GROUP BY s.id_senzor;
*/

/*
--SELECTEAZA TOATE CAMERELE CU NUME CARE APARE DE MAI MULTE ORI IN TABELA CAMERE--
SELECT COUNT(id_camera) AS Numar_camere, nume_camera
FROM Camera
GROUP BY nume_camera
HAVING COUNT(id_camera) > 1;
*/

/*
--SELECT pentru a vedea consumul de energie al camerelor din fiecare cladire.--
SELECT 
	cl.nume AS Nume_Cladire, 
	ca.id_camera AS ID_Camera,
	SUM(e.energie_kWh) AS Energie_camera
FROM Cladire cl
INNER JOIN Camera ca
	ON cl.id_cladire = ca.id_cladire
INNER JOIN Energie_Consum e
	ON e.id_camera = ca.id_camera
WHERE ca.id_camera > 7
	GROUP BY cl.nume, ca.id_camera;
*/


/*
--SELECT pentru a obtine masuratori de mediu pe senzori si camere.--
SELECT DISTINCT
	c.id_camera,
	c.nume_camera,
	s.id_senzor,
	s.tip_senzor,
	m.id_masurare,
	m.valoare
FROM Camera c
INNER JOIN Senzor s
	ON s.id_camera = c.id_camera
INNER JOIN Masuratori_mediu m
	ON s.id_senzor = m.id_senzor
WHERE s.id_senzor > 3;
*/

	
/*
--SELECT pentru a vedea ce camerele care au cele mai multe alarme.--
SELECT
    c.id_camera,
    c.nume_camera,
    COUNT(DISTINCT a.id_alarma) AS nr_alarme
FROM AlarmaCamera ac
INNER JOIN Alarma a
	ON a.id_alarma = ac.id_alarma
INNER JOIN Camera c
	ON c.id_camera = ac.id_camera
WHERE ac.id_alarma > 2
GROUP BY c.nume_camera, c.id_camera
HAVING COUNT(c.id_camera) > 1

*/


/*
--SELECT pentru a afisa camerele si alarmele asociate lor.--
--(camereke trebuie sa aiba id mai mare de 8)--
SELECT
	c.nume_camera AS Nume_Camera,
	c.id_camera AS ID_Camera,
	a.id_alarma AS ID_Alarma
FROM AlarmaCamera ac
INNER JOIN Camera c
	ON c.id_camera = ac.id_camera
INNER JOIN Alarma a
	ON a.id_alarma = ac.id_alarma

WHERE c.id_camera > 8;
*/

/*
--Selectam toti userii de la fiecare camera--

SELECT
	c.id_camera AS ID_Camera,
	c.nume_camera AS Camera_Name,
	u.nume AS Nume_Utilizator
FROM Utilizator_Camera uc
INNER JOIN Camera c
	ON c.id_camera = uc.id_camera
INNER JOIN Utilizator u
	ON u.id_user = uc.id_user;
*/

/*
--Selectam cate alarme are fiecare camera din fiecare cladire--
SELECT
	cl.id_cladire,
	cl.nume,
	c.id_camera, 
	c.nume_camera,
	a.id_alarma
FROM Camera c
INNER JOIN AlarmaCamera ac
	ON ac.id_camera = c.id_camera
INNER JOIN Alarma a
	ON a.id_alarma = ac.id_alarma
INNER JOIN Cladire cl
	ON cl.id_cladire = c.id_cladire
*/

/*
--Selectam toti utilizatorii din fiecare cladire--
SELECT
	cl.id_cladire AS ID_Cladire,
	cl.nume AS Nume_Cladire,
	c.id_camera AS ID_Camera,
	c.nume_camera AS Nume_Camera,
	u.nume AS Nume_Utilizator
FROM Camera c
INNER JOIN Cladire cl
	ON cl.id_cladire = c.id_cladire
INNER JOIN Utilizator_Camera uc
	ON uc.id_camera = c.id_camera
INNER JOIN Utilizator u
	ON u.id_user = uc.id_user;
*/