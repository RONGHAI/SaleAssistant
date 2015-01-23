package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.ClientAddress;

import com.ecbeta.app.engine.servicer.ClientAddressServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
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
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(ClientAddress.COLUMNS);
    }
    
   
}
