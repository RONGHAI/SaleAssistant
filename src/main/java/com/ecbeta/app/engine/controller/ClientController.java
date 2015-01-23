package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Client;
import me.ronghai.sa.model.ClientAddress;

import com.ecbeta.app.engine.servicer.ClientAddressServicer;
import com.ecbeta.app.engine.servicer.ClientServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class ClientController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ClientServicer", spring="clientService")
    private ClientServicer servicer;
    
    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ClientAddressServicer", spring="clientService")
    private ClientAddressServicer addressServicer;
    
    
    
    
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

    public ClientAddressServicer getAddressServicer() {
        return addressServicer;
    }

    public void setAddressServicer(ClientAddressServicer addressServicer) {
        this.addressServicer = addressServicer;
    }
    
    public Object getAddressColumnsAction(){
        return this.getAddressColumns(); 
    }
    
    
    public String getAddressColumns(){
        return JSONUtils.toString(ClientAddress.COLUMNS, 36);
    }
    @Override
    public AbstractServicer getServicer (String swithServicer){
        if("addressServicer".equals(swithServicer)){
            return this.addressServicer;
        }
        return this.servicer;
    }
   
}
