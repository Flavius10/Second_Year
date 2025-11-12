package org.example.repositories.repo_db;

import org.example.domain.User;

import java.util.Optional;

public interface ReopDB<ID, E extends User> {
    /**
     *
     * @param id
     *      id must be not null
     * @return an {@code Optional} - null if the entity is not found,
     *                             - the entity otherwise
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<E> findOne(ID id);

    /**
     *
     * @return all entities
     */
    Iterable<E> findAll();

    /**
     *
     * @param entity
     *         entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     *                             - the entity (id already exists)
     * @throws IllegalArgumentException
     *             if the given entity is null.     *
     */
    Optional<E> save(E entity);


    /**
     *  removes the entity with the specified id
     * @param id
     *      id must be not null
     * @return an {@code Optional}
     *            - null if there is no entity with the given id,
     *            - the removed entity, otherwise
     * @throws IllegalArgumentException
     *                   if the given id is null.
     */
    Optional<E> delete(ID id);

    /**
     *
     * @param entity
     *          entity must not be null
     * @return  an {@code Optional}
     *             - null if the entity was updated
     *             - otherwise (e.g. id does not exist) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    Optional<E> update(E entity);

    Optional<E> findByUsername(String username);

}
