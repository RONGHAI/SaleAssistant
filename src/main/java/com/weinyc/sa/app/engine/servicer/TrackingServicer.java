package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.TrackingDAOImpl;
import com.weinyc.sa.app.model.Tracking;
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

public class TrackingServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.TrackingDAOImpl trackingDAO;

    public TrackingDAOImpl getTrackingDAO() {
        return trackingDAO;
    }

    public void setTrackingDAO(TrackingDAOImpl trackingDAO) {
        this.trackingDAO = trackingDAO;
    }
    
    
    protected transient AbstractModel rel;

    public AbstractModel getRel() {
        return rel;
    }

    public void setRel(AbstractModel rel) {
        boolean change = false;
        if(this.rel != null && rel != null && !rel.equals(this.rel)){
            change = true;
        }
        this.rel = rel;
        if(change){
            this.refresh();
        }
    }
 
    public void setOrder(AbstractModel rel) {
        this.setRel(rel);
    }
 

    private List<Tracking> trackings;

    public List<Tracking> getTrackings() {
        return trackings;
    }
    
    @Override
    public AbstractModelDAO<?> getDAO(){
        return trackingDAO;
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
        this.trackings = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Tracking update(Tracking entity) {
        Tracking c = trackingDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Tracking find(Object id) {
        return trackingDAO.find(id);
    }

    public List<Tracking> find() {
        String p = "";
        if(this.rel != null){
            p = " and related_id =  " + this.rel.getId();
        }
        return trackingDAO.find(" WHERE disabled = 0 " + p);
    }


    public void remove(Tracking c) {
        this.trackingDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.trackingDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()) return false;
        if( 0 == this.trackingDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Tracking save(Tracking c) {
        this.trackingDAO.persistent(c);
        this.refresh();
        return c;
    }
    
    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.trackings, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.trackings;
    }
    
    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Tracking tracking = Tracking.fromJson(newJsonObj);
            Long id  = tracking.getId();
            if(this.trackingDAO.exsit(id)){
                tracking .setUpdateTime(new Date());
            }else{
                tracking.setId(null);
                tracking.setAddTime(new Date());
                tracking.setUpdateTime(new Date());
            }
            this.saveOrUpdate(tracking);
        }
        this.refresh();
        return true;
    }

    private Tracking saveOrUpdate(Tracking tracking) {
        if(this.rel != null){
            tracking.setRelatedId(this.rel.getId());
        }
        if(tracking.getId() == null){
           return this.save (tracking);
        }else{
           return this.update(tracking);
        }
    }

}
