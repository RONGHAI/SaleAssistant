package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Carrier;

import com.ecbeta.app.engine.servicer.CarrierServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class CarrierController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.CarrierServicer", spring="")
    private CarrierServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "CarrierForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Carrier";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (CarrierServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Carrier.COLUMNS);
    }
    
   
}
