package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.ClientAddressServicer;
import com.weinyc.sa.app.engine.servicer.ClientServicer;
import com.weinyc.sa.app.model.Client;
import com.weinyc.sa.app.model.ClientAddress;
public class ClientController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ClientServicer", spring="clientService")
    private ClientServicer servicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ClientAddressServicer", spring="clientService")
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
            this.addressServicer.setClient(new Long(this.getRequest().getParameter("client")));
            return this.addressServicer;
        }
        return this.servicer;
    }
   
}
