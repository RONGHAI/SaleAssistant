/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.ronghai.sa.dao.OrderDAO;
import me.ronghai.sa.model.Order;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class OrderDAOImpl extends AbstractModelDAOImpl<Order> implements OrderDAO{
   
    
    @Override
    public RowMapper<Order> createRowMapper() {
      return new RowMapper() {
          @Override
          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
              Order bean  = new Order();
              bean.setId(rs.getLong("ID"));
              bean.setAddTime(rs.getDate("add_time"));
              bean.setDisabled(rs.getBoolean("disabled"));
              bean.setUpdateTime(rs.getDate("update_time"));
              bean.setNote(rs.getString("note"));
              bean.setBirthday(rs.getDate("birthday"));
              bean.setGender(rs.getString("gender"));
              bean.setName(rs.getString("name"));
              bean.setPhone(rs.getString("phone"));
              bean.setQq(rs.getString("qq"));
              bean.setQqName(rs.getString("qq_name"));
              bean.setWangwang(rs.getString("wangwang"));
              return bean;
          }
      } ;
  }
}
