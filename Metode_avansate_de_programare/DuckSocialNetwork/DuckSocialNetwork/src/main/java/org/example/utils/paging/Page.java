package org.example.utils.paging;

public class Page<E> {

    private Iterable<E> elements;
    private int totalNumberOfElements;

    public Page(Iterable<E> elements, int totalNumberOfElements){
        this.elements = elements;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public Iterable<E> getElementsOnPage(){
        return this.elements;
    }

    public int getTotalNumberOfElements(){
        return this.totalNumberOfElements;
    }

}
