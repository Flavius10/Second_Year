import grup.Main;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testMain(){

        List<String> listaCuNull = Arrays.asList("Flavius", null, "Luca");

        String rezultat = Main.proceseazaParticipanti(listaCuNull);
        assertEquals("Flavius, Luca", rezultat, "Inca e null in valoare");
    }

    @Test
    public void testMain2(){

        List<String> listaFaraNull = Arrays.asList("Flavius", "Maria", "Luca");

        String rezultat = Main.proceseazaParticipanti(listaFaraNull);
        assertEquals("Flavius, Maria, Luca", rezultat, "Nu mai exista null in valoare");
    }



}
