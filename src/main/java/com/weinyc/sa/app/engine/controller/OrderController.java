package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.OrderServicer;
import com.weinyc.sa.app.model.Order;
public class OrderController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.OrderServicer", spring="")
    private OrderServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "OrderForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Order";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (OrderServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Order.COLUMNS);
    }
    
   
}
