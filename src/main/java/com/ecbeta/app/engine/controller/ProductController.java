package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Product;

import com.ecbeta.app.engine.servicer.ProductServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class ProductController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ProductServicer", spring="")
    private ProductServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "ProductForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Product";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (ProductServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Product.COLUMNS);
    }
    
   
}
