package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.ClientAddressDAOImpl;
import com.weinyc.sa.app.model.ClientAddress;
import com.weinyc.sa.core.model.AbstractModel;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class ClientAddressServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.ClientAddressDAOImpl clientAddressDAO;
    
    private transient Long client;

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
        return clientAddressDAO.find(" WHERE DISABLED = FALSE " + (this.client == null ? "" : " AND CLIENT_ID = "+this.client));
    }
    
    
    public List<ClientAddress> findWithClient(Long cid) {
        return clientAddressDAO.find(" WHERE DISABLED = FALSE AND CLIENT_ID = " + cid);
    }

    public void remove(ClientAddress c) {
        this.clientAddressDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        if(ids == null) return;
        this.clientAddressDAO.remove(false, Arrays.asList(ids),  this.client == null ? null : ( "AND CLIENT_ID = " + this.client));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.clientAddreses, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.clientAddreses;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 == this.clientAddressDAO.remove(false, new ArrayList<>(ids),  this.client == null ? null : ( "AND CLIENT_ID = " + this.client))){
            return false;
        };
        this.refresh();
        return true;
    }

    public ClientAddress save(ClientAddress c) {
        this.clientAddressDAO.persistent(c);
        this.refresh();
        return c;
    }
    
    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            ClientAddress clientAddress = ClientAddress.fromJson(newJsonObj);
            if(this.client != null){
                clientAddress.setClientId(this.client);
            }
            Long id  = clientAddress.getId();
            System.out.println("clientAddress" + clientAddress.getClientId());
            if(this.clientAddressDAO.exsit(id, this.client == null ? null : ( "AND CLIENT_ID = " + this.client))){
                clientAddress .setUpdateTime(new Date());
            }else{
                clientAddress.setId(null);
                clientAddress.setAddTime(new Date());
            }
            this.saveOrUpdate(clientAddress);
        }
        this.refresh();
        return true;
    }

    private ClientAddress saveOrUpdate(ClientAddress clientAddress) {
        if(clientAddress.getId() == null){
           return this.save (clientAddress);
        }else{
           return this.update(clientAddress);
        }
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        if(client.equals(this.client)){
        }else{
            this.client = client;
            this.refresh();
        }
       
        
    }

}
