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
import me.ronghai.sa.view.delegate.JTableBindingDelegate;
import me.ronghai.sa.view.panel.BasicCURDPanel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author ronghai
 */
public class ClientController implements AbstractController, JTableBindingDelegate<Client> {

    // @Autowired
    BasicCURDPanel basicCURDPanel;

    public BasicCURDPanel getBasicCURDPanel() {
        return basicCURDPanel;
    }

    public void setBasicCURDPanel(BasicCURDPanel basicCURDPanel) {
        this.basicCURDPanel = basicCURDPanel;
    }

    ClientService clientService;

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public DataWrapperBean init(DataWrapperBean param) {
        this.basicCURDPanel.setTableBindingDelegate(this);
        DataWrapperBean wrapper = new DataWrapperBean();
        wrapper.put("panel", this.basicCURDPanel);
        return wrapper;
    }
    
    public DataWrapperBean add(DataWrapperBean param) {
         DataWrapperBean wrapper = new DataWrapperBean();
         JTable table = (JTable)param.get("table");
         
         return wrapper;
    }
    
    public DataWrapperBean edit(DataWrapperBean param) {
         DataWrapperBean wrapper = new DataWrapperBean();
         JTable table = (JTable)param.get("table");
         //table.add
         return wrapper;
    }
    
    public DataWrapperBean remove(DataWrapperBean param) {
         DataWrapperBean wrapper = new DataWrapperBean();
         JTable table = (JTable)param.get("table");
         return wrapper;
    }

    @Override
    public void refresh() {
    }

    private JTableBinding tableBinding;

    @Override
    public JTableBinding getJTableBinding(AutoBinding.UpdateStrategy strategy, JTable dataTable, String name) {
        if (tableBinding == null) {
            tableBinding = org.jdesktop.swingbinding.SwingBindings.
                    createJTableBinding(strategy, null, null, name);
            org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
            columnBinding.setColumnName("Name");
            columnBinding.setColumnClass(String.class);
            columnBinding = tableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${phone}"));
            columnBinding.setColumnName("Phone");
            columnBinding.setColumnClass(Long.class);
            //columnBinding.setConverter(null);
        }
        tableBinding.setTargetObject(dataTable);
        tableBinding.setSourceObject(clientService.find());
        return tableBinding;
    }

    
}
