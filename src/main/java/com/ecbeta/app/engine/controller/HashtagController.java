package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Hashtag;

import com.ecbeta.app.engine.servicer.HashtagServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class HashtagController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.HashtagServicer", spring="")
    private HashtagServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "HashtagForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Hashtag";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (HashtagServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Hashtag.COLUMNS);
    }
    
   
}
