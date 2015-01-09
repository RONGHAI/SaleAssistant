package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.ClientAddressServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.ClientAddress;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class ClientAddressController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ClientAddressServicer", spring="")
    private ClientAddressServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "ClientAddressForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "ClientAddress";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (ClientAddressServicer servicer) {
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
        List<ClientAddress> list = this.servicer.getClientAddreses();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(ClientAddress c : list ){
            array.add(c.toJson());
        }
        map.put("records", array);
        return map;
    }
    
    @Override
    public Object saveRecordsAction(JSONObject json) {
        System.out.println("~~~~~saveRecordsAction~~~~"+json.get("changed"));
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
        return ClientAddress.COLUMNS.toString();
    }
    
   
}
