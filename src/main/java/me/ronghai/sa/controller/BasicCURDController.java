/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import java.util.Arrays;
import javax.swing.JTable;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.view.panel.BasicCURDPanel;
import me.ronghai.sa.view.table.BasicTableModel;

/**
 *
 * @author ronghai
 */
public abstract class BasicCURDController<E> implements  AbstractController {

    BasicCURDPanel basicCURDPanel;

    public BasicCURDPanel getBasicCURDPanel() {
        return basicCURDPanel;
    }

    public void setBasicCURDPanel(BasicCURDPanel basicCURDPanel) {
        this.basicCURDPanel = basicCURDPanel;
    }
    
    public DataWrapperBean init(DataWrapperBean param) {
        this.basicCURDPanel.setController(this);
        this.basicCURDPanel.setModel2Table(this.getBasicTableModel(false));
        DataWrapperBean wrapper = new DataWrapperBean();
        wrapper.put("panel", this.basicCURDPanel);
        return wrapper;
    }    
    /*
    protected JTableBinding tableBinding;

    public abstract void updateColumnBinding(JTableBinding tableBinding);

    @Override
    public JTableBinding getJTableBinding(AutoBinding.UpdateStrategy strategy, JTable dataTable, String name) {
        if (tableBinding == null) {
            tableBinding = org.jdesktop.swingbinding.SwingBindings.
                    createJTableBinding(strategy, null, null, name);
            this.updateColumnBinding(tableBinding);
        }
        if (tableBinding.isBound()) {
            tableBinding.unbind();
        }
        tableBinding.setTargetObject(dataTable);
        tableBinding.setSourceObject(this.getSourceObject());
        //tableBinding.addBindingListener(null);
       
        return tableBinding;
    }
    
     

    public abstract Object getSourceObject();
    */
    
    public BasicTableModel<E> getBasicTableModel(boolean retrieve){
        throw new UnsupportedOperationException();
    }
}
