/*
 * Copyright (C) 2014 Ronghai Wei <ronghai.wei@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.weinyc.sa.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.DAODelegate;
import com.weinyc.sa.core.dao.AbstractModelDAO;
import com.weinyc.sa.core.model.AbstractModel;
/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 * @param  <E>
 */
public class AbstractModelDAOWithEMImpl <E extends AbstractModel> implements AbstractModelDAO<E>, Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(type=PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    protected Class<E> entityClass;
    private static final Logger logger = Logger.getLogger(AbstractModelDAOWithEMImpl.class.getName());

    @SuppressWarnings("unchecked")
    public AbstractModelDAOWithEMImpl() {
        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    @Override
    public E persistent(E entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
        return entity;
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
    public int remove(boolean force, Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String sql;
        if (force) {
            sql = "DELETE FROM " + entityClass.getName() + "  e  where id in (:ids) ";
        } else {
            sql = "UPDATE " + entityClass.getName() + "  SET disabled = 1  where id in (:ids) ";
        }
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("ids", ids);
        int s = query.executeUpdate();
        return s;
    }
    
    
    @Override
    public int remove(boolean force, Collection<Long> ids, String configure) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String sql;
        if (force) {
            sql = "DELETE FROM " + entityClass.getName() + "  e  where id in (:ids) ";
        } else {
            sql = "UPDATE " + entityClass.getName() + "  SET disabled = 1  where id in (:ids) ";
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(configure)) {
            sql += " " + configure.replaceAll("'", "''");
        }
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("ids", ids);
        int s = query.executeUpdate();
        return s;
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
            logger.log(Level.SEVERE, "{0}", e);
        }
        return null;
    }

    public void refresh(E entity) {
        try {
            entityManager.refresh(entity);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
    }

    @Override
    public E find(Object id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e);
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
     public int update(String sql){
        Query query = entityManager.createNativeQuery(sql); 
        return query.executeUpdate();
     }  

    @Override
    public List<Map<String, Object>> execute(String sql) {
        throw new UnsupportedOperationException("This is from JDBCTemplate."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public RowMapper<E> createRowMapper() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exsit(Object id) {
        return find(id ) != null;
    }

    @Override
    public E find(Object id, String condition) {
        List<E> list = find(" id = " +id + condition);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public boolean exsit(Object id, String condition) {
        return find(id, condition ) != null;
    }

    @Override
    public List<E> find(List<Object> ids) {
        return null;
    }
    @Override
    public void setDelegate(DAODelegate<E> delegate) {
        throw new UnsupportedOperationException();
    }
}
 
