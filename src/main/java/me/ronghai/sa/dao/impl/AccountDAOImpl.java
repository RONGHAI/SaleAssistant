/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.AccountDAO;
import me.ronghai.sa.model.Account;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class AccountDAOImpl extends AbstractModelDAOImpl<Account> implements AccountDAO{
   
    
    @Override
    public RowMapper<Account> createRowMapper() {
         return Account._getModelMeta().getRowMapper();
  }
}
