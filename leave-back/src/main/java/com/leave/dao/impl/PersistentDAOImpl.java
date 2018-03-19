/*
 * PersistentDAOImpl - Base implementation of the PersistentDAO interface
 */

package com.leave.dao.impl;

import com.leave.dao.PersistentDAO;
import com.leave.entity.AbstractEntity;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base implementation of the PersistentDAO interface. Type parameter K is the
 * type of the Primary Key, and T is the type of the entity.
 *
 * @author Etienne Bernard
 * @version $Id$
 */
public abstract class PersistentDAOImpl<K extends Serializable, T extends AbstractEntity> implements PersistentDAO<K, T> {
    protected Class<T> persistentClass;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    /**
     * Default constructor
     */
    protected PersistentDAOImpl() {
        this.persistentClass = getPersistentClass();
    }

    /**
     * Get the persistent class this DAO is handling. By default, persistent
     * class is the second generic type.
     *
     * @return The persistent class
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Class<T> getPersistentClass() {
        Class clazz = getClass();
        do {
            Type type = clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
            }
            clazz = clazz.getSuperclass();
        }
        while (clazz != null);
        return null;
    }

    /**
     * Get a persistent object from its id
     *
     * @param id the persistent object's id
     * @return a Persistent object (may be null)
     */
    @Override
    public T get(K id) {
        return (T) hibernateTemplate.get(persistentClass, id);
    }

    @Override
    public T load(K id) {
        return (T) hibernateTemplate.load(persistentClass, id);
    }

    /**
     * Get a persistent object from one of its unique keys
     *
     * @param property the unique property's name
     * @param value    the unique property's value
     * @return a Persistent object (may be null)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findByUniqueKey(final String property, final Object value) {
        return (T) hibernateTemplate.findByCriteria(DetachedCriteria.forClass(persistentClass).add(Restrictions.eq(property, value)));
    }

    /**
     * Get all persistent objects of a class
     *
     * @return a List of Persistent objects
     */
    @Override
    public List<T> findAll() {
        return findAllWhereProjectionSortRange(null, null, null, -1, -1);
    }

    /**
     * Save or update an object
     *
     * @param object the object to save or update
     */
    @Override
    public void saveOrUpdate(T object) {
        hibernateTemplate.saveOrUpdate(object);
    }

    /**
     * Save an object
     *
     * @param object the object to save or update
     */
    @SuppressWarnings("unchecked")
    @Override
    public K save(T object) {
        return (K) hibernateTemplate.save(object);
    }

    /**
     * Save an object
     *
     * @param object the object to save or update
     */
    @Override
    public void update(T object) {
        hibernateTemplate.update(object);
    }

    /**
     * Delete an object
     *
     * @param object the object to delete
     */
    @Override
    public void delete(T object) {
        hibernateTemplate.delete(object);
    }

    /**
     * Get all persistent objects of a class, sorted by some fields
     *
     * @param sort the sort columns
     * @return a List of Persistent objects
     */
    @Override
    public List<T> findAllSort(String[] sort) {
        return findAllWhereProjectionSortRange(null, null, sort, -1, -1);
    }

    /**
     * Get all persistent objects of a class, filtered by criteria
     *
     * @param where the criteria
     * @return a List of Persistent objects
     */
    @Override
    public List<T> findAllWhere(Criterion[] where) {
        return findAllWhereProjectionSortRange(where, null, null, -1, -1);
    }

    /**
     * Perform a query on the class, with some projections
     *
     * @param projection the projections
     * @return a List of Persistent objects
     */
    @Override
    public List<T> findAllProjection(Projection[] projection) {
        return findAllWhereProjectionSortRange(null, projection, null, -1, -1);
    }

    /**
     * Get all persistent objects of a class, filtered by criteria and sorted by
     * some fields
     *
     * @param where the criteria
     * @param sort  the sort columns
     * @return a List of Persistent objects
     */
    @Override
    public List<T> findAllWhereSort(Criterion[] where, String[] sort) {
        return findAllWhereProjectionSortRange(where, null, sort, -1, -1);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAllWhereProjectionSortRange(final Criterion[] where, final Projection[] projection, final String[] sort, final int start, final int count) {

        DetachedCriteria c = DetachedCriteria.forClass(persistentClass);
        if (where != null && where.length > 0) {
            for (Criterion aWhere : where) {
                c.add(aWhere);
            }
        }
        if (projection != null && projection.length > 0) {
            ProjectionList projectionList = Projections.projectionList();
            for (Projection aProjection : projection) {
                projectionList.add(aProjection);
            }
            c.setProjection(projectionList);
        }
        if (sort != null) {
            Set<String> aliases = new HashSet<String>();
            for (String aSort : sort) {
                boolean desc = aSort.charAt(0) == '-';
                if (desc) {
                    aSort = aSort.substring(1);
                }
                int pos = aSort.indexOf('.');
                if (pos != -1) {
                    String alias = aSort.substring(0, pos);
                    if (!aliases.contains(alias)) {
                        c.createAlias(alias, alias);
                        aliases.add(alias);
                    }
                }
                c.addOrder(desc ? Order.desc(aSort) : Order.asc(aSort));
            }
        }

        return (List<T>) hibernateTemplate.findByCriteria(c, start, count);
    }


    @Override
    public List<T> findByNamedQuery(final String queryName) {
        return findByNamedQueryRange(queryName, null, -1, -1);
    }


    @Override
    public List<T> findByNamedQueryRange(final String queryName, int start, int count) {
        return findByNamedQueryRange(queryName, null, start, count);
    }


    @Override
    public List<T> findByNamedQuery(final String queryName, final Object[] params) {
        return findByNamedQueryRange(queryName, params, -1, -1);
    }


    @Override
    public int executeNamedQuery(final String queryName) {
        return executeNamedQuery(queryName, null);
    }

    @Override
    public int executeNamedQuery(String queryName, Object[] params) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByNamedQueryRange(final String queryName, final Object[] params, final int start, final int count) {
        return (List<T>) hibernateTemplate.findByNamedQuery(queryName, params);
    }

    @Override
    public long count(final Criterion[] where) {
        return 0;
//		DetachedCriteria c = DetachedCriteria.forClass(persistentClass);
//		if (where != null && where.length > 0) {
//			for (Criterion aWhere : where) {
//				c.add(aWhere);
//			}
//		}
//		c.setProjection(Projections.rowCount());
//		return (long) hibernateTemplate.findByCriteria(c).iterator().next();
    }

    /**
     * Merge an object
     *
     * @param object the object to merge
     */
    @Override
    public T merge(T object) {
        return (T) hibernateTemplate.merge(object);
    }
}
