package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import me.ronghai.sa.dao.impl.ProductDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Product;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.ProductDAOImpl productDAO;

    public ProductDAOImpl getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAOImpl productDAO) {
        this.productDAO = productDAO;
    }

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    /**
     *
     */
    private static final long serialVersionUID = 4874672762001288584L;

    @Override
    public void init(NavigationBean navBean) {
        this.refresh();
    }

    private void refresh() {
        this.products = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Product update(Product entity) {
        Product c = productDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Product find(Object id) {
        return productDAO.find(id);
    }

    public List<Product> find() {
        return productDAO.find(" WHERE disabled = false ");
    }


    public void remove(Product c) {
        this.productDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.productDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(){
        return JSONUtils.toJSONArray(this.products);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.products;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 == this.productDAO.remove(false, new ArrayList<>(ids))){
            return false;
        };
        this.refresh();
        return true;
    }

    public Product save(Product c) {
        this.productDAO.persistent(c);
        this.refresh();
        return c;
    }
    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Product product = Product.fromJson(newJsonObj);
            Long id  = product.getId();
            if(this.productDAO.exsit(id)){
                product .setUpdateTime(new Date());
            }else{
                product.setId(null);
                product.setAddTime(new Date());
                product .setUpdateTime(new Date());
            }
            this.saveOrUpdate(product);
        }
        this.refresh();
        return true;
    }

    private Product saveOrUpdate(Product product) {
        if(product.getId() == null){
           return this.save (product);
        }else{
           return this.update(product);
        }
    }

}
