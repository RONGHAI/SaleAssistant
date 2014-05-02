/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.ronghai.sa.dao.PropertyDAO;
import me.ronghai.sa.model.Property;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class PropertyDAOImpl extends AbstractModelDAOImpl<Property> implements PropertyDAO{
    
    
    @Override
    public RowMapper<Property> createRowMapper() {
      return new RowMapper() {
          @Override
          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
              Property bean  = new Property();
              bean.setId(rs.getLong("ID"));
              bean.setCode(rs.getString("CODE"));
              bean.setValue(rs.getString("VALUE"));;
              bean.setAddTime(rs.getDate("add_time"));
              bean.setDisabled(rs.getBoolean("disabled"));
              bean.setUpdateTime(rs.getDate("update_time"));
              bean.setNote(rs.getString("note"));
              return bean;
          }
      } ;
  }
}
