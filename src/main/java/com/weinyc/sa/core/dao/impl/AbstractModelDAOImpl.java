/*
 * Copyright (C) 2014 Ronghai Wei <ronghai.wei@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.weinyc.sa.core.dao.impl;

import java.io.Serializable;

import com.weinyc.sa.core.model.AbstractModel;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 * @param <E>
 */
public class AbstractModelDAOImpl<E extends AbstractModel> extends AbstractModelDAOWithJDBCImpl<E> implements  Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /*
    protected AbstractModelDAO<E> dao; 
    
    public AbstractModelDAOImpl(AbstractModelDAO<E> dao){
        this.dao = dao;
    }
    
    @Override
    public void persistent(E entity) {
        dao.persistent(entity);
    }

    @Override
    public void remove(E entity, boolean force) {
        dao.remove(entity, force);
    }

    @Override
    public void remove(E entity) {
        dao.remove(entity);
    }

    @Override
    public int remove(boolean force, Collection ids) {
        return dao.remove(force, ids);
    }

    @Override
    public E merge(E entity) {
        return (E)dao.merge(entity);
    }

    @Override
    public E update(E entity) {
        return dao.update(entity);
    }

    @Override
    public E find(Object id) {
        return dao.find(id);
    }

    @Override
    public List find() {
        return dao.find();
    }

    @Override
    public List find(String condition) {
        return dao.find(condition);
    }

    @Override
    public List find(int from, int size, String condition) {
        return dao.find(from, size, condition);
    }

    @Override
    public long count(String configure) {
        return dao.count(configure);
    }

    @Override
    public List execute(String sql) {
        return dao.execute(sql);
    }

    @Override
    public int update(String sql) {
        return dao.update(sql);
    }

    @Override
    public EntityManager getEntityManager() {
        return dao.getEntityManager();
    }*/
}
