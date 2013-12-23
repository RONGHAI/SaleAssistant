/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import javax.swing.JTable;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.model.Client;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author ronghai
 */
public class ClientController extends BasicCURDController<Client> implements AbstractController {

    ClientService clientService;

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public DataWrapperBean add(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");

        return wrapper;
    }

    public DataWrapperBean edit(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        //table.add
        return wrapper;
    }

    public DataWrapperBean remove(DataWrapperBean param) {
        DataWrapperBean wrapper = new DataWrapperBean();
        JTable table = (JTable) param.get("table");
        return wrapper;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void updateColumnBinding(JTableBinding tableBinding) {
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${phone}"));
        columnBinding.setColumnName("Phone");
        columnBinding.setColumnClass(String.class);
    }

    @Override
    public Object getSourceObject() {
        return this.clientService.find();
    }

}
