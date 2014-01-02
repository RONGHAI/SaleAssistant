/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.engine.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import me.ronghai.sa.dao.ClientDAO;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.model.Client;

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
    @Override
    public void remove(Client c){
        this.clientDAO.remove(c, false);
    }
    @Override
    public void remove(Long ... ids){
        for(Long id : ids){
            this.clientDAO.remove(this.clientDAO.find(id), false);
        }
    }
    
    
    @Override
    public void remove(Collection<Long> ids){
        for(Long id : ids){
            this.clientDAO.remove(this.clientDAO.find(id), false);
        }
    }
    
    @Override
    public Client save(Client c){
        this.clientDAO.persistent(c);
        return c;
    }

}
