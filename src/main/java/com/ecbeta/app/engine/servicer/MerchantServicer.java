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

import me.ronghai.sa.dao.impl.MerchantDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Merchant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class MerchantServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.MerchantDAOImpl merchantDAO;

    public MerchantDAOImpl getMerchantDAO() {
        return merchantDAO;
    }

    public void setMerchantDAO(MerchantDAOImpl merchantDAO) {
        this.merchantDAO = merchantDAO;
    }

    private List<Merchant> merchants;

    public List<Merchant> getMerchants() {
        return merchants;
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
        this.merchants = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Merchant update(Merchant entity) {
        Merchant c = merchantDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Merchant find(Object id) {
        return merchantDAO.find(id);
    }

    public List<Merchant> find() {
        return merchantDAO.find(" WHERE disabled = false ");
    }


    public void remove(Merchant c) {
        this.merchantDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.merchantDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(){
        return JSONUtils.toJSONArray(this.merchants);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.merchants;
    }
    
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if(0 == this.merchantDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Merchant save(Merchant c) {
        this.merchantDAO.persistent(c);
        this.refresh();
        return c;
    }
    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null  || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Merchant merchant = Merchant.fromJson(newJsonObj);
            Long id  = merchant.getId();
            if(this.merchantDAO.exsit(id)){
                merchant .setUpdateTime(new Date());
            }else{
                merchant.setId(null);
                merchant.setAddTime(new Date());
                merchant .setUpdateTime(new Date());
            }
            this.saveOrUpdate(merchant);
        }
        this.refresh();
        return true;
    }

    private Merchant saveOrUpdate(Merchant merchant) {
        if(merchant.getId() == null){
           return this.save (merchant);
        }else{
           return this.update(merchant);
        }
    }

}
