/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 * @param <T>
 */
public class BasicTableModel <T> extends AbstractTableModel{
    BasicTableColumnModel<T> columnModel;

    public BasicTableColumnModel<T> getColumnModel() {
        return columnModel;
    }

    public void setColumnModel(BasicTableColumnModel<T> columnModel) {
        this.columnModel = columnModel;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    List<T> data;

    public BasicTableModel(BasicTableColumnModel<T> columnModel, List<T> data) {
        this.columnModel = columnModel;
        this.data = data;
    }

    public BasicTableModel(List<T> data) {
        this.data = data;
    }
    public BasicTableModel() {
        this.data = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

   

    @Override
    public int getColumnCount() {
        return this.columnModel.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       BasicTableColumn tableColumn =  (BasicTableColumn) columnModel.getAllTableColumns().get(columnIndex);
       return tableColumn.getProperty().getValue(data.get(rowIndex));
    }
    
    public void add(T data){
         this.data.add(data);
    }
    
    public void remove(int...indexs){
        Arrays.sort(indexs);
        for(int i = indexs.length - 1; i >= 0; i--){
            this.data.remove(indexs[i]);
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        BasicTableColumn tableColumn =  (BasicTableColumn) columnModel.getAllTableColumns().get(columnIndex);
        tableColumn.getProperty().setValue(data.get(rowIndex), aValue);
    }
    
    @Override
    public String getColumnName(int column) {
        BasicTableColumn tableColumn =  (BasicTableColumn)  columnModel.getColumn(column);
        return tableColumn.getColumnName();
        //  return super.getColumnName(column);
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        BasicTableColumn tableColumn =  (BasicTableColumn) columnModel.getAllTableColumns().get(columnIndex);
        return tableColumn.getColumnClass();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
