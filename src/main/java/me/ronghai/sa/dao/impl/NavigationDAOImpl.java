/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import me.ronghai.sa.dao.NavigationDAO;
import me.ronghai.sa.model.Navigation;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class NavigationDAOImpl extends AbstractModelDAOImpl<Navigation> implements NavigationDAO{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public RowMapper<Navigation> createRowMapper() {
        return Navigation._getModelMeta().getRowMapper();
     /* return new RowMapper<Navigation>() {
          @Override
          public Navigation mapRow(ResultSet rs, int rowNum) throws SQLException {
              
              return Navigation._getModelMeta().getRowMapper();
              
              Navigation bean  = new Navigation();
              bean.setId(rs.getLong("ID"));
              bean.setAddTime(rs.getDate("add_time"));
              bean.setDisabled(rs.getBoolean("disabled"));
              bean.setUpdateTime(rs.getDate("update_time"));
              bean.setNote(rs.getString("note"));
              bean.setOrder(rs.getInt("order"));
              bean.setWorker(rs.getString("worker"));
              bean.setTier_1(rs.getLong("tier_1"));
              bean.setTier_2(rs.getLong("tier_2"));
              bean.setTier_3(rs.getLong("tier_3"));
              bean.setTier_4(rs.getLong("tier_4"));
              bean.setLabel(rs.getString("label"));
              return bean;
          }
      } ;*/
  }
}
