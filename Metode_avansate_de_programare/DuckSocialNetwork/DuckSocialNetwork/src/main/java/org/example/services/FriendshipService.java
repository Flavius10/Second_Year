package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.repo_file.RepoFileFriendship;
import org.example.validator.ValidatorFriendship;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * The type Friendship service.
 */
public class FriendshipService {

    private RepoFileFriendship repoFileFriendship;
    private ValidatorFriendship validatorFriendship = new ValidatorFriendship();

    /**
     * Instantiates a new Friendship service.
     *
     * @param repoFileFriendship the repo file friendship
     */
    public FriendshipService(RepoFileFriendship repoFileFriendship) {
        this.repoFileFriendship = repoFileFriendship;
    }

    /**
     * Save friendship.
     *
     * @param friendship the friendship
     * @param file_name  the file name
     */
    public void saveFriendship(Friendship friendship, String file_name){

        try{
            if(this.validatorFriendship.validate(friendship.getSecond_friend_username(),
                    friendship.getSecond_friend_username()))
            {
                this.repoFileFriendship.save(friendship, file_name);
            } else {
                throw new RuntimeException("Friendship is not valid!");
            }
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete friendship.
     *
     * @param friendship the friendship
     * @param file_name  the file name
     */
    public void deleteFriendship(Friendship friendship, String file_name){
        try{
            this.repoFileFriendship.delete(friendship, file_name);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find by names friendship.
     *
     * @param first_friend  the first friend
     * @param second_friend the second friend
     * @param file_name     the file name
     * @return the friendship
     */
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

    /**
     * Find all friendships iterable.
     *
     * @param file_name the file name
     * @return the iterable
     */
    public Iterable<Friendship> findAllFriendships(String file_name){
        return this.repoFileFriendship.findAll(file_name);
    }

}
