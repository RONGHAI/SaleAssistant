package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.HashtagServicer;
import com.weinyc.sa.app.model.Hashtag;
public class HashtagController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.HashtagServicer", spring="")
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
