package com.weinyc.sa.app.dao;

import java.util.List;

import com.weinyc.sa.core.dao.AbstractModelDAO;
import com.weinyc.sa.core.model.AbstractModel;

public interface DAODelegate <E extends AbstractModel>{
    
    public void setDao(AbstractModelDAO<E> dao);
    
    public void beforePersistent(E entity);
    
    public void afterPersistent(E entity);
    
    public void beforeRemove(E entity);
    public void afterRemove(E entity);
    
   
    
    public void beforeUpdate(E entity);
    public void afterUpdate(E entity);
    
    public List<E> afterFind(List<E> entity);
    
    public E afterFind(E entity);
    
    
}
