package com.revolut.test.repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public abstract class JpaPersistenceRepository<T> {

    private static final String UNIT = "money-transfer";
    private EntityManager entityManager;

    synchronized EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory(UNIT).createEntityManager();
        }
        return entityManager;
    }

    public abstract T find(long id);

    public void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public void lock(T t) {
        getEntityManager().lock(t, LockModeType.OPTIMISTIC);
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public T save(T t) {
        return getEntityManager().merge(t);
    }

    public void commit() {
        getEntityManager().getTransaction().commit();
    }
}
