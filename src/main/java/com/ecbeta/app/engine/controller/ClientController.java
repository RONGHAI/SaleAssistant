package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Client;

import com.ecbeta.app.engine.servicer.ClientServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class ClientController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ClientServicer", spring="clientService")
    private ClientServicer servicer;
    
    
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
    
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Client.COLUMNS, 36);
    }
    
   
}
