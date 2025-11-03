package org.example.repositories.repo_file;

import org.example.domain.ducks.Card;
import org.example.domain.ducks.Duck;
import org.example.domain.TypeDuck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;

import java.util.Collections;
import java.util.List;

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
            List<Duck> list = Collections.emptyList();
            card = new Card(10L, parts[7], list);
        }

        if (tip == TypeDuck.FLYING) {
            return new FlyingDuck(id, username, email, password, tip, viteza, rezistenta, card);
        } else {
            return new SwimmingDuck(id, username, email, password, tip, viteza, rezistenta, card);
        }

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
                (d.getCard() != null ? d.getCard().getNumeCard() : "");
    }

    @Override
    protected String getUsername(Duck entity) {
        return entity.getUsername();
    }
}
