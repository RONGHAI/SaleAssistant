package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.ClientDAOImpl;
import me.ronghai.sa.model.Client;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.ClientDAOImpl clientDAO;

    public ClientDAOImpl getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    /**
     *
     */
    private static final long serialVersionUID = 4874672762001288584L;

    @Override
    public void init(NavigationBean navBean) {
        this.refresh();
    }

    private void refresh() {
        this.clients = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Client update(Client entity) {
        Client c = clientDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Client find(Object id) {
        return clientDAO.find(id);
    }

    public List<Client> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }


    public void remove(Client c) {
        this.clientDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.clientDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.clientDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Client save(Client c) {
        this.clientDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Client client = Client.fromJson(newJsonObj);
            Long id  = client.getId();
            if(this.clientDAO.exsit(id)){
                client .setUpdateTime(new Date());
            }else{
                client.setId(null);
                client.setAddTime(new Date());
                client.setUpdateTime(new Date());
            }
            this.saveOrUpdate(client);
        };
        this.refresh();
    }

    private Client saveOrUpdate(Client client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }

}
