package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.repo_file.RepoFileFriendship;

import java.util.List;
import java.util.stream.StreamSupport;

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

    public Friendship findByNames(String first_friend, String second_friend, String file_name){
        Iterable<Friendship> friends = this.findAllFriendships(file_name);

        return StreamSupport.stream(friends.spliterator(), false)
                .filter(f ->
                        (f.getFirst_friend_username().equals(first_friend) && f.getSecond_friend_username().equals(second_friend)) ||
                                (f.getFirst_friend_username().equals(second_friend) && f.getSecond_friend_username().equals(first_friend))
                )
                .findFirst()
                .orElse(null);

    }

    public Iterable<Friendship> findAllFriendships(String file_name){
        return this.repoFileFriendship.findAll(file_name);
    }

}
