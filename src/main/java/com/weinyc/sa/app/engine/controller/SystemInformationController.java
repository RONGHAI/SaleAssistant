package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.SystemInformationServicer;
import com.weinyc.sa.app.model.Property;
public class SystemInformationController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.SystemInformationServicer", spring="")
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
