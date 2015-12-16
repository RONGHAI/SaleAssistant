/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.dao;

import com.weinyc.sa.app.model.Navigation;
import com.weinyc.sa.app.model.User;
import com.weinyc.sa.core.dao.AbstractModelDAO;
import java.util.List;

/**
 *
 * @author ronghai
 */
public interface NavigationDAO extends AbstractModelDAO<Navigation>{
    public List<Navigation> find(User user);
}
