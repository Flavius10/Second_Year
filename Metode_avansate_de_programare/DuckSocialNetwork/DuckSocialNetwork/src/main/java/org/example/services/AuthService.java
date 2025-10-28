package org.example.services;

import org.example.domain.Duck;
import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AuthService {

   private final Map<Long, Boolean> loggedInUsers = new HashMap<>();
   private PersoanaService persoanaService;
   private DuckService duckService;

   public AuthService(PersoanaService persoanaService, DuckService duckService) {
       this.persoanaService = persoanaService;
       this.duckService = duckService;
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

    public void signUp(User user, String file_name) {

        if (user instanceof Persoana) {

            try{
                this.persoanaService.savePerson((Persoana) user, file_name);
            } catch (UserAlreadyExists e)
            {
                throw new UserAlreadyExists(e.getMessage());
            }

        } else if (user instanceof Duck) {

            try{
                this.duckService.saveDuck((Duck) user, file_name);
            } catch(UserAlreadyExists e)
            {
                throw new UserAlreadyExists(e.getMessage());
            }

        } else {
            throw new RuntimeException("Unknown user type");
        }

        this.loggedInUsers.put(user.getId(), true);
    }

    public User loginAndReturnUser(String username, String password) {

        Stream<User> persoaneStream = StreamSupport.stream(
                persoanaService.findAllPersons("persoane.txt").spliterator(), false);

        Stream<User> ducksStream = StreamSupport.stream(
                duckService.findAllDucks("ducks.txt").spliterator(), false);

        User user = Stream.concat(persoaneStream, ducksStream)
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nume de utilizator sau parola incorectă!"));

        loggedInUsers.put(user.getId(), true);
        return user;
    }


}
