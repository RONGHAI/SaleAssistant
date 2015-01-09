package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.CarrierDAOImpl;
import me.ronghai.sa.model.Carrier;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class CarrierServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.CarrierDAOImpl carrierDAO;

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
        return carrierDAO.find(" WHERE disabled = false ");
    }


    public void remove(Carrier c) {
        this.carrierDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.carrierDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.carrierDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Carrier save(Carrier c) {
        this.carrierDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
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
            }
            this.saveOrUpdate(carrier);
        }
        this.refresh();
    }

    private Carrier saveOrUpdate(Carrier carrier) {
        if(carrier.getId() == null){
           return this.save (carrier);
        }else{
           return this.update(carrier);
        }
    }

}
