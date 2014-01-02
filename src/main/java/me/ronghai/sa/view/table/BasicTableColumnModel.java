/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.table;

import java.util.List;
import javax.swing.table.DefaultTableColumnModel;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 * @param <T>
 */
public class BasicTableColumnModel<T> extends DefaultTableColumnModel {
 
    public List<BasicTableColumn> getAllTableColumns() {
        return allTableColumns;
    }
    private List<BasicTableColumn> allTableColumns;
 
    public void setAllTableColumns(List<BasicTableColumn> allTableColumns) {
        this.allTableColumns = allTableColumns;
        for(int i = 0 ; i < this.allTableColumns.size() ; i++){
            BasicTableColumn col =   this.allTableColumns.get(i);
            col.setModelIndex(i);
            col.setHeaderValue(col.getColumnName());
            col.setIdentifier(i+"_"+col.getColumnName());
            this.addColumn( col );
        }        
        
    }
    
    public BasicTableColumnModel(){
        super();
    }
 
}
