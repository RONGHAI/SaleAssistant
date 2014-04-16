
package com.ecbeta.common.core.viewer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.json.JSONObject;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class MapJSONBean implements Serializable{
    private static final Logger logger =   Logger.getLogger(MapJSONBean.class.getName()) ;

    static Pattern pattern = Pattern.compile("\\[(.*?)\\]");
    private final HashMap<String, Object> json = new HashMap<>();

    public void add(String key, String value) {
        if (value == null || value.equals("")) {
            return;
        }
        Matcher matcher = pattern.matcher(key);
        if (!matcher.find()) {
            put(json, key, value);
        } else {
            matcher.reset();
            String firstKey = key.substring(0, key.indexOf('['));
            List<String> keys = new ArrayList<>();
            while (matcher.find()) {
                String _key = matcher.group(1);
                keys.add(_key);
            }
            HashMap<String, Object> object = (HashMap<String, Object>) json.get(firstKey);
            if (object == null) {
                object = new HashMap<>();
                json.put(firstKey, object);
            }

            for (int i = 0; i < keys.size(); i++) {
                String p = keys.get(i);
                HashMap<String, Object> current = null;
                try {
                    current = (HashMap<String, Object>) object.get(p);
                }catch (Exception e) {
                    logger.log(Level.INFO, e.getLocalizedMessage(), e);
                }
                if (current == null) {
                    current = new HashMap<>();
                    object.put(p, current);
                }
                object = current;
            }
            object.put(object.size() + "", value);
        }
    }

    @SuppressWarnings("unchecked")
    private static void put(HashMap<String, Object> json, String key, String value) {
        Object values = json.get(key);
        if (values == null) {
            json.put(key, value);
        } else if (values instanceof String) {
            ArrayList<String> ars = new ArrayList<>();
            ars.add(values.toString());
            ars.add(value);
            json.put(key, ars);
        } else if (values instanceof List) {
            ((List<String>) json.get(key)).add(value);
        }
    }

    public Object adjust(HashMap<String, Object> json) {
        List<String> keys = new ArrayList<>(json.keySet());
        try {
            List<Integer> index = new ArrayList<>();
            for (String key : keys) {
                index.add(new Integer(key));
            }
            Collections.sort(index);
            if (index.size() == (index.get(index.size() - 1) - index.get(0) + 1)) {
                List<Object> list = new ArrayList<>();
                for (Integer k : index) {

                    Object o = json.get(k.toString());
                    if (o instanceof Map) {
                        HashMap<String, Object> con = (HashMap<String, Object>) o;
                        list.add(adjust(con));
                    } else {
                        list.add(o);
                    }
                }
                return list;
            }
        }
        catch (NumberFormatException e) {
             logger.log(Level.OFF, e.getLocalizedMessage(), e);
        }

        if (keys.size() == 1 && keys.get(0).equals("")) {
            Object o = json.get(keys.get(0).toString());
            if (o instanceof Map) {
                HashMap<String, Object> con = (HashMap<String, Object>) o;
                return adjust(con);
            } else {
                return o;
            }
        }
        for (String k : keys) {
            Object o = json.get(k.toString());
            if (o instanceof Map) {
                HashMap<String, Object> con = (HashMap<String, Object>) o;
                json.put(k, adjust(con));
            } else {
            }

        }
        return json;
    }
      
    @Override
    public String toString(){
        return this.json.toString();
    }


    public JSONObject toJson(){ 
       return JSONObject.fromObject(adjust(this.json));
    }
}
