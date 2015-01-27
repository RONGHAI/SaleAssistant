/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.HashtagDAO;
import me.ronghai.sa.model.Hashtag;

import org.springframework.jdbc.core.RowMapper;

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
