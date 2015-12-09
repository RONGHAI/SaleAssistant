/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.HashtagDAO;
import com.weinyc.sa.app.model.Hashtag;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author ronghai
 */
public class HashtagDAOImpl extends AbstractModelDAOImpl<Hashtag> implements HashtagDAO {
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<Hashtag> createRowMapper() {
        return Hashtag._getModelMeta().getRowMapper();
    }
}
