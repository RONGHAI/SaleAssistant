package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Currency;

import com.ecbeta.app.engine.servicer.CurrencyServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class CurrencyController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.CurrencyServicer", spring="")
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
