package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.CategoryServicer;
import com.weinyc.sa.app.model.Category;
public class CategoryController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CategoryServicer", spring="")
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
