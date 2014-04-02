package com.ecbeta.app.engine.worker;

import com.ecbeta.app.engine.servicer.ClientServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class ClientWorker extends AbstractWorker{

    
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
        JSONObject<String, String> map = new JSONObject();
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
}
