package com.weinyc.sa.app.engine.controller;

import net.sf.json.JSONObject;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.AccountServicer;
import com.weinyc.sa.app.engine.servicer.MerchantServicer;
import com.weinyc.sa.app.model.Account;
public class AccountController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.AccountServicer", spring="")
    private AccountServicer servicer;
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.MerchantServicer", spring="")
    private MerchantServicer merchantServicer;
    
    
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
    
    

    public MerchantServicer getMerchantServicer() {
        return merchantServicer;
    }

    public void setMerchantServicer(MerchantServicer merchantServicer) {
        this.merchantServicer = merchantServicer;
    }
    
    public Object listMerchantsAction(){
        return this.merchantServicer.getJSONArray(new JSONObject());
    }
    
   
}
