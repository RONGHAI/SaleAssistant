/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.USAOrderDAO;
import me.ronghai.sa.model.USAOrder;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class USAOrderDAOImpl extends AbstractModelDAOImpl<USAOrder> implements USAOrderDAO {
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<USAOrder> createRowMapper() {
        return USAOrder._getModelMeta().getRowMapper();

    }
}
