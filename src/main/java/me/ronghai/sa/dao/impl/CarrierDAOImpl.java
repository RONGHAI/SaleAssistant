/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.dao.impl;
import me.ronghai.sa.dao.CarrierDAO;
import me.ronghai.sa.model.Carrier;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ronghai
 */
public class CarrierDAOImpl extends AbstractModelDAOImpl<Carrier> implements CarrierDAO{
    
    @Override
    public RowMapper<Carrier> createRowMapper(){
        return Carrier._getModelMeta().getRowMapper();
    }
}
