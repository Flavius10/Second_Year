package org.example.repositories.messages;

/**
 * The interface Repo crud.
 *
 * @param <Entity> the type parameter
 */
public interface RepoCrud<Entity> {

    /**
     * Save.
     *
     * @param entity the entity
     */
    void save(Entity entity);

    /**
     * Update.
     *
     * @param entity the entity
     */
    void update(Entity entity);

    /**
     * Delete.
     *
     * @param entity the entity
     */
    void delete(Entity entity);

    /**
     * Find by id entity.
     *
     * @param id the id
     * @return the entity
     */
    Entity findById(Long id);

    /**
     * Find all iterable.
     *
     * @return the iterable
     */
    Iterable<Entity> findAll();

}
