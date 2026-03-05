package grup;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    }

    public static String proceseazaParticipanti(List<String> participanti){

        String rezultat = Joiner.on(", ")
                .skipNulls()
                .join(participanti);

        System.out.println("Lista de participanti: " + rezultat);

        return rezultat;
    }
}