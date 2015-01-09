package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.ClientAddressDAOImpl;
import me.ronghai.sa.model.ClientAddress;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientAddressServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.ClientAddressDAOImpl clientAddressDAO;

    public ClientAddressDAOImpl getClientAddressDAO() {
        return clientAddressDAO;
    }

    public void setClientAddressDAO(ClientAddressDAOImpl clientAddressDAO) {
        this.clientAddressDAO = clientAddressDAO;
    }

    private List<ClientAddress> clientAddreses;

    public List<ClientAddress> getClientAddreses() {
        return clientAddreses;
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
        this.clientAddreses = this.find();
    }

    @Override
    public void destory() {

    }

   
    public ClientAddress update(ClientAddress entity) {
        ClientAddress c = clientAddressDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public ClientAddress find(Object id) {
        return clientAddressDAO.find(id);
    }

    public List<ClientAddress> find() {
        return clientAddressDAO.find(" WHERE disabled = false ");
    }


    public void remove(ClientAddress c) {
        this.clientAddressDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.clientAddressDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.clientAddressDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public ClientAddress save(ClientAddress c) {
        this.clientAddressDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            ClientAddress clientAddress = ClientAddress.fromJson(newJsonObj);
            Long id  = clientAddress.getId();
            if(this.clientAddressDAO.exsit(id)){
                clientAddress .setUpdateTime(new Date());
            }else{
                clientAddress.setId(null);
                clientAddress.setAddTime(new Date());
            }
            this.saveOrUpdate(clientAddress);
        }
        this.refresh();
    }

    private ClientAddress saveOrUpdate(ClientAddress clientAddress) {
        if(clientAddress.getId() == null){
           return this.save (clientAddress);
        }else{
           return this.update(clientAddress);
        }
    }

}
