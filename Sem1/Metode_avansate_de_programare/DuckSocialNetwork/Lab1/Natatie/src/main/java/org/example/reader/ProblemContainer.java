package org.example.reader;

public interface ProblemContainer<T> {
    void add(T item);
    boolean isEmpty();
    int size();
    T remove();
    T[] getAll();
}
