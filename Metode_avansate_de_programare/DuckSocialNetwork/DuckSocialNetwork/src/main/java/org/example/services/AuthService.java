package org.example.services;

import org.example.domain.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

   private Map<Long, Boolean> loggedInUsers = new HashMap<>();

   public void login(User user, String password){
       if (user.getPassword().equals(password))
       {
           this.loggedInUsers.put(user.getId(), true);
           System.out.println("Login succesful!");
       }

       throw new RuntimeException("Wrong password!");
   }

   public void logout(User user){
       this.loggedInUsers.put(user.getId(), false);
   }

   public boolean isLoggedIn(User user){
       return this.loggedInUsers.get(user.getId());
   }
}
