package org.example.repositories.repo_file;

import org.example.domain.Card;
import org.example.domain.Duck;
import org.example.domain.TypeDuck;

public class RepoFileDuck extends AbstractFileRepo<Duck> {

    @Override
    protected Duck fromStringFile(String line) {
        String[] parts = line.split(";");

        Long id = Long.parseLong(parts[0]);
        String username = parts[1];
        String email = parts[2];
        String password = parts[3];
        TypeDuck tip = TypeDuck.valueOf(parts[4]);
        double viteza = Double.parseDouble(parts[5]);
        double rezistenta = Double.parseDouble(parts[6]);

        Card card = null;
        if (parts.length > 7 && !parts[7].isEmpty()) {
            card = new Card(parts[7]);
        }

        return new Duck(id, username, email, password, tip, viteza, rezistenta, card);
    }

    @Override
    protected String toStringFile(Duck d) {
        return d.getId() + ";" +
                d.getUsername() + ";" +
                d.getEmail() + ";" +
                d.getPassword() + ";" +
                d.getTip() + ";" +
                d.getViteza() + ";" +
                d.getRezistenta() + ";" +
                (d.getCard() != null ? d.getCard().getNume() : "");
    }

    @Override
    protected String getUsername(Duck entity) {
        return entity.getUsername();
    }
}
