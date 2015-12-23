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
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ronghai
 */
public class NavigationDAOImpl extends AbstractModelDAOImpl<Navigation> implements NavigationDAO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public RowMapper<Navigation> createRowMapper() {
        return Navigation._getModelMeta().getRowMapper();
    }

    @Override
    public List<Navigation> findWithWorker(){
        return find(" WHERE worker <> '' ");
    }
     
    @Override
    public List<Navigation> find(User user) {
        if (user == null) {
            List<Navigation> finds = find();
            Navigation login = null;
            for (Navigation v : finds) {
                if (v.getWorker() != null && v.getWorker().endsWith(Constants.LOGIN_WORKER)) {
                    login = v;
                    break;
                }
            }
            if (login != null) {
                for (Iterator<Navigation> iterator = finds.iterator(); iterator.hasNext();) {
                    Navigation next = iterator.next();
                    if (StringUtils.isNotEmpty(next.getWorker()) || next.getId().equals(login.getId())) {
                    } else {
                        iterator.remove();
                    }
                }
            }
            return finds;
        } else {
            return find();
        }
    }
}
