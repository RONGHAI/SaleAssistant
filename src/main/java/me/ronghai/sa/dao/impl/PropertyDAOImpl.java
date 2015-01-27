/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.PropertyDAO;
import me.ronghai.sa.model.Property;

import org.springframework.jdbc.core.RowMapper;

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
