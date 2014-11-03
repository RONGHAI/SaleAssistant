/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.common.core.viewer.bean;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class W2UIColumnBean implements Serializable{

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
   
     
    private String field = "";
    private String size = null;
    private String caption = "";
    private boolean sortable = true;
    private boolean searchable = false;
    private boolean hidden = false;
    private boolean resizable = false;
    private String attr = "";
    private String style="";
    private String render = null;
    private String title = null;
    private String min = "15";
    private String max = null;
    private String type = null;
    private Object editable;

    public Object getEditable() {
        return editable;
    }

    public void setEditable(Object editable) {
        this.editable = editable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }
    private Object items = null;

    public W2UIColumnBean() {
    }
    
   /* 
	min            : 15,     // minimum width of column in px
	gridMinWidth   : null,   // minimum width of the grid when column is visible
	sizeCorrected  : null,   // read only, corrected size (see explanation below)
	sizeCalculated : null,   // read only, size in px (see explanation below)
	render         : null,   // render function
	editable       : {}      // editable object if column fields are editable
*/
    public Object toJson(){
        return this;
    }
    

    public W2UIColumnBean(String field, String caption,String size, boolean sortable ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
    }
    
    public W2UIColumnBean(String field, String caption,String size, String render , boolean sortable ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.render = render;
    }
   
    public W2UIColumnBean(String field, String caption,String size, boolean sortable , String type ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.type = type;
    }
    
    public W2UIColumnBean(String field, String caption,String size, String render , boolean sortable  , String type) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.render = render;
        this.type = type;
    }
    
    
    
    public W2UIColumnBean(String field, String caption,String size, boolean sortable, Object editable ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.editable = editable;
    }
    
    public W2UIColumnBean(String field, String caption,String size, String render , boolean sortable, Object editable ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.render = render;
        this.editable = editable;
    }
  
    public W2UIColumnBean(String field, String caption,String size, boolean sortable , String type, Object editable ) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.type = type;
        this.editable = editable;
    }
    
    public W2UIColumnBean(String field, String caption,String size, String render , boolean sortable  , String type, Object editable) {
        this.field = field;
        this.caption = caption;
        this.size = size;
        this.sortable = sortable;
        this.render = render;
        this.type = type;
        this.editable = editable;
    }
}
