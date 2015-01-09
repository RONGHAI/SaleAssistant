/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.CategoryDAO;
import me.ronghai.sa.model.Category;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class CategoryDAOImpl extends AbstractModelDAOImpl<Category> implements CategoryDAO{
   
    
    @Override
    public RowMapper<Category> createRowMapper() {
        return Category._getModelMeta().getRowMapper();
  }
}
