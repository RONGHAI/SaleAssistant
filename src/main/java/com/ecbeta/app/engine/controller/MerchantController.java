package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Merchant;

import com.ecbeta.app.engine.servicer.MerchantServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class MerchantController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.MerchantServicer", spring="")
    private MerchantServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "MerchantForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Merchant";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (MerchantServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Merchant.COLUMNS, 36);
    }
    
   
}
