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
    private me.ronghai.sa.dao.impl.OrderDAOImpl orderDAO;

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
        return orderDAO.find(" WHERE disabled = false ");
    }


    public void remove(Order c) {
        this.orderDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.orderDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.orderDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Order save(Order c) {
        this.orderDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
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
            }
            this.saveOrUpdate(order);
        }
        this.refresh();
    }

    private Order saveOrUpdate(Order order) {
        if(order.getId() == null){
           return this.save (order);
        }else{
           return this.update(order);
        }
    }

}
