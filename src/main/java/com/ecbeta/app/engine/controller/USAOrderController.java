package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.USAOrder;

import com.ecbeta.app.engine.servicer.USAOrderServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class USAOrderController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.USAOrderServicer", spring="")
    private USAOrderServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "USAOrderForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "USAOrder";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (USAOrderServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    

    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(USAOrder.COLUMNS);
    }
    
   
}
