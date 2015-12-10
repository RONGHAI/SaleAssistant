/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.weinyc.sa.core.db.DatabaseHandler;
import com.weinyc.sa.core.reflect.ReflectUtils;
import com.weinyc.sa.app.dao.DAODelegate;
import com.weinyc.sa.app.model.ModelMeta;
import com.weinyc.sa.core.dao.AbstractModelDAO;
import com.weinyc.sa.core.model.AbstractModel;

/**
 *
 * @author L5M
 * @param <E>
 */
public class AbstractModelDAOWithJDBCImpl<E extends AbstractModel> implements AbstractModelDAO<E>, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    public DatabaseHandler databaseHandler;

    
    
    protected transient DAODelegate<E> delegate;
    
    
    public DAODelegate<E> getDelegate() {
        return delegate;
    }

    @Override
    public void setDelegate(DAODelegate<E> delegate) {
        this.delegate = delegate;
        if(this.delegate != null){
            this.delegate.setDao(this);
        }
    }

    protected Class<E> entityClass;
    private static final Logger logger = Logger.getLogger(AbstractModelDAOWithJDBCImpl.class.getName());

    @SuppressWarnings("unchecked")
    public AbstractModelDAOWithJDBCImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];
    }
    
    
    private List<?>[] findColumnsAndValues(boolean excludeId, E entity) {

        List<String> columnNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        Map<String, Field> columnFields = entity.modelMeta().getColumnFields();
        Map<String, Method> field2Getter = entity.modelMeta().getField2Getter();

        for (Map.Entry<String, Field> entry : columnFields.entrySet()) {
            Field field = entry.getValue();
            if(excludeId && field.isAnnotationPresent(Id.class)){
                continue;
            }
            String cname = entry.getKey();
            Object value = null;
            try {
                value = field2Getter.get(field.getName()).invoke(entity);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
                logger.log(Level.WARNING, "filed is {0}  ", field);
                logger.log(Level.WARNING, "{0}", e);
            }
            Column annotation = field.isAnnotationPresent(Column.class) ? (Column) field.getAnnotation(Column.class) : null;
            if (annotation != null && value == null && !annotation.nullable()) {
                logger.log(Level.SEVERE, "{0}", cname + " is not null. ");
                return null;
            } else if (value != null) {
                columnNames.add(cname);
                values.add(value);
            }
        }

        /*
        
         List<Field> allFieldList =  ReflectUtils.getDeclaredFields((List<Field>)null, entityClass, false);
         Map<String, PropertyDescriptor> fieldName2PropertyDescriptor =  ReflectUtils.findFieldName2PropertyDescriptor(entityClass);    
        
         for ( Field field : allFieldList) {
         if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
         Column annotation = field.isAnnotationPresent(Column.class) ?  (Column) field.getAnnotation(Column.class) : null;
         String cname;
         if(annotation  != null && org.apache.commons.lang.StringUtils.isNotEmpty(annotation.name())){
         cname = annotation.name();
         cname = cname.replaceAll("[\\[\\]]", "`");
         }else{
         cname = field.getName();
         }
         Method getter = ReflectUtils.findGetter(entity, field, fieldName2PropertyDescriptor, null);
         try {
         Object value =  getter.invoke(entity);
         if(value == null && annotation != null && !annotation.nullable()){
         logger.log(Level.SEVERE, "{0}",  cname + " is not null. ");
         return null;
         }else if(value != null){
         columnNames.add(cname);
         values.add(value);
         }
         }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) { 
         if(annotation != null && !annotation.nullable()){
         logger.log(Level.SEVERE, "{0}", ex);
         return null;
         }else{
         logger.log(Level.WARNING, "{0}", ex);
         }
         }
         }
         }
         */
        return new List[]{columnNames, values};
    }
    
   
    
    @Override
    public E persistent(E entity) { 
        if(delegate != null){
            delegate.beforePersistent(entity);
        }
        List<?>[] columnsAndValues = findColumnsAndValues(true, entity);
        @SuppressWarnings("unchecked")
        List<String> columnNames = columnsAndValues == null ? null : (List<String>) columnsAndValues[0];
        @SuppressWarnings("unchecked")
        List<Object> values = columnsAndValues == null ? null : (List<Object>) columnsAndValues[1];
        
        logger.log(Level.INFO, "values\n{0}", values);
        
        if (columnNames != null && !columnNames.isEmpty()) {
            String[] ph = new String[columnNames.size()];
            Arrays.fill(ph, "?");
            final String sql = " INSERT INTO " + table(entityClass) + "( " + org.apache.commons.lang.StringUtils.join(columnNames, " , ") + " ) VALUES ( "
                    + org.apache.commons.lang.StringUtils.join(ph, " , ") + ")";
            this.databaseHandler.update(sql, values.toArray());
            E ne = this.find(" ORDER BY ID DESC LIMIT 1 ").get(0);
            entity.setId(ne.getId());
        }
        if(delegate != null){
            delegate.afterPersistent(entity);
        }
        return find(entity.getId());

    }

    @Override
    public void remove(E entity, boolean force) {
        if(delegate != null){
            delegate.beforeRemove(entity);
        }
        if (force) {
            remove(entity);
        } else {
            entity.setDisabled(true);
            merge(entity);
        }
        if(delegate != null){
            delegate.afterRemove(entity);
        }
    }
    
    

    @Override
    public void remove(E entity) {
        this.remove(true, Arrays.asList(entity.getId()));
    }

    @Override
    public int remove(boolean force, Collection<Long> ids) {
       int n = this.remove(force, ids, "");
       return n;
    }
    
    public void beforeRemove(boolean force, Collection<Long> ids, String configure){
        
    }
    
    public void afterRemove(boolean force, Collection<Long> ids, String configure){
        
    }
    
    
    
    @Override
    public int remove(boolean force, Collection<Long> ids, String configure) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        this.beforeRemove(force, ids, configure);
        String sql;
        String table = table(entityClass);
        if (force) {
            sql = "DELETE FROM " + table + "  e  WHERE id IN (:ids) ";
        } else {
            sql = "UPDATE " + table + "  SET disabled = 1  WHERE id IN (:ids) ";
        }
        if(StringUtils.isNotEmpty(configure)){
            if(!configure.trim().toUpperCase().startsWith("AND")){
                sql += " AND  ";
            }
            sql += configure;
        }
        logger.info("remove ids " + ids);
        MapSqlParameterSource parameters = new MapSqlParameterSource() ;
        parameters.addValue("ids", new ArrayList<>(ids));
        int n = this.databaseHandler.update(sql, parameters);
        
        this.afterRemove(force, ids, configure);
        return n;
        
    }

    @Override
    public E update(E entity) {
        return merge(entity);
    }

    @Override
    public E merge(E entity) {
        if(delegate != null){
            delegate.beforeUpdate(entity);
        }
        List<?>[] columnsAndValues = findColumnsAndValues(true, entity);
        @SuppressWarnings("unchecked")
        List<String> columnNames = columnsAndValues == null ? null : (List<String>) columnsAndValues[0];
        @SuppressWarnings("unchecked")
        List<Object> values = columnsAndValues == null ? null : (List<Object>) columnsAndValues[1];        
        if (columnNames != null && !columnNames.isEmpty()) {
            String sql = " UPDATE " + table(entityClass) +" SET " ;
            for(int i = 0; i <  columnNames.size(); i++){
                sql += "  " + columnNames.get(i)+" = ? ";
                if(i != columnNames.size() - 1){
                    sql += ", ";
                } 
            } 
            logger.log(Level.INFO, "{0}", values);
            logger.log(Level.INFO, "{0}",columnNames);
            sql += " WHERE ID =  " + entity.getId();  
            this.databaseHandler.update(sql, values.toArray()); 
        } 
        if(delegate != null){
            delegate.afterUpdate(entity);
        }
        return  find(entity.getId());
    }
    @SuppressWarnings("unchecked")
    @Override
    public RowMapper<E> createRowMapper() {        
        Method m = ReflectUtils.findMethod(entityClass, "_getModelMeta", null);
        ModelMeta<E> me;
        try {
            me = ( ModelMeta<E>) m.invoke(null);
            return me.getRowMapper();
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet."); 
    }


    @Override
    public E find(Object id) {
        String sql = "SELECT * FROM " + table(entityClass) + " WHERE id = ? ";
        E e = this.databaseHandler.queryForObject(sql, new Object[]{id}, createRowMapper());
        if(delegate != null){
            e = delegate.afterFind(e);
        }
        return e;
    }
    @Override
    public boolean exsit(Object id) {

        String sql = "SELECT count(*) FROM " + table(entityClass) + " WHERE id = ? ";
        return this.databaseHandler.queryForInt(sql, new Object[]{id}) != 0;

    }
    
    
    @Override
    public E find(Object id , String configure) {
        String sql = "SELECT * FROM " + table(entityClass) + " WHERE id = ? " ;
        if (org.apache.commons.lang.StringUtils.isNotEmpty(configure)) {
            sql += " " + configure.replaceAll("'", "''");
        }
        E entity = this.databaseHandler.queryForObject(sql, new Object[]{id}, createRowMapper());
        if(delegate != null){
            return delegate.afterFind(entity);
        }
        return entity;
    }
    @Override
    public boolean exsit(Object id , String configure) {
        String sql = "SELECT count(*) FROM " + table(entityClass) + " WHERE id = ? ";
        if (org.apache.commons.lang.StringUtils.isNotEmpty(configure)) {
            sql += " " + configure.replaceAll("'", "''");
        }
        return this.databaseHandler.queryForInt(sql, new Object[]{id}) != 0;

    }
    
    @Override
    public List<E> find() {
        String sql = "SELECT * FROM " + table(entityClass);
        List<E> entity = this.databaseHandler.query(sql, createRowMapper());
        if(delegate != null){
         return   delegate .afterFind(entity);
        }
        return entity;
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
        logger.log(Level.INFO, "{0}", sql);
        List<E> entity = this.databaseHandler.query(sql, createRowMapper());
        if(delegate != null){
            return   delegate.afterFind(entity);
        }
        return entity;

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


    @Override
    public List<E> find(List<Object> ids) {
        if(ids == null || ids.isEmpty()) return null;
        String sql = "SELECT * FROM " + table(entityClass) + " WHERE id IN (:ids) ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        List<E> entity = this.databaseHandler.query(sql, parameters, createRowMapper());
        if(delegate != null){
           return delegate.afterFind(entity);
        }
        return entity;
    }
    
    
    public List<Object> findRelatedIDs(final String relationTable, final String returnColumn,final  String whereColumn , final Long productId) {
        String sql = " SELECT "+returnColumn+" FROM "+relationTable+"  WHERE "+whereColumn+" = ? ";
        List<Object> ids = this.databaseHandler.query(sql, new Object[]{productId},  new RowMapper<Object> (){
            @Override
            public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
                 return arg0.getLong(returnColumn);
            }});
        
        return ids;
    }
    
    
    public void updateRelatedIDs(final String relationTable, final String returnColumn,final  String whereColumn , final Long productId, List<? extends AbstractModel> relateds ){
        String   sql = "DELETE FROM " + relationTable + "   WHERE "+whereColumn+" IN (:ids) ";
        MapSqlParameterSource parameters = new MapSqlParameterSource() ;
        parameters.addValue("ids", Arrays.asList(productId));
        this.databaseHandler.update(sql, parameters);
        if(relateds != null && !relateds.isEmpty()){
            System.out.println(relateds);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
            sql = "INSERT INTO " + relationTable +" ( " + returnColumn +", " + whereColumn+" ) VALUES ( ?, "+productId+") ";
            for(AbstractModel mm : relateds){
                databaseHandler.update( sql , mm.getId());
            }
        }
    }
    
    public void removeRelatedIDs(final String relationTable, final String returnColumn,final  String whereColumn , final Long productId ){
        String   sql = "DELETE FROM " + relationTable + "    WHERE "+whereColumn+" IN (:ids) ";
        MapSqlParameterSource parameters = new MapSqlParameterSource() ;
        parameters.addValue("ids", Arrays.asList(productId));
        this.databaseHandler.update(sql, parameters);
    }
    
   /* 
    pSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("ids", ids);

    List<Foo> foo = getJdbcTemplate().query("SELECT * FROM foo WHERE a IN (:ids)",
         getRowMapper(), parameters);*/
}
