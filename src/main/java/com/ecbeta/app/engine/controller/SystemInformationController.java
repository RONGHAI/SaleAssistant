package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Property;

import com.ecbeta.app.engine.servicer.SystemInformationServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class SystemInformationController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.SystemInformationServicer", spring="")
    private SystemInformationServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "SystemInformationForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "SystemInformation";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (SystemInformationServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Property.COLUMNS);
    }
    
   
}
