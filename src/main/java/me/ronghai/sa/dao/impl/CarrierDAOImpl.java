/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.ronghai.sa.dao.CarrierDAO;
import me.ronghai.sa.model.Carrier;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class CarrierDAOImpl extends AbstractModelDAOImpl<Carrier> implements CarrierDAO{
    
    
    @Override
    public RowMapper<Carrier> createRowMapper() {
      return new RowMapper() {
          @Override
          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
              Carrier bean  = new Carrier();
              bean.setId(rs.getLong("ID"));
              bean.setAddTime(rs.getDate("add_time"));
              bean.setDisabled(rs.getBoolean("disabled"));
              bean.setUpdateTime(rs.getDate("update_time"));
              bean.setNote(rs.getString("note"));
              bean.setTrackMethod(rs.getString("track_method"));
              bean.setTrackURL(rs.getString("track_url"));
              bean.setName(rs.getString("name"));
              bean.setWebsite(rs.getString("website"));
              return bean;
          }
      } ;
  }
}
