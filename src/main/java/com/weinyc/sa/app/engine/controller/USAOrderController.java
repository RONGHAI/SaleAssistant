package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.USAOrderServicer;
import com.weinyc.sa.app.model.USAOrder;
public class USAOrderController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.USAOrderServicer", spring="")
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
