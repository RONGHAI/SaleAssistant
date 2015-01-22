package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.USAOrderServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;

import java.util.List;

import me.ronghai.sa.model.USAOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class USAOrderController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.USAOrderServicer", spring="")
    private USAOrderServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "USAOrderForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "USAOrder";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (USAOrderServicer servicer) {
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
        List<USAOrder> list = this.servicer.getUSAOrders();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(USAOrder c : list ){
            array.add(c.toJson());
        }
        map.put("records", array);
        return map;
    }
    
    @Override
    public Object saveRecordsAction(JSONObject json) {
        JSONArray jsonArray = JSONUtils.getChanges(json);
        System.out.println("~~~~~saveRecordsAction~~~~"+jsonArray);
        
        servicer.saveOrUpdate(jsonArray);
        
        JSONObject map = new JSONObject();
        map.put("success", true);
        return map;
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(USAOrder.COLUMNS);
    }
    
   
}
