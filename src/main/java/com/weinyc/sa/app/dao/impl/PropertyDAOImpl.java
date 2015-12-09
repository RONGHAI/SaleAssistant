/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.PropertyDAO;
import com.weinyc.sa.app.model.Property;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author ronghai
 */
public class PropertyDAOImpl extends AbstractModelDAOImpl<Property> implements PropertyDAO {
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<Property> createRowMapper() {
        return Property._getModelMeta().getRowMapper();

    }
}
