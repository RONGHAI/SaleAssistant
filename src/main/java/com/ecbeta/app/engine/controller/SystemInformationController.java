package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.SystemInformationServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.Property;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class SystemInformationController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.SystemInformationServicer", spring="")
    private SystemInformationServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "SystemInformationForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "SystemInformation";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (SystemInformationServicer servicer) {
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
        List<Property> list = this.servicer.getSystemInformations();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        JSONArray array = new JSONArray();
        for(Property c : list ){
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
        return Property.COLUMNS.toString();
    }
    
   
}
