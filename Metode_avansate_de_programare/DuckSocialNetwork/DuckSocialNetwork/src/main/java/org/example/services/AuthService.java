package org.example.services;

import org.example.domain.User;
import org.example.repositories.RepoFilePersoana;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

   private final Map<Long, Boolean> loggedInUsers = new HashMap<>();
   private RepoFilePersoana persoanaRepo;

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

   public void signUp(User user){
       if (this.loggedInUsers.containsKey(user.getId()))
           throw new RuntimeException("User already exists!");

       this.persoanaRepo.save(user, "persoane.txt");
       this.loggedInUsers.put(user.getId(), true);
   }
}
