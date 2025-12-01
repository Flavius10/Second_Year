USE SmartBuilding

IF NOT EXISTS (SELECT * FROM sys.tables WHERE NAME = 'Legaturi_Eliminate')
CREATE TABLE Legaturi_Eliminate(
	NumeTabelSt VARCHAR(128),
	IdSt INT,
	NumeTabelDr VARCHAR(128),
	IdDr VARCHAR(128),
	Mesaj NVARCHAR(128),
	DataModificarii DATETIME DEFAULT GETDATE()
);

SELECT * FROM Legaturi_Eliminate;