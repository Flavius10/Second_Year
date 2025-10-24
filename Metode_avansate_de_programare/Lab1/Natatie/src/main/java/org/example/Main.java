package org.example;

import org.example.domain.*;
import org.example.execute.ExecuteProblem;
import org.example.reader.*;
import org.example.sorter.*;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Task executeProblem = new ExecuteProblem("1", "Test");
        executeProblem.execute();
    }
}