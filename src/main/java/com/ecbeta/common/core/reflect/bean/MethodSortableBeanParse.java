package com.ecbeta.common.core.reflect.bean;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ecbeta.common.core.annotation.RequestParse;
import com.ecbeta.common.util.SortableBeanParse;

public  class MethodSortableBeanParse extends  SortableBeanParse<Method> implements Serializable, Cloneable{ 
    /**
     * 
     */
    private static final long serialVersionUID = -1830632658092014872L;

    @Override
    public List<String> parseBeanToList (Method method) {
        List<String> list = new ArrayList<String>(1);
        RequestParse annotation = (RequestParse) method.getAnnotation(RequestParse.class );
        if(method.isAnnotationPresent(RequestParse.class) ){
            list.add(annotation.order()+"");
            list.add(annotation.parseType().ordinal()+"");
        }else{
            list.add("-1");
            list.add("-1");
        }
        return list;
    } 

    private static MethodSortableBeanParse Singleton;
    public static MethodSortableBeanParse instance() {
        if(Singleton == null){
            Singleton = new MethodSortableBeanParse();
        }
        return Singleton;
    } 
    private MethodSortableBeanParse(){ 
    }
    public Object clone(){
        return this;
    }
}