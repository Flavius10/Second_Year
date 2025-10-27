package org.example.services;

import org.example.domain.User;
import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

   private final Map<Long, Boolean> loggedInUsers = new HashMap<>();
   private RepoFilePersoana repoPersoana;
   private RepoFileDuck repoDuck;

   public AuthService(RepoFilePersoana persoanaRepo, RepoFileDuck duckRepo) {
       this.repoPersoana = persoanaRepo;
       this.repoDuck = duckRepo;
   }

   public void login(User user, String password){
       if (user.getPassword().equals(password))
       {
           this.loggedInUsers.put(user.getId(), true);
           System.out.println("Login succesful!");
           return;
       }

       throw new RuntimeException("Wrong password!");
   }

   public void logout(User user){
       this.loggedInUsers.put(user.getId(), false);
   }

   public boolean isLoggedIn(User user){
        return this.loggedInUsers.getOrDefault(user.getId(), false);
   }

   public void signUp(User user, String file_name){
       if (this.loggedInUsers.containsKey(user.getId()))
           throw new RuntimeException("User already exists!");

       this.repoPersoana.save(user, file_name);
       this.loggedInUsers.put(user.getId(), true);
   }

    public User loginAndReturnUser(String username, String password) {
        for (User user : repoPersoana.findAll("persoane.txt")) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUsers.put(user.getId(), true);
                return user;
            }
        }

        for (User user : repoDuck.findAll("ducks.txt")) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUsers.put(user.getId(), true);
                return user;
            }
        }

        throw new RuntimeException("Nume de utilizator sau parola incorectă!");
    }
}
