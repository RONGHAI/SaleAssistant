package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.MerchantDAOImpl;
import me.ronghai.sa.model.Merchant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchantServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.MerchantDAOImpl clientDAO;

    public MerchantDAOImpl getMerchantDAO() {
        return clientDAO;
    }

    public void setMerchantDAO(MerchantDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    private List<Merchant> clients;

    public List<Merchant> getMerchants() {
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

   
    public Merchant update(Merchant entity) {
        Merchant c = clientDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Merchant find(Object id) {
        return clientDAO.find(id);
    }

    public List<Merchant> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }


    public void remove(Merchant c) {
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

    public Merchant save(Merchant c) {
        this.clientDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Merchant client = Merchant.fromJson(newJsonObj);
            Long id  = client.getId();
            if(this.clientDAO.exsit(id)){
                client .setUpdateTime(new Date());
            }else{
                client.setId(null);
                client.setAddTime(new Date());
            }
            this.saveOrUpdate(client);
        };
        this.refresh();
    }

    private Merchant saveOrUpdate(Merchant client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }

}
