/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.OrderDAO;
import me.ronghai.sa.model.Order;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class OrderDAOImpl extends AbstractModelDAOImpl<Order> implements OrderDAO {

    @Override
    public RowMapper<Order> createRowMapper() {
        return Order._getModelMeta().getRowMapper();
    }
}
