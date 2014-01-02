/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.model.Client;
import me.ronghai.sa.view.table.BasicTableColumn;
import me.ronghai.sa.view.table.BasicTableColumnModel;
import me.ronghai.sa.view.table.BasicTableModel;

/**
 *
 * @author ronghai
 */
public class ClientController extends BasicCURDController<Client> implements AbstractController {

    ClientService clientService;
    BasicTableModel<Client> basicTableModel;

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public BasicTableModel<Client> getBasicTableModel(boolean retrieve) {
        if (basicTableModel == null) {
            basicTableModel = new BasicTableModel<>();
            basicTableModel.setData(this.clientService.find());
            BasicTableColumnModel<Client> columnModel = new BasicTableColumnModel<>();
            basicTableModel.setColumnModel(columnModel);
            
            
            List<BasicTableColumn> tableColumns = new ArrayList<>();
            
            BasicTableColumn  column = new BasicTableColumn();
            column.setColumnName("NAME");
            column.setProperty("${name}");
            tableColumns.add(column);

            column = new BasicTableColumn();
            column.setColumnName("PHONE");
            column.setProperty("${phone}");
            tableColumns.add(column);
            
            columnModel.setAllTableColumns(tableColumns);

        }
        
        if(retrieve){
            basicTableModel.setData(this.clientService.find());
        }
        return basicTableModel;
    }

     
    public DataWrapperBean add(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        this.basicTableModel.add(new Client());        
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
        System.out.println(Arrays.toString(table.getSelectedRows()));

         
        return wrapper;
        
    }
    
    public DataWrapperBean save(DataWrapperBean param) {
         
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        BasicTableModel<Client> model = (BasicTableModel<Client>) table.getModel();
        List<Client> clients =  model.getData();

        List<Client> oldClients = this.clientService.find();
        Map<Long, Client> id2Client = new HashMap<>();
        for(Client c : oldClients ){
            id2Client.put(c.getId(), c);
        }
        for(Client c : clients){
            id2Client.remove(c.getId()); 
           // c = c.getId() == null ? this.clientService.save(c) : this.clientService.update(c);
        } 
        System.out.println("removed"+id2Client.keySet()); 
        this.clientService.remove(oldClients.get(0));
        
        
        model.setData(this.clientService.find());
        return wrapper;
        
    }

    @Override
    public void refresh() {
    }

    
    /*
    @Override
    public void updateColumnBinding(JTableBinding tableBinding) {

        ELProperty p = org.jdesktop.beansbinding.ELProperty.create("${name}");

        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${phone}"));
        columnBinding.setColumnName("Phone");
        columnBinding.setColumnClass(Long.class);
    }

    @Override
    public Object getSourceObject() {
        return this.clientService.find();
    }
    */

}
