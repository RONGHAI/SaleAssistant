package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.USAOrderDAOImpl;
import com.weinyc.sa.app.model.USAOrder;
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

public class USAOrderServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.USAOrderDAOImpl americaOrderDAO;

    public USAOrderDAOImpl getAmericaOrderDAO() {
        return americaOrderDAO;
    }

    public void setOrderDAO(USAOrderDAOImpl americaOrderDAO) {
        this.americaOrderDAO = americaOrderDAO;
    }

    private List<USAOrder> orders;

    public List<USAOrder> getUSAOrders() {
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

   
    public USAOrder update(USAOrder entity) {
        USAOrder c = americaOrderDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public USAOrder find(Object id) {
        return americaOrderDAO.find(id);
    }

    public List<USAOrder> find() {
        return americaOrderDAO.find(" WHERE disabled = 0 ");
    }


    public void remove(USAOrder c) {
        this.americaOrderDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.americaOrderDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()) return false;
        if( 0 == this.americaOrderDAO.remove(false, new ArrayList<>(ids))){
            return false;
        };
        this.refresh();
        return true;
    }

    public USAOrder save(USAOrder c) {
        this.americaOrderDAO.persistent(c);
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
        if(jsonArray == null || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            USAOrder order = USAOrder.fromJson(newJsonObj);
            Long id  = order.getId();
            if(this.americaOrderDAO.exsit(id)){
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

    private USAOrder saveOrUpdate(USAOrder order) {
        if(order.getId() == null){
           return this.save (order);
        }else{
           return this.update(order);
        }
    }

}
