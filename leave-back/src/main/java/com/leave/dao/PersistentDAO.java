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
	 * @param id
	 *            the persistent object's id
	 * @return a Persistent object (may be null)
	 */
	T get(K id);

	/**
	 * Load a persistent object from its id
	 *
	 * @param id
	 *            the persistent object's id
	 * @return a Persistent object (may be null)
	 */
	T load(K id);

	/**
	 * Get a persistent object from one of its unique keys
	 *
	 * @param property
	 *            the unique property's name
	 * @param value
	 *            the unique property's value
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
	 * Get all persistent objects of a class, filtered by criteria and sorted by
	 * some fields, for a range of results
	 *
	 * @param where
	 *            the criteria
	 * @param projection
	 *            the projections
	 * @param sort
	 *            the sort columns
	 * @param start
	 *            the start index for the result list (starts at 0)
	 * @param count
	 *            the maximum number of results
	 * @return a List of Persistent objects
	 */
	List<T> findByCriteria(Criterion[] where, Projection[] projection, String[] sort, int start, int count);

	/**
	 * Save or update an object
	 *
	 * @param object
	 *            the object to save or update
	 */
	@Transactional
	void saveOrUpdate(T object);

	/**
	 * Delete an object
	 *
	 * @param object
	 *            the object to delete
	 */
	@Transactional
	void delete(T object);


	/**
	 * Count the elements
	 *
	 * @param where
	 *            the criteria
	 * @return the number of elements matching the criteria
	 */
	long count(Criterion[] where);

	/**
	 * Save an object
	 *
	 * @param object
	 *            the object to save or update
	 */
	K save(T object);

	/**
	 * Update an object
	 *
	 * @param object
	 *            the object to save or update
	 */
	void update(T object);
}
