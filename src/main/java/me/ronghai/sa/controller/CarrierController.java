/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import me.ronghai.sa.engine.service.CarrierService;
import me.ronghai.sa.model.Carrier;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author ronghai
 */
public class CarrierController extends BasicCURDController<Carrier> implements AbstractController {

    CarrierService carrierService;

    public CarrierService getCarrierService() {
        return carrierService;
    }

    public void setCarrierService(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void updateColumnBinding(JTableBinding tableBinding) {
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${website}"));
        columnBinding.setColumnName("Website");
        columnBinding.setColumnClass(String.class);
    }

    @Override
    public Object getSourceObject() {
        return this.carrierService.find();
    }
}
