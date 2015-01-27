
package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.MerchantDAO;
import me.ronghai.sa.model.Merchant;

import org.springframework.jdbc.core.RowMapper;

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
