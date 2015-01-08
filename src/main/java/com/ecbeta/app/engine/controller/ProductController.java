package com.ecbeta.app.engine.controller;

import com.ecbeta.app.engine.servicer.ProductServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractController;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.util.JSONUtils;
import java.util.List;
import me.ronghai.sa.model.Product;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class ProductController extends AbstractController{

    
    @ServicerType(value="com.ecbeta.app.engine.servicer.ProductServicer", spring="clientService")
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
    
    @Override
    public Object deleteRecordsAction(JSONObject json) {
        this.servicer.remove(JSONUtils.toCollection(json, "selected", Long.class));
        JSONObject map = new JSONObject();
        map.put("status", "success");
        return map;
    }

    @Override
    public Object getRecordsAction(JSONObject json) {
        List<Product> list = this.servicer.getProducts();
        JSONObject map = new JSONObject();
        map.put("status", "success");
        map.put("total", list.size());
        
        JSONArray array = new JSONArray();
        for(Product c : list ){
            array.add(c.toJson());
        }
        map.put("records", array);
        return map;
    }
    
    @Override
    public Object saveRecordsAction(JSONObject json) {
        System.out.println("~~~~~saveRecordsAction~~~~"+json.get("changed"));
        JSONArray jsonArray = (JSONArray)json.get("changed");
        
        servicer.saveOrUpdate(jsonArray);
        
        JSONObject map = new JSONObject();
        map.put("success", true);
        return map;
    }
    
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return Product.COLUMNS.toString();
    }
    
   
}
