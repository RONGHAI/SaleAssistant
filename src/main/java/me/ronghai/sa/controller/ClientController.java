/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.format.GenderFormat;
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

    public TableCellRenderer getTableCellRenderer(){
        return new DefaultTableCellRenderer(){
              @Override
              public Component getTableCellRendererComponent(JTable table, Object value,
                                            boolean isSelected, boolean hasFocus,
                                            int row, int column){
                  BasicTableColumn  col = (BasicTableColumn) table.getColumnModel().getColumn(column);
                  if(col.getFormat() != null && value != null){
                      value = col.getFormat().format(value);
                  }
                  return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
              }
        };
    }
    
     public TableCellEditor getTableCellEditor(){
         return null;
      /*  return new DefaultTableCellRenderer(){
              @Override
              public Component getTableCellRendererComponent(JTable table, Object value,
                                            boolean isSelected, boolean hasFocus,
                                            int row, int column){
                  BasicTableColumn  col = (BasicTableColumn) table.getColumnModel().getColumn(column);
                  if(col.getFormat() != null && value != null){
                      value = col.getFormat().format(value);
                  }
                  return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
              }
        };
         */
    }
    
    @Override
    public BasicTableModel<Client> getBasicTableModel(boolean retrieve) {
        if (basicTableModel == null) {
            basicTableModel = new BasicTableModel<>();
            basicTableModel.setData(this.clientService.find());
            BasicTableColumnModel<Client> columnModel = new BasicTableColumnModel<>();
            basicTableModel.setColumnModel(columnModel);
             
            
            List<BasicTableColumn> tableColumns = new ArrayList<>();
            TableCellRenderer render = this.getTableCellRenderer(); 
            TableCellEditor editor =  this.getTableCellEditor();
            
            BasicTableColumn  column = new BasicTableColumn( "${name}" , "name");
            tableColumns.add(column);

            column = new BasicTableColumn( "${phone}" , "phone");
            tableColumns.add(column);
            
            column = new BasicTableColumn( "${qq}" , "qq");
            column.setColumnClass(Long.class);
            tableColumns.add(column);
            
            column = new BasicTableColumn( "${wangwang}" , "wangwang");
            tableColumns.add(column);
            
            column = new BasicTableColumn( "${birthday}" , "birthday");
            column.setColumnClass(Date.class);
            column.setFormat(new SimpleDateFormat("yyyy-MM-dd"));
            tableColumns.add(column);
            
            column = new BasicTableColumn( "${gender}" , "gender");
            column.setFormat(new GenderFormat());
            tableColumns.add(column);
            
            
            column = new BasicTableColumn( "${note}" , "note");
            tableColumns.add(column);
            
            for(BasicTableColumn c : tableColumns){
                c.setCellRenderer(render);
                c.setCellEditor(editor);
            }
            columnModel.setAllTableColumns(tableColumns);

        }
        
        if(retrieve){
            basicTableModel.setData(this.clientService.find());
        } 
        return basicTableModel;
    }

     
    public DataWrapperBean add(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        Client c = new Client();
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
            if(c.getId() == null || c.isChanged() ){
                c.setUpdateTime(new Date());
            }
            c = c.getId() == null ? this.clientService.save(c) :   c.isChanged() ? this.clientService.update(c) : null;
        }
        this.clientService.remove(id2Client.keySet()); 
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
