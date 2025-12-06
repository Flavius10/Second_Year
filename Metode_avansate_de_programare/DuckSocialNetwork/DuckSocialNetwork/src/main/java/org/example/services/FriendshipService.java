package org.example.services;

import org.example.domain.Friendship;
import org.example.repositories.repo_db.RepoDBFriendship;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;
import org.example.validator.ValidatorFriendship;

import java.util.Optional;

public class FriendshipService {

    private RepoDBFriendship repoDBFriendship;

    public FriendshipService(RepoDBFriendship repoDBFriendship) {
        this.repoDBFriendship = repoDBFriendship;
    }

    public void saveFriendship(Friendship friendship){
        Optional<Friendship> existing = findByNamesOptional(friendship.getFirst_friend_username(), friendship.getSecond_friend_username());

        if (existing.isPresent()) {
            throw new RuntimeException("Friendship already exists!");
        }
        repoDBFriendship.save(friendship);
    }

    public void deleteFriendship(Friendship friendship){
        Optional<Friendship> deleted = this.repoDBFriendship.delete(friendship.getId());

        if (deleted.isEmpty())
            throw new RuntimeException("Friendship not found!");
    }

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

    public Page<Friendship> findAllOnPage(Pageable pageable) {
        return repoDBFriendship.findAllOnPage(pageable);
    }
}