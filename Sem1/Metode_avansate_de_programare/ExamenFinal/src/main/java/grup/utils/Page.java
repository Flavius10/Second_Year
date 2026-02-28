package grup.utils;

import java.util.List;

public class Page<E> {
    private List<E> elementsOnPage; // Lista scurta (ex: 5 elemente)
    private int totalElements;      // Totalul din baza de date (ex: 100)

    public Page(List<E> elementsOnPage, int totalElements) {
        this.elementsOnPage = elementsOnPage;
        this.totalElements = totalElements;
    }
    public List<E> getElementsOnPage() { return elementsOnPage; }
    public int getTotalElements() { return totalElements; }
}