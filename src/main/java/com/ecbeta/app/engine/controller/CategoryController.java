package com.ecbeta.app.engine.controller;

import me.ronghai.sa.model.Category;

import com.ecbeta.app.engine.servicer.CategoryServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
public class CategoryController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.CategoryServicer", spring="")
    private CategoryServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "CategoryForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Category";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (CategoryServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Category.COLUMNS);
    }
    
   
   
    
}
