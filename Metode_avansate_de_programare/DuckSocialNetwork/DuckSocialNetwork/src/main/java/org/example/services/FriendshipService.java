package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.repo_file.RepoFileFriendship;

public class FriendshipService {

    private RepoFileFriendship repoFileFriendship;

    public FriendshipService(RepoFileFriendship repoFileFriendship) {
        this.repoFileFriendship = repoFileFriendship;
    }

    public void saveFriendship(Friendship friendship, String file_name){

        try{
            this.repoFileFriendship.save(friendship, file_name);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFriendship(Friendship friendship, String file_name){
        try{
            this.repoFileFriendship.delete(friendship, file_name);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
