/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author L5M
 */
public class DataWrapperBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> map;

    public DataWrapperBean() {
        this.map = new HashMap<>();
    }
    
    public DataWrapperBean(Object ... ob){
        this();
        for(int i = 0; i < ob.length; i+= 2){
            if(i + 1 >= ob.length){
                return;
            }
            this.put(ob[i]+"", ob[i+1]);
        }
    }
    
    public final void put(String string, Object object) {
        this.map.put(string, object);
    }

    public Object get(String key) {
        return this.map.get(key);
    }

}
