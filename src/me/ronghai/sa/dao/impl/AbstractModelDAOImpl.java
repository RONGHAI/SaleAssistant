/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import me.ronghai.sa.dao.AbstractModelDAO;
import me.ronghai.sa.model.AbstractModel;

/**
 *
 * @author L5M
 * @param <E>
 */
public class AbstractModelDAOImpl<E extends AbstractModel> implements AbstractModelDAO<E> {

    @PersistenceContext
    private EntityManager entityManager;
    protected Class<E> entityClass;
    private static final Logger logger = Logger.getLogger(AbstractModelDAOImpl.class.getName());

    @SuppressWarnings("unchecked")
    public AbstractModelDAOImpl() {
        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    @Override
    public void persistent(E entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
        }
    }

    @Override
    public void remove(E entity, boolean force) {
        if (force) {
            remove(entity);
        } else {
            entity.setDisabled(true);
            merge(entity);
        }

    }

    @Override
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public E update(E entity) {
        return merge(entity);
    }

    @Override
    public E merge(E entity) {
        try {
            entityManager.merge(entity);
            return entity;
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void refresh(E entity) {
        try {
            entityManager.refresh(entity);
        } catch (Exception e) {
        }
    }

    @Override
    public E find(Object id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<E> find() {
        String jpql = "SELECT e FROM " + entityClass.getName() + " e";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }

    @Override
    public List<E> find(String configure) {
        return find(-1, 0, configure);
    }

    @Override
    public List<E> find(int from, int size, String configure) {
        String jpql = "SELECT e FROM " + entityClass.getName() + " e ";
        if (configure != null) {
            jpql = jpql + configure;
        }
        Query query = entityManager.createQuery(jpql);
        if (from != -1) {
            query.setFirstResult(from);
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    @Override
    public long count(String configure) {
        String jpql = "SELECT count(*) FROM " + entityClass.getName();
        if (configure != null) {
            jpql = jpql + configure;
        }
        Query query = entityManager.createQuery(jpql);
        Object rtn = query.getSingleResult();
        return rtn == null ? 0 : Long.parseLong(rtn.toString());
    }

    @Override
    public E reference(Object id) {
        try {
            return entityManager.getReference(entityClass, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return null;
    }
}
