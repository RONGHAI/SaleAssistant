package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Attachment;

import com.ecbeta.app.engine.servicer.AttachmentServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class AttachmentController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.AttachmentServicer", spring="")
    private AttachmentServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "AttachmentForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Attachment";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (AttachmentServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Attachment.COLUMNS);
    }
    
   
}
