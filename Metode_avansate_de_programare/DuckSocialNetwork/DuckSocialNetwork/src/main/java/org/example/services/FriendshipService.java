package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.RepoFriendship;

public class FriendshipService {

    private RepoFriendship repoFriendship;

    public FriendshipService(RepoFriendship repoFriendship) {
        this.repoFriendship = repoFriendship;
    }

    public void saveFriendship(Friendship friendship, String file_name){

        try{
            this.repoFriendship.save(friendship, file_name);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFriendship(Friendship friendship, String file_name){
        try{
            this.repoFriendship.delete(friendship, file_name);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
