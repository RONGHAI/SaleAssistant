/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import me.ronghai.sa.model.AbstractModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.viewer.bean.MapJSONBean;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class JSONUtils {
    private static final Logger logger = Logger.getLogger(JSONUtils.class.getName());

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> toCollection(Object json, String key, Class<T> clazz) {
        if (json instanceof JSONArray) {
            return JSONArray.toCollection((JSONArray) json, clazz);
        } else if (json instanceof JSONObject) {
            JSONArray array = (JSONArray) ((JSONObject) json).get(key);
            return JSONArray.toCollection(array, clazz);
        }
        return null;
    }

    public static JSONObject toJSON(HttpServletRequest request) {
        MapJSONBean json = new MapJSONBean();
        for (Object s : request.getParameterMap().keySet()) {
            for (String value : request.getParameterValues(s.toString())) {
                json.add(s.toString(), value);
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            String data = "";
            while ((line = br.readLine()) != null) {
                data += line;
            }
            data = URLDecoder.decode(data, "UTF-8");
            try {
                JSONObject oo = JSONObject.fromObject(data);
                return oo;
            }
            catch (Exception ex) {
                Map<String, List<String>> params = parseParameters(data);
                for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                    for (String value : entry.getValue()) {
                        json.add(entry.getKey(), value);
                    }
                }
            }

        }
        catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        JSONObject job = json.toJson();
        logger.info("~~~~~~~~~AutoCreating~~~~~~~~~");
        logger.info(json == null ?  "" : job.toString(2) );
        logger.info("~~~~~~~~~End~~~~~~~~~");
        return job;
    }

    private static Map<String, List<String>> parseParameters(String url) {
        Map<String, List<String>> params = new HashMap<>();
        String query = url;
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            String key = pair[0];
            String value = "";
            if (pair.length > 1) {
                value = pair[1];
            }
            List<String> values = params.get(key);
            if (values == null) {
                values = new ArrayList<>();
                params.put(key, values);
            }
            values.add(value);
        }
        return params;
    }
     public static void expectOne(JSONObject json, String key){
        if(json.has(key)){
            Object o = json.get(key);
            if(o instanceof JSONArray){
                json.put(key, ((JSONArray)o ).get(0));
            }
        }
          
    }
     public static void expectMore(JSONObject json, String key){
        if(json.has(key)){
            Object o = json.get(key);
            if(o instanceof JSONArray){
               
            }else{
                List<Object> list = new ArrayList<>(1);
                list.add(o);
                json.put(key, list);
            }
        }
          
    }
     
    public static String toString(JSONArray o){
        if(o == null ){
            return null;
        }
        return reformatJSON(o.toString(2));
    }
    public static String toString(JSONObject o){
        if(o == null ){
            return null;
        }
        return reformatJSON(o.toString(2));
    }
    
    public static String toString(JSONArray o, int start){
        if(o == null ){
            return null;
        }
        return reformatJSON(o.toString(start, 2));
    }
    public static String toString(JSONObject o, int start){
        if(o == null ){
            return null;
        }
        return reformatJSON(o.toString(start, 2));
    }
    
    
    private static String reformatJSON(String str){
        str = str.replaceAll("\"(\\w+)\":", "$1:"); // remove quotes from keys
        str = str.replaceAll("\"(sale_assistant[.])([\\w\\(\\)]+)\"", "$1$2"); //remove quotes from javascript object
        str = str.replaceAll("\"(\\d+)\"", "$1");
        return str;
    }
    
    public final static JSONArray getChanges(JSONObject json){
        for(String s : Constants.W2UI_CHANGES){
            Object ar = json.get(s);
            if(ar != null){
                return (JSONArray)ar;
            }
        }
        return null;
    }
    
    public static JSONArray toJSONArray(Collection<? extends AbstractModel> list){
        JSONArray jsonArray = new JSONArray();
        for(AbstractModel o : list){
            jsonArray.add(o.toJson());
        }
        return jsonArray;
    }
}
