/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.dao.impl;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.app.dao.NavigationDAO;
import com.weinyc.sa.app.model.Navigation;
import com.weinyc.sa.app.model.User;
import com.weinyc.sa.common.constants.Constants;
import com.weinyc.sa.core.dao.impl.AbstractModelDAOImpl;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public List<Navigation> find(User user) {
        if(user == null){
            List<Navigation> finds = find();
            Navigation login = null;
            for(Navigation v : finds){
                if(v.getWorker() != null && v.getWorker().endsWith(Constants.LOGIN_WORKER)){
                    login = v;
                    break;
                }
            }
            if(login != null){
                //will update it later, now only support 2 levels.
                int[]p = login.getParentNavTier();
                for (Iterator<Navigation> iterator = finds.iterator(); iterator.hasNext();) {
                    Navigation next = iterator.next();
                    if(next.getId().equals(login.getId()) || Arrays.equals(p, next.getNavTier())){
                    }else{
                        iterator.remove();
                    }
                }
            }            
            return finds;
        }else{
            return find();
        }
     }
}
