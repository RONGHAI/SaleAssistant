package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.CarrierDAOImpl;
import com.weinyc.sa.app.model.Carrier;
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

public class CarrierServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.CarrierDAOImpl carrierDAO;

    public CarrierDAOImpl getCarrierDAO() {
        return carrierDAO;
    }

    public void setCarrierDAO(CarrierDAOImpl carrierDAO) {
        this.carrierDAO = carrierDAO;
    }

    private List<Carrier> carriers;

    public List<Carrier> getCarriers() {
        return carriers;
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
        this.carriers = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Carrier update(Carrier entity) {
        Carrier c = carrierDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Carrier find(Object id) {
        return carrierDAO.find(id);
    }

    public List<Carrier> find() {
        return carrierDAO.find(" WHERE disabled = 0 ");
    }


    public void remove(Carrier c) {
        this.carrierDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        if(ids == null) return;
        this.carrierDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.carriers, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.carriers;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 ==  this.carrierDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Carrier save(Carrier c) {
        this.carrierDAO.persistent(c);
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
            Carrier carrier = Carrier.fromJson(newJsonObj);
            Long id  = carrier.getId();
            if(this.carrierDAO.exsit(id)){
                carrier .setUpdateTime(new Date());
            }else{
                carrier.setId(null);
                carrier.setAddTime(new Date());
                carrier .setUpdateTime(new Date());
            }
            this.saveOrUpdate(carrier);
        }
        this.refresh();
        return true;
    }

    private Carrier saveOrUpdate(Carrier carrier) {
        if(carrier.getId() == null){
           return this.save (carrier);
        }else{
           return this.update(carrier);
        }
    }

}
