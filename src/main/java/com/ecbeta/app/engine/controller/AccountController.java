package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Account;

import com.ecbeta.app.engine.servicer.AccountServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class AccountController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.AccountServicer", spring="")
    private AccountServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "AccountForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Account";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (AccountServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Account.COLUMNS, 36);
    }
    
   
}
