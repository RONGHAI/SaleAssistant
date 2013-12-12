/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.engine.service.impl;

import java.io.Serializable;
import java.util.List;
import me.ronghai.sa.dao.CarrierDAO;
import me.ronghai.sa.engine.service.CarrierService;
import me.ronghai.sa.model.Carrier;

/**
 *
 * @author ronghai
 */
public class CarrierServiceImpl implements CarrierService, Serializable {

    private static final long serialVersionUID = 1L;
    private CarrierDAO carrierDAO;

    public CarrierDAO getCarrierDAO() {
        return carrierDAO;
    }

    public void setCarrierDAO(CarrierDAO carrierDAO) {
        this.carrierDAO = carrierDAO;
    }
     

    @Override
    public Carrier update(Carrier entity) {
        return carrierDAO.update(entity);
    }

    @Override
    public Carrier find(Object id) {
        return carrierDAO.find(id);
    }

    @Override
    public List<Carrier> find() {
        return carrierDAO.find();
    }

}
