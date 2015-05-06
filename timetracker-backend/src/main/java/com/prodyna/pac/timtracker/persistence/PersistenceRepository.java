package com.prodyna.pac.timtracker.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Abstract implementation of {@link Repository} using JPA.
 * @author moritz löser (moritz.loeser@prodyna.com)
 *
 * @param <T>
 */
public abstract class PersistenceRepository<T> implements Repository<T> {
    
    /**
     * Entity manager - provides JPA.
     */
    @Inject
    private EntityManager em;
    
    /**
     * Type of this entity.
     */
    private Class<T> type;
    
    
    /**
     * 
     * @param type type of entity
     */
    public PersistenceRepository(final Class<T> type) {
        this.type = type;
    }
    
    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public T store(final T entity) {
        T mergedEntity = merge(entity);
        em.persist(mergedEntity);
        return mergedEntity;
    }

    @Override
    public T get(final Long id) {
        return em.find(type, id);
    }
    
    @Override
    public void remove(final T entity) {
        em.remove(merge(entity));
    }

    /**
     * Calls {@link EntityManager#merge(Object)} on given entity and returns it.
     * @param entity to be merged
     * @return merged entity
     */
    private T merge(final T entity) {
        return em.merge(entity);
    }
}
