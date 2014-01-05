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
        if (true || basicTableModel == null) {
            basicTableModel = new BasicTableModel<>();
            basicTableModel.setData(this.carrierService.find());
            BasicTableColumnModel<Carrier> columnModel = new BasicTableColumnModel<>();
            basicTableModel.setColumnModel(columnModel);
            
            
            List<BasicTableColumn> tableColumns = new ArrayList<>();
            
            BasicTableColumn column = new BasicTableColumn("${name}", "name");
            tableColumns.add(column);

            column = new BasicTableColumn("${website}", "website");
            tableColumns.add(column);
            
            column = new BasicTableColumn("${trackURL}", "Track URL");
            tableColumns.add(column);
            
            column = new BasicTableColumn("${trackMethod}", "trackMethod");
            tableColumns.add(column);
            
            column = new BasicTableColumn("${note}", "note");
            tableColumns.add(column);
                        
            columnModel.setAllTableColumns(tableColumns);

        }
        if(retrieve){
            basicTableModel.setData(this.carrierService.find());
        }
        return basicTableModel;
    }
    
    
    
    public DataWrapperBean add(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        Carrier c = new Carrier();
        c.setChanged(true);
        c.setAddTime(new Date());
        this.basicTableModel.add(c);  
        table.editCellAt(1, 0 );
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
        BasicTableModel<Carrier> model = (BasicTableModel<Carrier>) table.getModel();
        List<Carrier> clients =  model.getData();
        List<Carrier> oldClients = this.carrierService.find();
        Map<Long, Carrier> id2Client = new HashMap<>();
        for(Carrier c : oldClients ){
            id2Client.put(c.getId(), c);
        }
        for(Carrier c : clients){
            id2Client.remove(c.getId()); 
            System.out.println(c.isChanged());
            if(c.getId() == null || c.isChanged() ){
                c.setUpdateTime(new Date());
                System.out.println(c.getName());
            }
            c = c.getId() == null ? this.carrierService.save(c) :   c.isChanged() ? this.carrierService.update(c) : null;
        }
        this.carrierService.remove(id2Client.keySet()); 
        model.setData(this.carrierService.find());
        return wrapper;
    }
}
