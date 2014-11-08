/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import me.ronghai.sa.model.AbstractModel;
import org.springframework.jdbc.core.RowMapper;

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

    public E merge(E entity);

    public E update(E entity);

    public E find(Object id);
    public boolean exsit(Object id);
    public List<E> find();

    public List<E> find(String condition);

    public List<E> find(int from, int size, String condition);

    public long count(String configure);

    
    public List<Map<String, Object>> execute(String sql);
    
    public int update(String sql);
    
    
    public RowMapper<E> createRowMapper();
}
