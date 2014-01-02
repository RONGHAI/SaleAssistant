/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.engine.service;

import java.util.Collection;
import java.util.List;
import me.ronghai.sa.model.Client;

/**
 *
 * @author ronghai
 */
public interface ClientService {
    public Client update(Client entity);
    public Client find(Object id);
    public void remove(Client c);
    public List<Client> find();
    public void remove(Long ... ids);
    public void remove(Collection<Long> ids);
    public Client save(Client c);
}
