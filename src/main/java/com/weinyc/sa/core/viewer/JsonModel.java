/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.core.viewer;
import net.sf.json.JSONObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class JsonModel implements Serializable{
    
    private final Map<String, Object> data = new HashMap<>();

    public Object put(String key, Object value) {
        return data.put(key, value);
    }

    public JsonModel() {
    }
    
    
    public String toJson(){
        return JSONObject.fromObject(data).toString();
    }
}
