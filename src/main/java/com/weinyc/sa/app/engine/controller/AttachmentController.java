package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.AttachmentServicer;
import com.weinyc.sa.app.model.Attachment;
public class AttachmentController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.AttachmentServicer", spring="")
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
