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
import me.ronghai.sa.dao.ClientDAO;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.model.Client;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ronghai
 */
public class ClientServiceImpl implements ClientService, Serializable {

    private static final long serialVersionUID = 1L;
    private ClientDAO clientDAO;

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Client update(Client entity) {
        return clientDAO.update(entity);
    }

    @Override
    public Client find(Object id) {
        return clientDAO.find(id);
    }

    @Override
    public List<Client> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Client c){
        this.clientDAO.remove(c, false);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Long ... ids){
       this.clientDAO.remove( false,  Arrays.asList(ids));
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Collection<Long> ids){
        this.clientDAO.remove( false, ids);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Client save(Client c){
        this.clientDAO.persistent(c);
        return c;
    }

}
