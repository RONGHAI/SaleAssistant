package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.CarrierServicer;
import com.weinyc.sa.app.model.Carrier;
public class CarrierController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CarrierServicer", spring="")
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
