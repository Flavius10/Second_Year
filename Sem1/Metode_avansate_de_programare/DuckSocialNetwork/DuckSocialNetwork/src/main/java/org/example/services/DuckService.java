package org.example.services;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBDuck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class DuckService {

    private final RepoDBDuck repoDBDuck;

    public DuckService(RepoDBDuck repoDBDuck){
        this.repoDBDuck = repoDBDuck;
    }

    public void saveDuck(Duck user){
        Optional<Duck> existing = repoDBDuck.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            throw new UserAlreadyExists("Duck already exists!");
        }

        user.setPassword(hashPassword(user.getPassword()));

        repoDBDuck.save(user);
    }

    public void updateDuck(Duck user){
        if (repoDBDuck.findOne(user.getId()).isEmpty()) {
            throw new UserNotFound("Nu s-a putut actualiza rata (nu a fost găsită în DB).");
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        }

        Optional<Duck> updated = repoDBDuck.update(user);
        if (updated.isEmpty()) {
            throw new UserNotFound("Eroare la update!");
        }
    }

    public void deleteDuck(Duck user){
        Optional<Duck> result = this.repoDBDuck.delete(user.getId());
        if (result.isEmpty())
            throw new UserNotFound("User not found!");
    }

    public User findByIdDuck(Long id) {
        return repoDBDuck.findOne(id)
                .orElseThrow(() -> new UserNotFound("User not found!"));
    }

    public Iterable<Duck> findAllDucks() {
        return repoDBDuck.findAll();
    }

    public Duck findByUsernameDuck(String username) {
        return repoDBDuck.findByUsername(username).orElse(null);
    }

    public Page<Duck> findAllOnPage(Pageable pageable) {
        return repoDBDuck.findAllOnPage(pageable);
    }

    public Iterable<Duck> findByType(TypeDuck type){
        return repoDBDuck.findByType(type);
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