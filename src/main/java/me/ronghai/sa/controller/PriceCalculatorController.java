/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.controller;

import javax.swing.JPanel;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.engine.service.PropertyService;
import me.ronghai.sa.model.Property;
import me.ronghai.sa.view.panel.PriceCalculatorPanel;
import me.ronghai.sa.view.table.BasicTableModel;

/**
 *
 * @author ronghai
 */
public class PriceCalculatorController  implements AbstractController {
    
    PropertyService propertyService;
    
    private double EX_RATE = 6.1, SERVER_FEE_RATE = 0.1;
    
    public PropertyService getPropertyService() {
        return propertyService;
    }

    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    public DataWrapperBean init(DataWrapperBean param){
        EX_RATE = Double.parseDouble(this.propertyService.findPropertyValue("EX_RATE"));
        SERVER_FEE_RATE = Double.parseDouble(this.propertyService.findPropertyValue("SERVER_FEE_RATE"));
        
        DataWrapperBean wrapper = new DataWrapperBean(); 
        JPanel panel = new PriceCalculatorPanel();
        wrapper.put("panel", panel);
        return wrapper;
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
