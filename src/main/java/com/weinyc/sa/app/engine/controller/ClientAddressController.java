package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.ClientAddressServicer;
import com.weinyc.sa.app.model.ClientAddress;
public class ClientAddressController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ClientAddressServicer", spring="")
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
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(ClientAddress.COLUMNS);
    }
    
   
}
