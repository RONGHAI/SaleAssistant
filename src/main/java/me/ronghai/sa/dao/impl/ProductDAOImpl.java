/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import java.util.List;

import me.ronghai.sa.dao.ProductDAO;
import me.ronghai.sa.model.Product;

import org.springframework.jdbc.core.RowMapper;

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
