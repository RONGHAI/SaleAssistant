/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.controller;

import javax.swing.JTable;
import me.ronghai.sa.bean.DataWrapperBean;
import me.ronghai.sa.view.delegate.JTableBindingDelegate;
import me.ronghai.sa.view.panel.BasicCURDPanel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author ronghai
 */
public abstract class BasicCURDController<E> implements JTableBindingDelegate {

    BasicCURDPanel basicCURDPanel;

    public BasicCURDPanel getBasicCURDPanel() {
        return basicCURDPanel;
    }

    public void setBasicCURDPanel(BasicCURDPanel basicCURDPanel) {
        this.basicCURDPanel = basicCURDPanel;
    }

    public DataWrapperBean init(DataWrapperBean param) {
        this.basicCURDPanel.setTableBindingDelegate(this);
        DataWrapperBean wrapper = new DataWrapperBean();
        wrapper.put("panel", this.basicCURDPanel);
        return wrapper;
    }

    private JTableBinding tableBinding;

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
        return tableBinding;
    }

    public abstract Object getSourceObject();

}
