package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

/**
 * Generic service interface for managing entities.
 *
 * @param <T> the type of the entity
 */
public interface GenericService<T> {

    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     */
    public List<T> findAll();

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id the identifier of the entity to retrieve
     * @return the entity with the specified identifier
     */
    public T findById(int id);

    /**
     * Creates a new entity.
     *
     * @param Object the entity to create
     * @return the created entity
     */
    public T save(T Object);

    /**
     * Deletes an entity.
     *
     * @param Object the entity to delete
     */
    public void delete(T Object);

}
