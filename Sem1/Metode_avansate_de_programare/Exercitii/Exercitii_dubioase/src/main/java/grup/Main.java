package grup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class ExceptionTest extends Error {
    public ExceptionTest(String message) {
        super(message);
    }
}

class C {
    public void writeData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Folders\\Facultate\\Anul_2\\Metode_avansate_de_programare\\Exercitii\\Exercitii_dubioase\\src\\main\\resources\\file.txt"));
            writer.write("A");
            writer.write("B");
            writer.close();
            writer.newLine();
            writer.write("C");
        } catch (IOException io) {
            throw new ExceptionTest("Error");
        }
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        C obj = new C();
        try {
            obj.writeData();
        } catch (ExceptionTest ex) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Folders\\Facultate\\Anul_2\\Metode_avansate_de_programare\\Exercitii\\Exercitii_dubioase\\src\\main\\resources\\file.txt"));
                writer.write(ex.getMessage());
                writer.close();
            } catch (IOException io) {
            }
        }
    }


}
