package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.OrderDAOImpl;
import com.weinyc.sa.app.model.Order;
import com.weinyc.sa.core.dao.AbstractModelDAO;
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

public class OrderServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.OrderDAOImpl orderDAO;

    public OrderDAOImpl getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAOImpl orderDAO) {
        this.orderDAO = orderDAO;
    }

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }
    
    @Override
    public AbstractModelDAO<?> getDAO(){
        return orderDAO;
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
        this.orders = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Order update(Order entity) {
        Order c = orderDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Order find(Object id) {
        return orderDAO.find(id);
    }

    public List<Order> find() {
        return orderDAO.find(" WHERE disabled = 0 ");
    }


    public void remove(Order c) {
        this.orderDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.orderDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()) return false;
        if(0 == this.orderDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Order save(Order c) {
        this.orderDAO.persistent(c);
        this.refresh();
        return c;
    }

    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.orders, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.orders;
    }
    
    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null || jsonArray.isEmpty()) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Order order = Order.fromJson(newJsonObj);
            Long id  = order.getId();
            if(this.orderDAO.exsit(id)){
                order .setUpdateTime(new Date());
            }else{
                order.setId(null);
                order.setAddTime(new Date());
                order .setUpdateTime(new Date());
            }
            this.saveOrUpdate(order);
        }
        this.refresh();
        return true;
    }

    private Order saveOrUpdate(Order order) {
        if(order.getId() == null){
           return this.save (order);
        }else{
           return this.update(order);
        }
    }

}
