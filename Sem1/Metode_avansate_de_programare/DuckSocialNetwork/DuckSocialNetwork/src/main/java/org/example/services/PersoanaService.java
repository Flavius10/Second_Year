package org.example.services;

import org.example.domain.Persoana;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBPersoana;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class PersoanaService {

    private final RepoDBPersoana repoDBPersoana;

    public PersoanaService(RepoDBPersoana repoDBPersoana) {
        this.repoDBPersoana = repoDBPersoana;
    }

    public void savePerson(Persoana user) {
        Optional<Persoana> existing = repoDBPersoana.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            throw new UserAlreadyExists("User with username " + user.getUsername() + " already exists!");
        }

        user.setPassword(hashPassword(user.getPassword()));

        repoDBPersoana.save(user);
    }

    public void updatePerson(Persoana user) {
        if (repoDBPersoana.findOne(user.getId()).isEmpty()) {
            throw new UserNotFound("User not found (could not update)!");
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        }

        Optional<Persoana> updated = repoDBPersoana.update(user);
        if (updated.isEmpty()) {
            throw new UserNotFound("User not found (could not update)!");
        }
    }

    public void deletePerson(Persoana user) {
        Optional<Persoana> deleted = this.repoDBPersoana.delete(user.getId());
        if (deleted.isEmpty()) {
            throw new UserNotFound("User not found (could not delete)!");
        }
    }

    public Persoana findByIdPerson(Long id) {
        return this.repoDBPersoana.findOne(id)
                .orElseThrow(() -> new UserNotFound("User with id " + id + " not found!"));
    }

    public Iterable<Persoana> findAllPersons() {
        return this.repoDBPersoana.findAll();
    }

    public Persoana findByUsernamePerson(String username) {
        return repoDBPersoana.findByUsername(username).orElse(null);
    }

    public Page<Persoana> findAllOnPage(Pageable pageable) {
        return repoDBPersoana.findAllOnPage(pageable);
    }

    private String hashPassword(String plainPassword) {
        if (plainPassword == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Eroare criptare", e);
        }
    }
}