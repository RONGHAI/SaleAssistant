package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.CurrencyServicer;
import com.weinyc.sa.app.model.Currency;
public class CurrencyController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CurrencyServicer", spring="")
    private CurrencyServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "CurrencyForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Currency";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (CurrencyServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
 
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Currency.COLUMNS);
    }
    
   
}
