/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.TrackingDAO;
import com.weinyc.sa.app.model.Tracking;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author ronghai
 */
public class TrackingDAOImpl extends AbstractModelDAOImpl<Tracking> implements TrackingDAO {
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<Tracking> createRowMapper() {
        return Tracking._getModelMeta().getRowMapper();
    }
}
