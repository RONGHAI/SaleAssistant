package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.AccountServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.Account;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class AccountController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.AccountServicer", spring="clientService")
    private AccountServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "AccountForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Account";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (AccountServicer servicer) {
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
        List<Account> list = this.servicer.getAccounts();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(Account c : list ){
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
        return Account.COLUMNS.toString();
    }
    
   
}
