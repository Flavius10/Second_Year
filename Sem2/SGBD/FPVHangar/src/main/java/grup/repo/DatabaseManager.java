package grup.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/fpvdb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Flavius10";

    // Metoda pentru a obtine o conexiune la baza de date
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
