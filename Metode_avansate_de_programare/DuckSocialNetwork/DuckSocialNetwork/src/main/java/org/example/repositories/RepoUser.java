package org.example.repositories;

import org.example.domain.User;

/**
 * The interface Repo user.
 */
public interface RepoUser extends RepoFile<User>{

    /**
     * Find by username user.
     *
     * @param username  the username
     * @param file_name the file name
     * @return the user
     */
    User findByUsername(String username, String file_name);

}
