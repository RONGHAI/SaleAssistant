package com.weinyc.sa.app.engine.servicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.PropertyDAOImpl;
import com.weinyc.sa.app.model.Property;
import com.weinyc.sa.core.model.AbstractModel;

public class SystemInformationServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.PropertyDAOImpl systemInformationDAO;

    public PropertyDAOImpl getSystemInformationDAO() {
        return systemInformationDAO;
    }

    public void setSystemInformationDAO(PropertyDAOImpl systemInformationDAO) {
        this.systemInformationDAO =  systemInformationDAO;
    }

    private List<Property> systemInformations;

    public List<Property> getSystemInformations() {
        return systemInformations;
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
        this.systemInformations = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Property update(Property entity) {
        Property c = systemInformationDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Property find(Object id) {
        return systemInformationDAO.find(id);
    }

    public List<Property> find() {
        return systemInformationDAO.find(" WHERE disabled = false ");
    }


    public void remove(Property c) {
        this.systemInformationDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.systemInformationDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.systemInformations, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.systemInformations;
    }
    
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()) return false;
        if( 0 ==  this.systemInformationDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Property save(Property c) {
        this.systemInformationDAO.persistent(c);
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
            Property bean = Property.fromJson(newJsonObj);
            Long id  = bean.getId();
            if(this.systemInformationDAO.exsit(id)){
                bean .setUpdateTime(new Date());
            }else{
                bean.setId(null);
                bean.setAddTime(new Date());
                bean .setUpdateTime(new Date());
            }
            this.saveOrUpdate(bean);
        }
        this.refresh();
        return true;
    }

    private Property saveOrUpdate(Property client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }

}
