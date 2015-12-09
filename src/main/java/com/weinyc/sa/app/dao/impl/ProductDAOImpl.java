/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.app.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.ProductDAO;
import com.weinyc.sa.app.model.Product;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author ronghai
 */
public class ProductDAOImpl extends AbstractModelDAOImpl<Product> implements ProductDAO{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public RowMapper<Product> createRowMapper() {
        return Product._getModelMeta().getRowMapper();
    }

   

    @Override
    public List<Object> findCategories(Long productId) {
        return findRelatedIDs("PRODUCT_CATEGORIES", "CATEGORY_ID", "PRODUCT_ID", productId);
    }
    
    
    @Override
    public List<Object> findImages(Long productId) {
        return findRelatedIDs("PRODUCT_IMAGES", "IMAGE_ID", "PRODUCT_ID", productId);
    }

    
    
    
   public void updateRelated(Product pro){
       updateRelatedIDs("PRODUCT_IMAGES", "IMAGE_ID", "PRODUCT_ID",  pro.getId(), pro.getAttachments());
       updateRelatedIDs("PRODUCT_CATEGORIES", "CATEGORY_ID", "PRODUCT_ID",   pro.getId(), pro.getCategories());
   }


    
    @Override
    public void removeRelated(Product pro) {
        removeRelatedIDs("PRODUCT_IMAGES", "IMAGE_ID", "PRODUCT_ID",  pro.getId());
        removeRelatedIDs("PRODUCT_CATEGORIES", "CATEGORY_ID", "PRODUCT_ID",   pro.getId());
        
    }
        
 
    
}
