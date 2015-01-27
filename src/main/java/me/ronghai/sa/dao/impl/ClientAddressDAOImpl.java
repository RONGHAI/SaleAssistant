/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.ClientAddressDAO;
import me.ronghai.sa.model.ClientAddress;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class ClientAddressDAOImpl extends AbstractModelDAOImpl<ClientAddress> implements ClientAddressDAO{
    private static final long serialVersionUID = 1L;
    
    @Override
    public RowMapper<ClientAddress> createRowMapper() {
         return ClientAddress._getModelMeta().getRowMapper();
    }
}
