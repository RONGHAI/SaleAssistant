package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import me.ronghai.sa.dao.impl.HashtagDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Hashtag;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class HashtagServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.HashtagDAOImpl hashtagDAO;

    public HashtagDAOImpl getHashtagDAO() {
        return hashtagDAO;
    }

    public void setHashtagDAO(HashtagDAOImpl hashtagDAO) {
        this.hashtagDAO = hashtagDAO;
    }

    private List<Hashtag> hashtags;

    public List<Hashtag> getHashtags() {
        return hashtags;
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
        this.hashtags = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Hashtag update(Hashtag entity) {
        Hashtag c = hashtagDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Hashtag find(Object id) {
        return hashtagDAO.find(id);
    }

    public List<Hashtag> find() {
        return hashtagDAO.find(" WHERE disabled = false ");
    }


    public void remove(Hashtag c) {
        this.hashtagDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.hashtagDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }
    
    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.hashtags, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.hashtags;
    }
    

    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 == this.hashtagDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Hashtag save(Hashtag c) {
        this.hashtagDAO.persistent(c);
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
            Hashtag hashtag = Hashtag.fromJson(newJsonObj);
            Long id  = hashtag.getId();
            if(this.hashtagDAO.exsit(id)){
                hashtag .setUpdateTime(new Date());
            }else{
                hashtag.setId(null);
                hashtag.setAddTime(new Date());
                hashtag .setUpdateTime(new Date());
            }
            this.saveOrUpdate(hashtag);
        }
        this.refresh();
        return true;
    }

    private Hashtag saveOrUpdate(Hashtag hashtag) {
        if(hashtag.getId() == null){
           return this.save (hashtag);
        }else{
           return this.update(hashtag);
        }
    }

}
