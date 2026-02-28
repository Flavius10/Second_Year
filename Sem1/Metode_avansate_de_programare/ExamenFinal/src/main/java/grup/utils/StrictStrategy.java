package grup.utils;

import java.util.List;
// import grup.domain.Entitate;

// SCHIMBA 'Object' CU NUMELE CLASEI TALE (ex: Car, User)
public class StrictStrategy implements IValidationStrategy<Object> {

    @Override
    public void validate(Object entityNou, List<Object> repoData) throws Exception {
        System.out.println("--- RULAM VALIDARE STRICTA ---");

        // EXEMPLU 1: Verificare Duplicat
        for (Object existent : repoData) {
            // if (existent.getId().equals(entityNou.getId())) {
            //     throw new Exception("EROARE: Exista deja acest ID!");
            // }
        }

        // EXEMPLU 2: Verificare Limita (ex: Maxim 3 produse)
        int count = repoData.size();
        if (count >= 3) {
            throw new Exception("EROARE: Ai atins limita maxima de 3 elemente!");
        }

        // EXEMPLU 3: Verificare Status (ex: E stricat?)
        // if (entityNou.getStatus() == "BROKEN") {
        //      throw new Exception("Nu poti adauga un element stricat!");
        // }
    }
}