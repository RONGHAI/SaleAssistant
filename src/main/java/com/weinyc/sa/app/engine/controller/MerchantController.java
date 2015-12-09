package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.MerchantServicer;
import com.weinyc.sa.app.model.Merchant;
public class MerchantController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.MerchantServicer", spring="")
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
