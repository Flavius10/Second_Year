package org.example.repositories;

/**
 * The interface Repo file.
 *
 * @param <Entity> the type parameter
 */
public interface RepoFile<Entity> {

    /**
     * Save.
     *
     * @param entity    the entity
     * @param file_name the file name
     */
    void save(Entity entity, String file_name);

    /**
     * Update.
     *
     * @param entity    the entity
     * @param file_name the file name
     */
    void update(Entity entity, String file_name);

    /**
     * Delete.
     *
     * @param entity    the entity
     * @param file_name the file name
     */
    void delete(Entity entity, String file_name);

    /**
     * Find by id entity.
     *
     * @param id        the id
     * @param file_name the file name
     * @return the entity
     */
    Entity findById(Long id, String file_name);

    /**
     * Find all iterable.
     *
     * @param file_name the file name
     * @return the iterable
     */
    Iterable<Entity> findAll(String file_name);

}
