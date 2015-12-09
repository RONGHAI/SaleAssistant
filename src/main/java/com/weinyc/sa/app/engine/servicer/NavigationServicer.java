/*
 * Copyright (C) 2014 Weiwei
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.dao.impl.NavigationDAOImpl;
import com.weinyc.sa.app.model.Navigation;
import com.weinyc.sa.core.model.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Weiwei
 */
public class NavigationServicer extends AbstractServicer  {

    @Autowired
    private com.weinyc.sa.app.dao.impl.NavigationDAOImpl navigationDAO;

    public NavigationDAOImpl getNavigationDAO() {
        return navigationDAO;
    }

    public void setNavigationDAO(NavigationDAOImpl navigationDAO) {
        this.navigationDAO = navigationDAO;
    }

   
    private List<Navigation> navigations;

    public List<Navigation> getNavigations() {
        return navigations;
    }

    public void setNavigations(List<Navigation> navigations) {
        this.navigations = navigations;
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
        this.navigations = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Navigation update(Navigation entity) {
        Navigation c = navigationDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Navigation find(Object id) {
        return navigationDAO.find(id);
    }

    public List<Navigation> find() {
        return navigationDAO.find(" WHERE disabled = false ");
    }


    public void remove(Navigation c) {
        this.navigationDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.navigationDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.navigations, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.navigations;
    }
    
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty()) return false;
        if( 0 == this.navigationDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public Navigation save(Navigation c) {
        this.navigationDAO.persistent(c);
        this.refresh();
        return c;
    }

    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null  || jsonArray.isArray()) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Navigation client = Navigation.fromJson(newJsonObj);
            Long id  = client.getId();
            if(this.navigationDAO.exsit(id)){
                client .setUpdateTime(new Date());
            }else{
                client.setId(null);
                client.setAddTime(new Date());
                client .setUpdateTime(new Date());
            }
            this.saveOrUpdate(client);
        }
        this.refresh();
        return true;
    }

    private Navigation saveOrUpdate(Navigation client) {
        if(client.getId() == null){
           return this.save (client);
        }else{
           return this.update(client);
        }
    }
    
}
