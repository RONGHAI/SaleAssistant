/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import me.ronghai.sa.dao.CurrencyDAO;
import me.ronghai.sa.model.Currency;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class CurrencyDAOImpl extends AbstractModelDAOImpl<Currency> implements CurrencyDAO {

    @Override
    public RowMapper<Currency> createRowMapper() {
        return Currency._getModelMeta().getRowMapper();
    }
}
