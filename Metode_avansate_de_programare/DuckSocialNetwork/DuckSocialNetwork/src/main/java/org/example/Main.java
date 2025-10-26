package org.example;

import org.example.domain.Persoana;
import org.example.domain.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        User persoana = new Persoana(1L, "john_doe", "flaviusa1010@gmail.com", "123456");
        System.out.println(persoana.getUsername() + ":");


    }
}