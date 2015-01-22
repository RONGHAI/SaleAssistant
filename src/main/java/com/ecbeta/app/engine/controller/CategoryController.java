package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.CategoryServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.Category;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    
    @Override
    public Object deleteRecordsAction(JSONObject json) {
        this.servicer.remove(JSONUtils.toCollection(json, "selected", Long.class));
        JSONObject map = new JSONObject();
        map.put("status", "success");
        return map;
    }

    @Override
    public Object getRecordsAction(JSONObject json) {
        List<Category> list = this.servicer.getCategories();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(Category c : list ){
            array.add(c.toJson());
        }
        map.put("records", array);
        return map;
    }
    
    @Override
    public Object saveRecordsAction(JSONObject json) {
        JSONArray jsonArray = JSONUtils.getChanges(json);
        servicer.saveOrUpdate(jsonArray);
        
        JSONObject map = new JSONObject();
        map.put("success", true);
        return map;
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Category.COLUMNS);
    }
    
   
}
