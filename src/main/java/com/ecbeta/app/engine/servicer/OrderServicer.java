package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.OrderDAOImpl;
import me.ronghai.sa.model.Order;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.OrderDAOImpl clientDAO;

    public OrderDAOImpl getOrderDAO() {
        return clientDAO;
    }

    public void setOrderDAO(OrderDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    private List<Order> clients;

    public List<Order> getOrders() {
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

   
    public Order update(Order entity) {
        Order c = clientDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Order find(Object id) {
        return clientDAO.find(id);
    }

    public List<Order> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }


    public void remove(Order c) {
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

    public Order save(Order c) {
        this.clientDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Order client = Order.fromJson(newJsonObj);
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

    private Order saveOrUpdate(Order client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }

}
