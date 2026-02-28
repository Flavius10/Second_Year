USE SmartBuilding
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_UtilizatorCamera_User')
    DROP INDEX N_idx_UtilizatorCamera_User ON Utilizator_Camera;
GO
CREATE NONCLUSTERED INDEX N_idx_UtilizatorCamera_User 
ON Utilizator_Camera(id_user);
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_UtilizatorCamera_Camera')
    DROP INDEX N_idx_UtilizatorCamera_Camera ON Utilizator_Camera;
GO
CREATE NONCLUSTERED INDEX N_idx_UtilizatorCamera_Camera 
ON Utilizator_Camera(id_camera);
GO

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_Camera_Capacitate')
    DROP INDEX N_idx_Camera_Capacitate ON Camera;
GO
CREATE NONCLUSTERED INDEX N_idx_Camera_Capacitate 
ON Camera(capacitate);
GO

USE SmartBuilding
GO
SELECT * FROM Utilizator_Camera
WITH(INDEX(N_idx_UtilizatorCamera_User))
WHERE id_user = 1;

SELECT * FROM Camera 
WITH(INDEX(N_idx_Camera_Capacitate))
WHERE capacitate = 50;


SELECT * FROM Utilizator_Camera
WITH(INDEX(N_idx_UtilizatorCamera_Camera))
WHERE id_camera = 1;



GO
CREATE OR ALTER VIEW v_RaportAccesComplet
AS
SELECT 
    U.nume AS NumeUtilizator,
    U.email,
    U.rol,
    C.nume_camera AS CameraAccesibila,
    C.tip_camera,
    UC.interval_orar,
    UC.nivel_acces
FROM Utilizator U
INNER JOIN Utilizator_Camera UC ON U.id_user = UC.id_user
INNER JOIN Camera C ON UC.id_camera = C.id_camera;
GO

GO
CREATE OR ALTER VIEW v_CamereMari
AS
SELECT 
    nume_camera,
    tip_camera,
    capacitate,
    ocupata
FROM Camera
WHERE capacitate >= 10;
GO

PRINT 'Indexii si View-urile au fost create cu succes.';
SELECT * FROM v_CamereMari
SELECT * FROM v_RaportAccesComplet