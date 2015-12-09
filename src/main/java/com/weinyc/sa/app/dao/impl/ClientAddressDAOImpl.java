/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.ClientAddressDAO;
import com.weinyc.sa.app.model.ClientAddress;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

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
