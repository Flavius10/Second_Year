package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.repo_db.RepoDBFriendship;
import org.example.repositories.repo_file.RepoFileFriendship;
import org.example.validator.ValidatorFriendship;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * The type Friendship service.
 */
public class FriendshipService {

    private RepoDBFriendship repoDBFriendship;
    private ValidatorFriendship validatorFriendship = new ValidatorFriendship();

    /**
     * Instantiates a new Friendship service.
     */
    public FriendshipService(RepoDBFriendship repoDBFriendship) {
        this.repoDBFriendship = repoDBFriendship;
    }

    /**
     * Save friendship.
     *
     * @param friendship the friendship
     */
    public void saveFriendship(Friendship friendship){

        Optional<Friendship> saved = repoDBFriendship.save(friendship);

        if (saved.isPresent())
            throw new RuntimeException("Friendship already exists!");

    }

    /**
     * Delete friendship.
     *
     * @param friendship the friendship
     */
    public void deleteFriendship(Friendship friendship){

        Optional<Friendship> deleted = this.repoDBFriendship.delete(friendship.getId());

        if (deleted.isEmpty())
            throw new RuntimeException("Friendship not found!");
    }

    /**
     * Find by names friendship.
     *
     * @param first_friend  the first friend
     * @param second_friend the second friend
     * @return the friendship
     */
    public Friendship findByNames(String first_friend, String second_friend){
        Iterable<Friendship> friendships = this.repoDBFriendship.findAll();
        for(Friendship friendship: friendships){
            if((friendship.getFirst_friend_username().equals(first_friend) && friendship.getSecond_friend_username().equals(second_friend)) ||
                    (friendship.getFirst_friend_username().equals(second_friend) && friendship.getSecond_friend_username().equals(first_friend))){
                return friendship;
            }
        }
        throw new RuntimeException("Friendship not found!");
    }

    /**
     * Find all friendships iterable.
     * @return the iterable
     */
    public Iterable<Friendship> findAllFriendships(){
        return repoDBFriendship.findAll();
    }


    public Optional<Friendship> findByNamesOptional(String first_friend, String second_friend) {
        Iterable<Friendship> friendships = this.repoDBFriendship.findAll();
        for (Friendship friendship : friendships) {
            if ((friendship.getFirst_friend_username().equals(first_friend) &&
                    friendship.getSecond_friend_username().equals(second_friend)) ||
                    (friendship.getFirst_friend_username().equals(second_friend) &&
                            friendship.getSecond_friend_username().equals(first_friend))) {
                return Optional.of(friendship);
            }
        }
        return Optional.empty();
    }

}
