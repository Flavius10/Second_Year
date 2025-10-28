package org.example.repositories;

import org.example.exceptions.UserAlreadyExists;

public interface RepoFile<Entity> {

    void save(Entity entity, String file_name);
    void update(Entity entity, String file_name);
    void delete(Entity entity, String file_name);
    Entity findById(Long id, String file_name);
    Iterable<Entity> findAll(String file_name);

}
