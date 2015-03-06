/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

   

    @Override
    public List<Object> findCategories(Long productId) {
        String sql = " SELECT CATEGORY_ID FROM `product_categories`  WHERE product_id = ? ";
        List<Object> ids = this.databaseHandler.query(sql, new Object[]{productId},  new RowMapper<Object> (){
            @Override
            public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
                 return arg0.getLong("CATEGORY_ID");
            }});
        
        return ids;
    }
    
    
  /*  String sql = "SELECT * FROM " + table(entityClass) + " WHERE id = ? ";
    E e = this.databaseHandler.queryForObject(sql, new Object[]{id}, createRowMapper());
    return this.afterFind(e);*/
    
    
}
