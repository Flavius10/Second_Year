package org.example.repositories.repo_file;

import org.example.domain.Persoana;
import java.time.LocalDate;

public class RepoFilePersoana extends AbstractFileRepo<Persoana> {

    @Override
    protected Persoana fromStringFile(String line) {
        String[] parts = line.split(";");
        return new Persoana(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                LocalDate.parse(parts[7])
        );
    }

    @Override
    protected String toStringFile(Persoana p) {
        return p.getId() + ";" +
                p.getUsername() + ";" +
                p.getEmail() + ";" +
                p.getPassword() + ";" +
                p.getNume() + ";" +
                p.getPrenume() + ";" +
                p.getOcupatie() + ";" +
                p.getDataNastere();
    }

    @Override
    protected String getUsername(Persoana entity) {
        return entity.getUsername();
    }
}
