/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.engine.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import me.ronghai.sa.dao.PropertyDAO;
import me.ronghai.sa.engine.service.PropertyService;
import me.ronghai.sa.model.Property;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ronghai
 */
public class PropertyServiceImpl implements PropertyService, Serializable {

    private static final long serialVersionUID = 1L;
    private PropertyDAO propertyDAO;

    public PropertyDAO getPropertyDAO() {
        return propertyDAO;
    }

    public void setPropertyDAO(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Override
    public String findPropertyValue(String code){
        List<Property> p = this.propertyDAO.find(" WHERE code = " + code);
        if(p != null && p.size() > 0 ){
            return p.get(0).getValue();
        }
        return "";
    }
 
     
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Property update(Property entity) {
        return propertyDAO.update(entity);
    }

    @Override
    public Property find(Object id) {
        return propertyDAO.find(id);
    }

    @Override
    public List<Property> find() {
        return propertyDAO.find();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Property c){
        this.propertyDAO.remove(c, false);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Long ... ids){
       this.propertyDAO.remove( false,  Arrays.asList(ids));
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Collection<Long> ids){
        this.propertyDAO.remove( false, ids);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Property save(Property c){
        System.out.println("save...");
        this.propertyDAO.persistent(c);
        return c;
    }

    
}
