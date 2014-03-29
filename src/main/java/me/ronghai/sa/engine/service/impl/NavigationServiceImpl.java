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
import me.ronghai.sa.dao.NavigationDAO;
import me.ronghai.sa.engine.service.NavigationService;
import me.ronghai.sa.model.Navigation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ronghai
 */
public class NavigationServiceImpl implements NavigationService, Serializable {

    private static final long serialVersionUID = 1L;
    private NavigationDAO navigationDAO;

    public NavigationDAO getNavigationDAO() {
        return navigationDAO;
    }

    public void setNavigationDAO(NavigationDAO navigationDAO) {
        this.navigationDAO = navigationDAO;
    }

     

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Navigation update(Navigation entity) {
        return navigationDAO.update(entity);
    }

    @Override
    public Navigation find(Object id) {
        return navigationDAO.find(id);
    }

    @Override
    public List<Navigation> find() {
        return navigationDAO.find(" WHERE disabled = false ");
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Navigation c){
        this.navigationDAO.remove(c, false);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Long ... ids){
       this.navigationDAO.remove( false,  Arrays.asList(ids));
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Collection<Long> ids){
        this.navigationDAO.remove( false, ids);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Navigation save(Navigation c){
        this.navigationDAO.persistent(c);
        return c;
    }

}
