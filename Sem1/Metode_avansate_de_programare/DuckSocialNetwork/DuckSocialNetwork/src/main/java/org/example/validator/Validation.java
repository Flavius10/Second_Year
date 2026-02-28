package org.example.validator;

public interface Validation<T, E> {
    boolean validate(T t, E e);
}
