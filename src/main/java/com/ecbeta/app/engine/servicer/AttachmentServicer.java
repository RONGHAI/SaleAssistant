package com.ecbeta.app.engine.servicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import me.ronghai.sa.dao.AttachmentDAO;
import me.ronghai.sa.dao.impl.AttachmentDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Attachment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;

public class AttachmentServicer extends AbstractServicer  {

    @Autowired
    private AttachmentDAOImpl  attachmentDAO;

    
    public AttachmentDAO getAttachmentDAO() {
        return attachmentDAO;
    }

    public void setAttachmentDAO(AttachmentDAOImpl attachmentDAO) {
        this.attachmentDAO = attachmentDAO;
    }

    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
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
        this.attachments = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Attachment update(Attachment entity) {
        Attachment c = attachmentDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Attachment find(Object id) {
        return attachmentDAO.find(id);
    }

    public List<Attachment> find() {
        return attachmentDAO.find(" WHERE disabled = false ");
    }


    public void remove(Attachment c) {
        this.attachmentDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        if(ids == null) return;
        this.attachmentDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.attachments, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.attachments;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 ==  this.attachmentDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Attachment save(Attachment c) {
        this.attachmentDAO.persistent(c);
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
            Attachment carrier = Attachment.fromJson(newJsonObj);
            Long id  = carrier.getId();
            if(this.attachmentDAO.exsit(id)){
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

    private Attachment saveOrUpdate(Attachment carrier) {
        if(carrier.getId() == null){
           return this.save (carrier);
        }else{
           return this.update(carrier);
        }
    }

}
