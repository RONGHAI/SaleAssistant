package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.ClientServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import net.sf.json.JSONObject;
public class ClientController extends AbstractController{

    
    @ServicerType("com.ecbeta.app.engine.servicer.ClientServicer")
    private ClientServicer servicer;
    
    @Override
    public String getAppName () {
        return "Client";
    }

    @Override
    public String getFORM_NAME () {
        return "ClientForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Client";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (ClientServicer servicer) {
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
        List<?> list = this.servicer.getClients();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        map.put("records", list);
        return map;
    }
    
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        String s = "[				\n" +
"                                            { field: 'name', caption: 'Name', size: '20%', sortable: true },\n" +
"                                            { field: 'wangwang', caption: 'Wangwang', size: '20%', sortable: true },\n" +
"                                            { field: 'qq', caption: 'QQ', size: '20%', sortable: true },\n" +
"                                            { field: 'birthday', caption: 'Birthday', size: '120px', sortable: true },\n" +
"                                            { field: 'gender', caption: 'Gender', size: '120px', sortable: true },\n" +
"                                            { field: 'phone', caption: 'Phone', size: '120px', sortable: true },\n" +
"                                    ]";
        return s;
        
    }
}
