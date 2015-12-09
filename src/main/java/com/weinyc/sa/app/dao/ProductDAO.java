/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.dao;

import java.util.List;

import com.weinyc.sa.app.model.Product;
import com.weinyc.sa.core.dao.AbstractModelDAO;

/**
 *
 * @author ronghai
 */
public interface ProductDAO extends AbstractModelDAO<Product>{
    public List<Object> findCategories(Long productId);

    public List<Object> findImages(Long id);
    
    public void updateRelated(Product pro);

    public void removeRelated(Product entity);
}

