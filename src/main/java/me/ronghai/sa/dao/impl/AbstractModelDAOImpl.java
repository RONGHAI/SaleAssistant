/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import com.ecbeta.common.core.db.DatabaseHandler;
import com.mysql.jdbc.StringUtils;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import me.ronghai.sa.dao.AbstractModelDAO;
import me.ronghai.sa.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author L5M
 * @param <E>
 */
public class AbstractModelDAOImpl<E extends AbstractModel> implements AbstractModelDAO<E>, Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    protected EntityManager entityManager;
    
    @Autowired
    protected DatabaseHandler databaseHandler;
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
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
            logger.log(Level.SEVERE, "{0}", e);
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
    public int remove(boolean force, Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String sql;
        String table = table(entityClass);
        if (force) {
            sql = "DELETE FROM " +  table + "  e  WHERE id IN (:ids) ";
        } else {
            sql = "UPDATE " +table + "  SET disabled = 1  WHERE id IN (:ids) ";
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids); 
        return this.databaseHandler.update(sql, parameters);
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
    
    private RowMapper<E> createRowMapper(){ 
        return new  RowMapper()   {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                 /*   Customer customer = new Customer();
                    customer.setCustId(rs.getInt("CUST_ID"));
                    customer.setName(rs.getString("NAME"));
                    customer.setAge(rs.getInt("AGE"));
                    return customer; */
                return null;
            };
        
        };
    }


    @Override
    public E find(Object id) {
        
        String sql = "SELECT * FROM " + table(entityClass)+ " WHERE id = ? ";
        return this.databaseHandler.queryForObject(sql,  new Object[] { id }, createRowMapper());
        
       
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
    
    public static String table(Class<?> entityClass ){
        String table = entityClass.getSimpleName();
        if(entityClass.isAnnotationPresent(Entity.class)){
            String t = ((Entity)entityClass.getAnnotation(Entity.class)).name();
              if(org.apache.commons.lang.StringUtils.isNotEmpty(t)){
                  table = t;
              }
        }
        return table;
    }
    
    public static String  column(Class<?> entityClass){
        return "";
    }

    @Override
    public long count(String configure) {
        String sql = " SELECT COUNT (*)  FROM " + table(entityClass);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(configure)){
            sql += " " + configure;
        }
        try {
            Collection<Object> values = this.databaseHandler.execute(sql).get(0).values();
            for (Object o : values) {
                return Long.parseLong(o.toString());
            }
        }catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }

    @Override
    public E reference(Object id) {
        try {
            return entityManager.getReference(entityClass, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
        return null;
    }
     @Override
     public List<Map<String, Object>> execute(String sql){
        try {
            return this.databaseHandler.execute(sql);
        }catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     @Override
     public int update(String sql){
        try {
            return this.databaseHandler.update(sql);
        }catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return 0;
     }  
}
