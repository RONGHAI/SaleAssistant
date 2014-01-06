/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.engine.service.PropertyService;
import me.ronghai.sa.model.Property;
import me.ronghai.sa.view.table.BasicTableColumn;
import me.ronghai.sa.view.table.BasicTableColumnModel;
import me.ronghai.sa.view.table.BasicTableModel;

/**
 *
 * @author ronghai
 */
public class PropertyController extends BasicCURDController<Property> implements AbstractController {

    PropertyService propertyService;
    BasicTableModel<Property> basicTableModel;
    
    public PropertyService getPropertyService() {
        return propertyService;
    }

    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public void refresh() {
    }

    
    @Override
    public BasicTableModel<Property> getBasicTableModel(boolean retrieve) {
        if (true || basicTableModel == null) {
            basicTableModel = new BasicTableModel<>();
            basicTableModel.setData(this.propertyService.find());
            BasicTableColumnModel<Property> columnModel = new BasicTableColumnModel<>();
            basicTableModel.setColumnModel(columnModel);
            
            
            List<BasicTableColumn> tableColumns = new ArrayList<>();
            
            BasicTableColumn column = new BasicTableColumn("${code}", "code");
            tableColumns.add(column);

            column = new BasicTableColumn("${value}", "value");
            tableColumns.add(column);
            
            column = new BasicTableColumn("${note}", "note");
            tableColumns.add(column);
                        
            columnModel.setAllTableColumns(tableColumns);

        }
        if(retrieve){
            basicTableModel.setData(this.propertyService.find());
        }
        return basicTableModel;
    }
    
    
    
    public DataWrapperBean add(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        Property c = new Property();
        c.setChanged(true);
        c.setAddTime(new Date());
        this.basicTableModel.add(c);  
        table.editCellAt(this.basicTableModel.getRowCount() - 1, 0 );
        return wrapper;
    }
 
    public DataWrapperBean remove(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        System.out.println(Arrays.toString(table.getSelectedRows()));
        this.basicTableModel.remove(table.getSelectedRows());
        return wrapper;
    }
    
    public DataWrapperBean edit(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        return wrapper;
        
    }
    
    public DataWrapperBean save(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        BasicTableModel<Property> model = (BasicTableModel<Property>) table.getModel();
        List<Property> clients =  model.getData();
        List<Property> oldClients = this.propertyService.find();
        Map<Long, Property> id2Client = new HashMap<>();
        for(Property c : oldClients ){
            id2Client.put(c.getId(), c);
        }
        for(Property c : clients){
            id2Client.remove(c.getId()); 
            System.out.println(c.isChanged());
            if(c.getId() == null || c.isChanged() ){
                c.setUpdateTime(new Date());
            }
            c = c.getId() == null ? this.propertyService.save(c) :   c.isChanged() ? this.propertyService.update(c) : null;
        }
        this.propertyService.remove(id2Client.keySet()); 
        model.setData(this.propertyService.find());
        return wrapper;
    }
    
}
