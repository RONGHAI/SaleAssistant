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

package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.NavigationServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.Navigation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Weiwei
 */
public class NavigationController     extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.NavigationServicer", spring="navigationService")
    private NavigationServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "NavigationForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Navigation";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (NavigationServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
    @Override
    public Object deleteRecordsAction(JSONObject json) {
        this.servicer.remove(JSONUtils.toCollection(json, "selected", Long.class));
        JSONObject map = new JSONObject();
        map.put("status", "success");
        return map;
    }

    @Override
    public Object getRecordsAction(JSONObject json) {
        List<Navigation> list = this.servicer.getNavigations();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(Navigation c : list ){
            array.add(c.toJson());
        }
        map.put("records", array);
        return map;
    }
    
    @Override
    public Object saveRecordsAction(JSONObject json) {
        JSONArray jsonArray = (JSONArray)json.get("changed");
        servicer.saveOrUpdate(jsonArray);
        JSONObject map = new JSONObject();
        map.put("success", true);
        return map;
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return Navigation.COLUMNS.toString();
    }
    
   
}