/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.core.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.DAODelegate;
import com.weinyc.sa.core.model.AbstractModel;

/**
 *
 * @author L5M
 * @param <E>
 */
public interface AbstractModelDAO<E extends AbstractModel> {

    public E persistent(E entity);

    public void remove(E entity, boolean force);

    public void remove(E entity);
    
    public int remove(boolean force, Collection<Long>  ids);
    
    public int remove(boolean force, Collection<Long>  ids, String configure);

    public E merge(E entity);

    public E update(E entity);

    public E find(Object id);
    public E find(Object id, String condition);
    
    public boolean exsit(Object id);
    
    public boolean exsit(Object id , String condition);
    
    public List<E> find();
    public List<E> find(List<Object> ids);
 
    public List<E> find(String condition);

    public List<E> find(int from, int size, String condition);

    public long count(String configure);

    
    public List<Map<String, Object>> execute(String sql);
    
    public int update(String sql);
    
    public void setDelegate(DAODelegate<E> delegate) ;
    public RowMapper<E> createRowMapper();
    
    public long max();
    
    
}
