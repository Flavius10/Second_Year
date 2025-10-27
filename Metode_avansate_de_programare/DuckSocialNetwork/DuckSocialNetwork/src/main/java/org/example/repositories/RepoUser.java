package org.example.repositories;

import org.example.domain.User;

public interface RepoUser extends RepoFile<User>{

    User findByUsername(String username, String file_name);

}
