/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import me.ronghai.sa.dao.InvoiceDAO;
import me.ronghai.sa.model.Invoice;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class InvoiceDAOImpl extends AbstractModelDAOImpl<Invoice> implements InvoiceDAO {

    @Override
    public RowMapper<Invoice> createRowMapper() {
        return Invoice._getModelMeta().getRowMapper();
    }
}
