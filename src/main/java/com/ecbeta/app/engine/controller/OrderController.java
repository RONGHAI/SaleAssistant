package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Order;

import com.ecbeta.app.engine.servicer.OrderServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class OrderController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.OrderServicer", spring="")
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
