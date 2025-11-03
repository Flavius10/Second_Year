USE SmartBuilding;
GO

IF OBJECT_ID('BringDatabaseAtVersion', 'P') IS NOT NULL
    DROP PROCEDURE BringDatabaseAtVersion;
GO
PRINT 'Am sters procedura veche. O cream din nou...';
GO

CREATE PROCEDURE BringDatabaseAtVersion(@VersiuneTinta INT) AS
BEGIN
    DECLARE @VersiuneaCurenta INT;
    SELECT @VersiuneaCurenta = Versiune FROM VersiuneDB;
    PRINT 'Versiune curenta: ' + CAST(@VersiuneaCurenta AS VARCHAR(10));
    PRINT 'Versiune tinta: ' + CAST(@VersiuneTinta AS VARCHAR(10));

    IF @VersiuneTinta > 5 OR @VersiuneTinta < 0
    BEGIN
        PRINT 'Eroare: Versiunea tinta trebuie sa fie intre 0 si 5.'; RETURN;
    END

    WHILE @VersiuneaCurenta < @VersiuneTinta
    BEGIN
        PRINT 'Facem upgrade de la ' + CAST(@VersiuneaCurenta AS VARCHAR(10)) + ' la ' + CAST(@VersiuneaCurenta + 1 AS VARCHAR(10));
        
        IF @VersiuneaCurenta = 0
            EXEC do_proc_3;
        ELSE IF @VersiuneaCurenta = 1
            EXEC do_proc_2;
        ELSE IF @VersiuneaCurenta = 2
            EXEC do_proc_1;
        ELSE IF @VersiuneaCurenta = 3
            EXEC do_proc_4;
        ELSE IF @VersiuneaCurenta = 4
            EXEC do_proc_5;
        
        SET @VersiuneaCurenta = @VersiuneaCurenta + 1;
        UPDATE VersiuneDB SET Versiune = @VersiuneaCurenta;
    END


    WHILE @VersiuneaCurenta > @VersiuneTinta
    BEGIN
        PRINT 'Facem downgrade de la ' + CAST(@VersiuneaCurenta AS VARCHAR(10)) + ' la ' + CAST(@VersiuneaCurenta - 1 AS VARCHAR(10));

        IF @VersiuneaCurenta = 5
            EXEC undo_proc_5;
        ELSE IF @VersiuneaCurenta = 4
            EXEC undo_proc_4;
        ELSE IF @VersiuneaCurenta = 3
            EXEC undo_proc_1;
        ELSE IF @VersiuneaCurenta = 2
            EXEC undo_proc_2;
        ELSE IF @VersiuneaCurenta = 1
            EXEC undo_proc_3;
            
        SET @VersiuneaCurenta = @VersiuneaCurenta - 1;
        UPDATE VersiuneDB SET Versiune = @VersiuneaCurenta;
    END
    
    PRINT 'Gata! Baza de date este acum la versiunea ' + CAST(@VersiuneaCurenta AS VARCHAR(10));
END
GO