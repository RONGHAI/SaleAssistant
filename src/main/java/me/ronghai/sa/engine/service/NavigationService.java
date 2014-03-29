/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.engine.service;

import java.util.Collection;
import java.util.List;
import me.ronghai.sa.model.Navigation;

/**
 *
 * @author ronghai
 */
public interface NavigationService {
    public Navigation update(Navigation entity);
    public Navigation find(Object id);
    public void remove(Navigation c);
    public List<Navigation> find();
    public void remove(Long ... ids);
    public void remove(Collection<Long> ids);
    public Navigation save(Navigation c);
}
