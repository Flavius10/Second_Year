package org.example;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Sem5_2025 {
    /**
     * * 1. Lista studentilor care contin un anumit string (parametru), sortati descrescator
     * in functie de media lor
     */
    public static void report1_0(List<Nota> note, String string) {
        Map<Student, List<Nota>> res = note.stream()
                .collect(Collectors.groupingBy(Nota::getStudent));
        res.entrySet().stream()
                .map((x) -> {
                    String studentName = x.getKey().getName();
                    double avg = x.getValue().stream()
                            .mapToDouble(Nota::getValue)
                            .average()
                            .orElse(0d);
                    return new AbstractMap.SimpleEntry<>(studentName, avg);
                }).forEach(x-> System.out.println(x.getKey() + " : " + x.getValue()));
    }
    public static void report1_1(List<Nota> note, String string) {
        Map<Student, List<Nota>> studentsGrades = note
                .stream()
                //.collect(Collectors.groupingBy(Nota::getStudent));
                .collect(Collectors.groupingBy(n-> n.getStudent()));
        studentsGrades.entrySet()
                .stream()
                .filter(x -> x.getKey().getName().contains(string))
                .sorted((o1, o2) -> {
                    double avg1 = average(o1.getValue());
                    double avg2 = average(o2.getValue());
                    return -Double.compare(avg1, avg2);
                })
                .forEach(x -> System.out.println(x.getKey().getName() + "; media: " + average(x.getValue())));
    }

    private static double average(List<Nota> notaList) {
        double sum = notaList.stream()
                .map(Nota::getValue)
                .reduce(0d, Double::sum);
        return sum / notaList.size();
    }

    public static void report1_2(List<Nota> note, String string) {
        Map<Student, Double> studentAverages = note.stream()
                .collect(Collectors.groupingBy(
                        Nota::getStudent,
                        Collectors.averagingDouble(Nota::getValue)
                ));

        studentAverages.entrySet().stream()
                .filter(e -> e.getKey().getName().toLowerCase().contains(string.toLowerCase()))
                .sorted(Map.Entry.<Student, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getName() + "; media: " + e.getValue()));

    }

    public static void report2(List<Nota> note, String string){
        Map<String, Double> profesor = note.stream()
                .collect(Collectors.groupingBy(
                        Nota::getProfesor,
                        Collectors.averagingDouble(Nota::getValue)
                ));

        profesor.entrySet().stream().
                filter(e -> e.getKey().toLowerCase().contains(string.toLowerCase()))
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + "; media: " + e.getValue()));

    }

    public static void report3(List<Nota> note, Integer group){

        note.stream()
                //.filter(n -> n.getStudent().getGroup() == group)
                .collect(Collectors.groupingBy(
                        Nota::getTema,
                        Collectors.mapping(Nota::getStudent, Collectors.toSet())
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach(x -> System.out.println(x.getKey().getId() + " -- nr studenti: " + x.getValue().size()));
    }



    public static void main(String[] args) {
        List<Nota> note = getNote(getStudents(), getTeme());
//        report1_0(note, "");
//        report1_1(note, "");
//        report1_2(note, "");

        //report2(note, "");
        report3(note, 221);


    }

    private static List<Student> getStudents() {
        Student s1 = new Student("andrei", 221);
        s1.setId(1L);
        Student s2 = new Student("dan", 222);
        s2.setId(2L);
        Student s3 = new Student("gigi", 221);
        s3.setId(3L);
        Student s4 = new Student("costel", 222);
        s4.setId(4L);
        return Arrays.asList(s1, s2, s3, s4);
    }

    private static List<Tema> getTeme() {
        return Arrays.asList(
                new Tema("t1", "desc1"),
                new Tema("t2", "desc2"),
                new Tema("t3", "desc3"),
                new Tema("t4", "desc4")
        );
    }

    private static List<Nota> getNote(List<Student> stud, List<Tema> teme) {
        return Arrays.asList(
                new Nota(stud.get(0), teme.get(0), 10d, LocalDate.of(2019, 11, 2), "profesor1"),
                new Nota(stud.get(1), teme.get(0), 9d, LocalDate.of(2019, 11, 2).minusWeeks(1), "profesor1"),
                new Nota(stud.get(1), teme.get(1), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(1), teme.get(2), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(2), teme.get(1), 7d, LocalDate.of(2019, 10, 28), "profesor1"),
                new Nota(stud.get(2), teme.get(3), 9d, LocalDate.of(2019, 10, 27), "profesor2"),
                new Nota(stud.get(1), teme.get(3), 10d, LocalDate.of(2019, 10, 29), "profesor2"),
                new Nota(stud.get(3), teme.get(3), 8d, LocalDate.of(2019, 10, 29), "profesor1"),
                new Nota(stud.get(3), teme.get(2), 10d, LocalDate.of(2019, 10, 29), "profesor1")
        );
    }
}
