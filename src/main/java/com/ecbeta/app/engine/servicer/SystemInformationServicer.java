package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.PropertyDAOImpl;
import me.ronghai.sa.model.Property;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemInformationServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.PropertyDAOImpl systemInformationDAO;

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


    public void remove(Collection<Long> ids) {
        this.systemInformationDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Property save(Property c) {
        this.systemInformationDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
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
    }

    private Property saveOrUpdate(Property client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }

}
