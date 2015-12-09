
package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.MerchantDAO;
import com.weinyc.sa.app.model.Merchant;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;

/**
 *
 * @author ronghai
 */
public class MerchantDAOImpl extends AbstractModelDAOImpl<Merchant> implements MerchantDAO {
    private static final long serialVersionUID = 1L;
    @Override
    public RowMapper<Merchant> createRowMapper() {
        return Merchant._getModelMeta().getRowMapper();
    }
}
