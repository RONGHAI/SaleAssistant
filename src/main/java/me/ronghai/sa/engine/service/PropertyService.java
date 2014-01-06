/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.engine.service;

import java.util.Collection;
import java.util.List;
import me.ronghai.sa.model.Property;

/**
 *
 * @author ronghai
 */
public interface PropertyService {
    public Property update(Property entity);
    public Property find(Object id);
    public void remove(Property c);
    public List<Property> find();
    public void remove(Long ... ids);
    public void remove(Collection<Long> ids);
    public String findPropertyValue(String code);
    public Property save(Property c);
}
