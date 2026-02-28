package grup.utils;

// Importa Entitatea ta (Car, Book, Room, etc.)
// import grup.domain.Entitate;
// import grup.service.ServiceEntitate;

public interface IValidationStrategy<T> {

    /**
     * Metoda care decide daca operatiunea e permisa sau nu.
     * @param entity - Obiectul nou pe care vrei sa il adaugi/modifici
     * @param repoData - Lista cu datele existente (ca sa faci verificari de istoric)
     * @throws Exception - Daca validarea pica, arunca mesajul de eroare
     */
    void validate(T entity, java.util.List<T> repoData) throws Exception;
}