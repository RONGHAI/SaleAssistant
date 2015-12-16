/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.UserDAO;
import com.weinyc.sa.app.model.User;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author weiwei
 */
public class UserDAOImpl extends AbstractModelDAOImpl<User> implements UserDAO{
    private static final long serialVersionUID = 1L;
    
    @Override
    public RowMapper<User> createRowMapper() {
         return User._getModelMeta().getRowMapper();
  }

    @Override
    public User findByName(String username) {
        User user = this.find(false, new String[]{"name", "email"}, new Object[]{username, username});
        return user;
    }
}
