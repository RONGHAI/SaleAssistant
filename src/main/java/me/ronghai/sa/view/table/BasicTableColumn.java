/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.table;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class BasicTableColumn extends TableColumn {

    private ELProperty property;
    private boolean show;
    private String columnName;
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public ELProperty getProperty() {
        return property;
    }
    
    public void setProperty(ELProperty property) {
        this.property = property;
    }
    
    public void setProperty(String property) {
        this.setProperty(org.jdesktop.beansbinding.ELProperty.create(property));
    }
    
    public BasicTableColumn() {
    }
    
    public BasicTableColumn(int modelIndex) {
        super(modelIndex);
    }
    
    public BasicTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
    }
    
    public BasicTableColumn(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
    }
}