package com.weinyc.sa.core.reflect.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.weinyc.sa.core.annotation.RequestParse;
import com.weinyc.sa.common.util.SortableBeanParse;

public class FieldSortableBeanParse extends  SortableBeanParse<Field> implements Serializable, Cloneable{ 
    /**
     * 
     */
    private static final long serialVersionUID = -1830632658092014873L;

    @Override
    public List<String> parseBeanToList (Field field) {
        List<String> list = new ArrayList<String>(1);
        RequestParse annotation = (RequestParse) field.getAnnotation(RequestParse.class );
        if(field.isAnnotationPresent(RequestParse.class) ){
            list.add(annotation.order()+"");
            list.add(annotation.parseType().ordinal()+"");
        }else{
            list.add("-1");
            list.add("-1");
        }
        return list;
    } 
    private static FieldSortableBeanParse Singleton;
    public static FieldSortableBeanParse instance() {
        if(Singleton == null){
            Singleton = new FieldSortableBeanParse();
        }
        return Singleton;
    } 
    private FieldSortableBeanParse(){ 
    }
    public Object clone(){
        return this;
    }
}
