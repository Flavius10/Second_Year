package grup;

import grup.domain.Persoana;
import grup.repository.RepositoryPersoana;

import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();

        try(InputStream input = Main.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                System.out.println("Nu am putut gasi fisierul config.properties");
                return;
            }
            props.load(input);

            String url = props.getProperty("jdbc.url");

            RepositoryPersoana repositoryPersoana = new RepositoryPersoana(url);

            repositoryPersoana.adauga(new Persoana(1L, "Ionescu", "Ion", 30));
            System.out.println("Am adaugat Ionescu Ion, 30 de ani.");

            System.out.println("\nPersoane peste 25 de ani (inainte de modificare):");
            repositoryPersoana.gasesteDupaConditie(25).forEach(System.out::println);

            System.out.println("\nExecutam modificarea persoanei cu ID 1...");
            repositoryPersoana.modifica(new Persoana(1L, "Popescu", "Ion", 26));

            System.out.println("Persoane peste 25 de ani (dupa modificare):");
            repositoryPersoana.gasesteDupaConditie(27).forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Eroare la incarcarea proprietatilor: " + e.getMessage());
            return;
        }
    }
}