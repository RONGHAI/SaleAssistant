package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.InvoiceServicer;
import com.weinyc.sa.app.model.Invoice;
public class InvoiceController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.InvoiceServicer", spring="")
    private InvoiceServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "InvoiceForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Invoice";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (InvoiceServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
   
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Invoice.COLUMNS);
    }
    
   
}
