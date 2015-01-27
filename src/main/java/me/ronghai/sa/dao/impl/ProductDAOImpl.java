/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.ProductDAO;
import me.ronghai.sa.model.Product;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class ProductDAOImpl extends AbstractModelDAOImpl<Product> implements ProductDAO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public RowMapper<Product> createRowMapper() {
        return Product._getModelMeta().getRowMapper();

    }
}
