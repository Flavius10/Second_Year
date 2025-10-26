package org.example.repositories;

public interface RepoCrud<Entity> {

    void save(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
    Entity findById(Long id);
    Iterable<Entity> findAll();

}
