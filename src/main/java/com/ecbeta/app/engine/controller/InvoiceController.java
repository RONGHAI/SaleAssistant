package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Invoice;

import com.ecbeta.app.engine.servicer.InvoiceServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class InvoiceController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.InvoiceServicer", spring="")
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
