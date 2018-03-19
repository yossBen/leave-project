/*
 * PersistentDAO - DAO for Persistent objects
 */
package com.leave.dao;

import com.leave.entity.AbstractEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * DAO for Persistent objects
 */
public interface PersistentDAO<K extends Serializable, T extends AbstractEntity> {

    /**
     * Get a persistent object from its id
     *
     * @param id the persistent object's id
     * @return a Persistent object (may be null)
     */
    T get(K id);

    /**
     * Load a persistent object from its id
     *
     * @param id the persistent object's id
     * @return a Persistent object (may be null)
     */
    T load(K id);

    /**
     * Get a persistent object from one of its unique keys
     *
     * @param property the unique property's name
     * @param value    the unique property's value
     * @return a Persistent object (may be null)
     */
    T findByUniqueKey(String property, final Object value);

    /**
     * Get all persistent objects of a class
     *
     * @return a List of Persistent objects
     */
    List<T> findAll();

    /**
     * Get all persistent objects of a class, sorted by some fields
     *
     * @param sort the sort columns
     * @return a List of Persistent objects
     */
    List<T> findAllSort(String[] sort);

    /**
     * Get all persistent objects of a class, filtered by criteria
     *
     * @param where the criteria
     * @return a List of Persistent objects
     */
    List<T> findAllWhere(Criterion[] where);

    /**
     * Perform a query on the class, with some projections
     *
     * @param projection the projections
     * @return a List of Persistent objects
     */
    List<T> findAllProjection(Projection[] projection);

    /**
     * Get all persistent objects of a class, filtered by criteria and sorted by
     * some fields
     *
     * @param where the criteria
     * @param sort  the sort columns
     * @return a List of Persistent objects
     */
    List<T> findAllWhereSort(Criterion[] where, String[] sort);

    /**
     * Get all persistent objects of a class, filtered by criteria and sorted by
     * some fields, for a range of results
     *
     * @param where      the criteria
     * @param projection the projections
     * @param sort       the sort columns
     * @param start      the start index for the result list (starts at 0)
     * @param count      the maximum number of results
     * @return a List of Persistent objects
     */
    List<T> findAllWhereProjectionSortRange(Criterion[] where, Projection[] projection, String[] sort, int start, int count);

    /**
     * Runs the named query.
     *
     * @param queryName name of the query
     * @return
     */
    List<T> findByNamedQuery(String queryName);

    /**
     * Runs the named query limiting the result set to the offsets provided
     *
     * @param queryName name of the query
     * @param start     row index of the first row to return (0-based), -1 to ignore
     * @param count     number of rows to return (-1 to ignore and return the complete
     *                  set)
     * @return
     */
    List<T> findByNamedQueryRange(String queryName, int start, int count);

    /**
     * Runs the named query with the supplied parameters.
     *
     * @param queryName
     * @param params
     * @return
     */
    List<T> findByNamedQuery(String queryName, Object[] params);

    /**
     * Runs the NamedQuery with the supplied parameters, limiting the resultset
     * according to the index provided.
     *
     * @param queryName name of the query
     * @param params    parameter values
     * @param start     row index of the first row to return (0-based), -1 to ignore
     * @param count     number of rows to return (-1 to ignore and return the complete
     *                  set)
     * @return
     */
    List<T> findByNamedQueryRange(String queryName, Object[] params, int start, int count);

    /**
     * Runs the NamedQuery (it should be a DELETE or UPDATE query), and returns
     * the number of affected entries.
     *
     * @param queryName
     * @return
     */
    int executeNamedQuery(String queryName);

    /**
     * Runs the NamedQuery (it should be a DELETE or UPDATE query), and returns
     * the number of affected entries.
     *
     * @param queryName
     * @return
     */
    int executeNamedQuery(String queryName, Object[] params);

    /**
     * Save or update an object
     *
     * @param object the object to save or update
     */
    @Transactional
    void saveOrUpdate(T object);

    /**
     * Delete an object
     *
     * @param object the object to delete
     */
    @Transactional
    void delete(T object);

    /**
     * Merge an object
     *
     * @param object the object to merge
     */
    @Transactional
    T merge(T object);

    /**
     * Count the elements
     *
     * @param where the criteria
     * @return the number of elements matching the criteria
     */
    long count(Criterion[] where);

    /**
     * Save an object
     *
     * @param object the object to save or update
     */
    K save(T object);

    /**
     * Update an object
     *
     * @param object the object to save or update
     */
    void update(T object);
}
