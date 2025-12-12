package org.example.repositories.repo_file;

import org.example.domain.friendship.Friendship;

/**
 * The type Repo file friendship.
 */
public class RepoFileFriendship extends AbstractFileRepo<Friendship>{


    @Override
    protected Friendship fromStringFile(String line){
        String[] parts = line.split(";");
        return new Friendship(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2]
        );
    }

    @Override
    protected String toStringFile(Friendship f) {
        return f.getId() + ";" +
                f.getFirst_friend_username() + ";" +
                f.getSecond_friend_username();
    }

    @Override
    protected String getUsername(Friendship entity) {
        return entity.getFirst_friend_username() + ' ' + entity.getSecond_friend_username();
    }
}
