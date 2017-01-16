package com.epamtraining.dao.interfaces;

import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * Base interface for working with entity.
 *
 * @autor Sergey Bondarenko
 */
public interface DaoBase<E, K> {
    /**
     * Return all results from DB.
     *
     * @return List/<E>
     */
    List<E> findAll() throws DAOLogicalException;

    /**
     * Get E entity by K ed from DB.
     *
     * @param id
     * @return E object
     */
    E findEntityById(K id) throws DAOLogicalException;

    /**
     * Update entity in DB.
     *
     * @param entity
     * @return E object
     */
    boolean update(E entity) throws DAOLogicalException;

    /**
     * Delete entity in DB by id.
     *
     * @param id
     * @return delete status
     */
    boolean delete(K id) throws DAOLogicalException;

    /**
     * Create Entity in DB.
     *
     * @param entity
     * @return creation status
     */
    boolean create(E entity) throws DAOLogicalException;
}
