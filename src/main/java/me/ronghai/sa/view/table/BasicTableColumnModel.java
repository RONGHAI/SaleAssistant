/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.view.table;

import java.util.Collections;
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
        this.allTableColumns = Collections.synchronizedList(allTableColumns);
        for(int i = 0 ; i < this.allTableColumns.size() ; i++){
            BasicTableColumn col =   this.allTableColumns.get(i);
            col.setModelIndex(i);
            col.setHeaderValue(col.getColumnName());
            col.setIdentifier(i+"_"+col.getColumnName());
            if(col.isShow()){
                this.addColumn( col );
            }
           
        }
    }
    
    public void updateTableColumns(){
        for(int i = 0; i <  this.allTableColumns.size(); i++){
            BasicTableColumn col  = this.allTableColumns.get(i);
            if(!col.isShow()){
                this.removeColumn(col);
            }else{
                try{
                    this.getColumnIndex(col.getIdentifier());
                }catch(IllegalArgumentException ex){
                    this.addColumn(col); 
                    if(i < this.getColumnCount()){
                        this.moveColumn(this.getColumnCount() - 1, i);
                    }
                }
            }
        }
    }
    
    
    public void updateTableColumns(BasicTableColumn col){
        if(!col.isShow()){
            this.removeColumn(col);
        }else{
            try{
                this.getColumnIndex(col.getIdentifier());
            }catch(IllegalArgumentException ex){
                this.addColumn(col); 
                int i = this.allTableColumns.indexOf(col);
                if(i < this.getColumnCount()){
                    this.moveColumn(this.getColumnCount() - 1, i);
                }
            }
        }
         
    }
    
    public BasicTableColumnModel(){
        super();
    }
 
}
