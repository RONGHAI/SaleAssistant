/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;
import me.ronghai.sa.dao.AttachmentDAO;
import me.ronghai.sa.model.Attachment;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class AttachmentDAOImpl extends AbstractModelDAOImpl<Attachment> implements AttachmentDAO{
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<Attachment> createRowMapper(){
        return Attachment._getModelMeta().getRowMapper();
    }
}
