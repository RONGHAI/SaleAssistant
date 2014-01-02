/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import java.util.ArrayList;
import java.util.List;
import me.ronghai.sa.engine.service.CarrierService;
import me.ronghai.sa.model.Carrier;
import me.ronghai.sa.view.table.BasicTableColumn;
import me.ronghai.sa.view.table.BasicTableColumnModel;
import me.ronghai.sa.view.table.BasicTableModel;

/**
 *
 * @author ronghai
 */
public class CarrierController extends BasicCURDController<Carrier> implements AbstractController {

    CarrierService carrierService;
    BasicTableModel<Carrier> basicTableModel;
    
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
    public BasicTableModel<Carrier> getBasicTableModel(boolean retrieve) {
        if (basicTableModel == null) {
            basicTableModel = new BasicTableModel<>();
            basicTableModel.setData(this.carrierService.find());
            BasicTableColumnModel<Carrier> columnModel = new BasicTableColumnModel<>();
            basicTableModel.setColumnModel(columnModel);
            
            
            List<BasicTableColumn> tableColumns = new ArrayList<>();
            
            BasicTableColumn column = new BasicTableColumn();
            column.setColumnName("NAME");
            column.setProperty(org.jdesktop.beansbinding.ELProperty.create("${name}"));
            tableColumns.add(column);

            column = new BasicTableColumn();
            column.setColumnName("WEBSITE");
            column.setProperty(org.jdesktop.beansbinding.ELProperty.create("${website}"));
            tableColumns.add(column);
            
            columnModel.setAllTableColumns(tableColumns);

        }
        if(retrieve){
            basicTableModel.setData(this.carrierService.find());
        }
        return basicTableModel;
    }
}
