package com.weinyc.sa.app.engine.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.CategoryServicer;
import com.weinyc.sa.app.engine.servicer.ProductServicer;
import com.weinyc.sa.app.model.Product;
public class ProductController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ProductServicer", spring="")
    private ProductServicer servicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CategoryServicer", spring="")
    private CategoryServicer categoryServicer;
    
    public CategoryServicer getCategoryServicer() {
        return categoryServicer;
    }

    public void setCategoryServicer(CategoryServicer categoryServicer) {
        this.categoryServicer = categoryServicer;
    }

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
    
   
    @Override
    public AbstractServicer getServicer (String swithServicer){
        if("categoryServicer".equals(swithServicer)){
            return this.categoryServicer;
        }
        return this.getServicer();
    }

    
    public Object listCategoriesAction(){
        JSONObject json = this.getJSONObject();
        String swithServicer = json == null ? null : (String)json.get("servicer");
        if(swithServicer == null){
            swithServicer = this.getRequest().getParameter("servicer");
        }
        AbstractServicer servicer =    this.getServicer(swithServicer);
        if(servicer instanceof CategoryServicer){
            CategoryServicer catservicer = (CategoryServicer)servicer;
            return catservicer.getJSONArrayWithoutSort(json);
        }
        return new JSONArray();
    }
}
