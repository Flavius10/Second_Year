package grup;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CheckedExample {
    public static void main(String[] args) {
        File file = new File("nu_exista.txt");

        try {
            FileReader fr = new FileReader(file);
        } catch (IOException e) {
            System.out.println("Fișierul nu a fost găsit, dar am gestionat situația.");
        }
    }
}