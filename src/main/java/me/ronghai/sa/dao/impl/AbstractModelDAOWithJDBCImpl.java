/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import com.ecbeta.common.core.db.DatabaseHandler;
import com.ecbeta.common.core.reflect.ReflectUtils;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class AbstractModelDAOWithJDBCImpl<E extends AbstractModel> implements AbstractModelDAO<E>, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    protected DatabaseHandler databaseHandler;

    

    protected Class<E> entityClass;
    private static final Logger logger = Logger.getLogger(AbstractModelDAOWithJDBCImpl.class.getName());

    @SuppressWarnings("unchecked")
    public AbstractModelDAOWithJDBCImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];
    }
    
    
    private   List<?>[] findColumnsAndValues (E entity) {
        List<Field> allFieldList =  ReflectUtils.getDeclaredFields((List<Field>)null, entityClass, false);
        Map<String, PropertyDescriptor> fieldName2PropertyDescriptor =  ReflectUtils.findFieldName2PropertyDescriptor(entityClass);    
        List<String> columnNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();
           for ( Field field : allFieldList) {
               if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                   Column annotation = (Column) field.getAnnotation(Column.class);
                   String cname =  org.apache.commons.lang.StringUtils.isEmpty(annotation.name()) ? field.getName(): annotation.name();
                   Method getter = ReflectUtils.findGetter(entity, field, fieldName2PropertyDescriptor, null);
                   try {
                       Object value =  getter.invoke(entity);
                       if(value == null && !annotation.nullable()){
                            logger.log(Level.SEVERE, "{0}",  cname + " is not null. ");
                            return null;
                       }else if(value != null){
                           columnNames.add(cname);
                           values.add(value);
                       }
                   }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) { 
                        if(!annotation.nullable()){
                            logger.log(Level.SEVERE, "{0}", ex);
                            return null;
                        }else{
                            logger.log(Level.WARNING, "{0}", ex);
                        }
                   }
               }
           }
           return new List[]{columnNames, values};
    }
    
    @Override
    public E persistent(E entity) { 
     
        List<?>[] columnsAndValues = findColumnsAndValues(entity);
        List<String> columnNames = columnsAndValues == null ? null : (List<String>) columnsAndValues[0];
        List<Object> values = columnsAndValues == null ? null : (List<Object>) columnsAndValues[1];

        if (columnNames != null && !columnNames.isEmpty()) {
            String[] ph = new String[columnNames.size()];
            Arrays.fill(ph, "?");
            final String sql = " INSERT INTO " + table(entityClass) + "( " + org.apache.commons.lang.StringUtils.join(columnNames, " , ") + " ) VALUES ( "
                    + org.apache.commons.lang.StringUtils.join(ph, " , ") + ")";
            this.databaseHandler.update(sql, values.toArray());

            E ne = this.find(" ORDER BY ID DESC LIMIT 1 ").get(0);
            entity.setId(ne.getId());
            return ne;
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
        this.remove(true, Arrays.asList(entity.getId()));
    }

    @Override
    public int remove(boolean force, Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String sql;
        String table = table(entityClass);
        if (force) {
            sql = "DELETE FROM " + table + "  e  WHERE id IN (:ids) ";
        } else {
            sql = "UPDATE " + table + "  SET disabled = 1  WHERE id IN (:ids) ";
        }
        System.out.println(ids);
        MapSqlParameterSource parameters = new MapSqlParameterSource() ;
        parameters.addValue("ids", new ArrayList<>(ids));
        return this.databaseHandler.update(sql, parameters);
    }

    @Override
    public E update(E entity) {
        return merge(entity);
    }

    @Override
    public E merge(E entity) {
        //TODO ..
        List<?>[] columnsAndValues = findColumnsAndValues(entity);
        List<String> columnNames = columnsAndValues == null ? null : (List<String>) columnsAndValues[0];
        List<Object> values = columnsAndValues == null ? null : (List<Object>) columnsAndValues[1];        
        if (columnNames != null && !columnNames.isEmpty()) {
            String sql = " UPDATE " + table(entityClass) +" SET " ;
            for(int i = 0; i <  columnNames.size(); i++){
                sql += "  " + columnNames.get(i)+" = ? ";
                if(i != columnNames.size() - 1){
                    sql += ", ";
                } 
            } 
             System.out.println(values);
            System.out.println(columnNames);
            sql += " WHERE ID =  " + entity.getId();  
            this.databaseHandler.update(sql, values.toArray()); 
           
            return  find(entity.getId());
        } 
        return entity;
    }
    @Override
    public RowMapper<E> createRowMapper() {
         throw new UnsupportedOperationException("Not supported yet."); 
    }


    @Override
    public E find(Object id) {

        String sql = "SELECT * FROM " + table(entityClass) + " WHERE id = ? ";
        return this.databaseHandler.queryForObject(sql, new Object[]{id}, createRowMapper());

    }
    @Override
    public boolean exsit(Object id) {

        String sql = "SELECT count(*) FROM " + table(entityClass) + " WHERE id = ? ";
        return this.databaseHandler.queryForInt(sql, new Object[]{id}) != 0;

    }
    
    @Override
    public List<E> find() {
        String sql = "SELECT * FROM " + table(entityClass);
        return this.databaseHandler.query(sql, createRowMapper());
    }

    @Override
    public List<E> find(String configure) {
        return find(-1, 0, configure);
    }

    @Override
    public List<E> find(int from, int size, String configure) {
        String sql = "SELECT * FROM " + table(entityClass);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(configure)) {
            sql += " " + configure;
        }
        String part = from < 0 ? "" : " ROW_NUMBER >= " + from + " AND ROW_NUMBER <= " + (from + size);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(part)) {
            if (sql.toUpperCase().contains("WHERE")) {
                sql += " AND " + part;
            } else {

                sql += " WHERE " + part;
            }
        }
        System.out.println(sql);
        return this.databaseHandler.query(sql, createRowMapper());

    }

    public static String table(Class<?> entityClass) {
        String table = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(Entity.class)) {
            String t = ((Entity) entityClass.getAnnotation(Entity.class)).name();
            if (org.apache.commons.lang.StringUtils.isNotEmpty(t)) {
                table = t;
            }
        }
        return table;
    }

    public static String column(Class<?> entityClass) {
        return "";
    }

    @Override
    public long count(String configure) {
        String sql = " SELECT COUNT (*)  FROM " + table(entityClass);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(configure)) {
            sql += " " + configure;
        }
        try {
            Collection<Object> values = this.databaseHandler.execute(sql).get(0).values();
            for (Object o : values) {
                return Long.parseLong(o.toString());
            }
        }
        catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return 0;

    }

    @Override
    public List<Map<String, Object>> execute(String sql) {
        try {
            return this.databaseHandler.execute(sql);
        }
        catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return null;
    }

    @Override
    public int update(String sql) {
        try {
            return this.databaseHandler.update(sql);
        }
        catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
