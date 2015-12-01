/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecbeta.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import me.ronghai.sa.model.AbstractModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.reflect.ReflectUtils;
import com.ecbeta.common.core.viewer.bean.MapJSONBean;
import com.ecbeta.common.util.annotation.JsonIgnore;
import com.ecbeta.common.util.annotation.Jsonable;

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
            
            List<String> datalist = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                datalist.add(line);
            }
            if(!datalist.isEmpty()){
                String separate = datalist.get(0);
                boolean multipart = datalist.lastIndexOf(datalist.get(0)) > 0;
                if(multipart){
                    boolean removeNext = false;
                    for (Iterator<String> iterator = datalist.iterator(); iterator.hasNext();) {
                        line  = (String) iterator.next();
                        if(line.equals(separate)){
                            iterator.remove();
                            removeNext = false;
                            continue;
                        }
                        if(line.contains("Content-Disposition: attachment")){
                            iterator.remove();
                            removeNext = true;
                            continue;
                        }
                        if(line.contains("Content-Disposition:") || removeNext){
                            iterator.remove();
                            continue;
                        }
                    }
                }
            }
            String data = StringUtils.join(datalist, "");
            if(StringUtils.isNotEmpty(data)){
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
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, null, ex);
        }
        JSONObject job = json.toJson();
        logger.info("~~~~~~~~~AutoCreating~~~~~~~~~");
        logger.info(json == null ?  "" : job.toString(2) );
        logger.info("~~~~~~~~~End~~~~~~~~~");
        return job;
    }

    private static Map<String, List<String>> parseParameters(String url) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
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
                values = new ArrayList<String>();
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
     public static void expectOne(JSONObject json, String... keys){
         if(keys == null ) return;
         for(String key : keys){
             expectOne(json, key);
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
     
     public static void expectMore(JSONObject json, String ok, String key){
         if(json.has(key)){
             Object o = json.get(key);
             if(o instanceof JSONArray){
                JSONArray array = (JSONArray) o;
                List<Object> list = new ArrayList<>(1);
                for(int i = 0; i < array.size(); i++){
                    Object obj = array.get(i);
                    if(obj instanceof String){
                        JSONObject jo = new JSONObject();
                        jo.put(ok, obj);
                        list.add(jo);
                    }else if(obj instanceof JSONObject){
                        list.add(obj);
                    }
                }
                json.put(key, list);
             }else{
                 List<Object> list = new ArrayList<>(1);
                 if(o instanceof String){
                     JSONObject jo = new JSONObject();
                     jo.put(ok, o);
                     list.add(jo);
                 }else{
                     list.add(o);
                 }
                 json.put(key, list);
             }
         }
     }
     
     public static void expectMore(JSONObject json, String... keys){
         if(keys == null ) return;
         for(String key : keys){
             expectMore(json, key);
         }
     }
     
        
    @SuppressWarnings("unchecked")
    public final static <T> T toBean(JSONObject json, Class<T> clazz){
        return (T) JSONObject.toBean(expect(json,clazz),clazz);
    }
    
    public static JSONObject expect(JSONObject json, Class<?> clazz){
        Map<String, Field>  fields = new HashMap<String, Field>();
        fields = ReflectUtils.getDeclaredFields(fields, clazz, true); 
        for(Object key : json.keySet()){
            Field field = fields.get(key+"");
            if(field == null) continue;
            if(field.getType().isArray() || Collection.class.isAssignableFrom(field.getType()) ){
                expectMore(json, key+"");
            }else{
                expectOne(json, key+"");
            }
        }
        return json;
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
        return reformatJSON(o.toString(2, 2));
    }
    public static String toString(JSONObject o, int start){
        if(o == null ){
            return null;
        }
        return reformatJSON(o.toString(2, 2));
    }
    
    
    
    public final static JSONObject toJSON(Object o){
        List<Field> fields  = new ArrayList<Field>();
        fields = ReflectUtils.getDeclaredFields(fields, o.getClass(), true);
        JSONObject json = new JSONObject();
        for(Field field : fields){
            try {
                if(!field.isAnnotationPresent(JsonIgnore.class)) {
                    json.put(field.getName(),  ReflectUtils.value(o, field, null));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        List<Method> methods = Arrays.asList(o.getClass().getMethods());
        for(Method m : methods){
            if(m.isAnnotationPresent(Jsonable.class)){
                try {
                    String name = m.getAnnotation(Jsonable.class).value();
                    if(StringUtils.isEmpty(name)){
                        name = m.getName();
                        if(name.startsWith("is")){
                            name = name.substring(2);
                        }else if(name.startsWith("get")){
                            name = name.substring(3);
                        }
                        name = name.substring(0, 1).toUpperCase()+name.substring(1);
                    }
                   
                    json.put(name,   m.invoke(o));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return json;
    }
    
    
    
    private static String reformatJSON(String str){
        str = str.replaceAll("\"(\\w+)\":", "$1:"); // remove quotes from keys
        str = str.replaceAll("\"("+Constants.SAJS_PREFIX+"[.])([\\w\\(\\)]+)\"", "$1$2"); //remove quotes from javascript object
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
    
    
    public static List<JSONObject> toJSONObjects(Collection<? extends AbstractModel> list){
        List<JSONObject>  jsonArray = new  ArrayList<>();
        for(AbstractModel o : list){
            Object obj = o.toJson();
            if(obj instanceof JSONObject){
                jsonArray.add((JSONObject)obj);
            }else{
                jsonArray.add(JSONObject.fromObject(obj));
            }
        }
        logger.log(Level.INFO, "all Json Object Arrays \n{0}", jsonArray);
        return jsonArray;
    }
    
    public static JSONArray toJSONArray(List<? extends AbstractModel> list, JSONObject json){
        List<JSONObject> jsonArray  = toJSONObjects(list);
        jsonArray = limit(jsonArray, json);
        jsonArray = search(jsonArray, json);
        jsonArray = sort(jsonArray, json);
        return JSONArray.fromObject(jsonArray);
    }
    
    public static JSONArray toJSONArrayWithoutSort(List<? extends AbstractModel> list, JSONObject json){
        List<JSONObject> jsonArray  = toJSONObjects(list);
        jsonArray = limit(jsonArray, json);
        jsonArray = search(jsonArray, json);
        return JSONArray.fromObject(jsonArray);
    }
    
    public static List<JSONObject> search(List<JSONObject> jsonArray, JSONObject json){
        JSONArray searchs  = json != null && json.has("search") ? json.getJSONArray("search") : null;
        if(searchs == null){
            return jsonArray;
        }
        
        String searchLogic = json.getString("searchLogic");
        boolean or = "OR".equals(searchLogic);
        for (Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext();) {
            JSONObject bean = iterator.next();
            boolean in = or ? false : true;
            for(Object o : searchs.toArray()){
                if(isIn(bean, (JSONObject)o)){
                   if(or){
                       in = true;
                       break;
                   }
                }else{
                    if(!or){
                        in = false;
                        break;
                    }
                }
            }
            if(!in){
                iterator.remove();
                continue;
            }
        }
        return jsonArray;
    }
    
    public static boolean isIn(JSONObject obj, JSONObject search){
        if(!search.has("value")){
            return true;
        }
        try{
            expectOne(search, "field", "type", "operator");
            String operator = search.getString("operator");
            String field = search.getString("field");
            String type = search.getString("type");
            expectMore(search, "value");
            JSONArray value = search.getJSONArray("value");
            String v0 = value.getString(0);
            String v1 = value.size() > 1 ? value.getString(1):null;
            String sf = obj.getString(field);
            
            if("text".equals(type)){
                if("contains".equals(operator)){
                    if(obj.has(field) && sf.contains(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("begins".equals(operator)){
                    if(obj.has(field) && sf.startsWith(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("ends".equals(operator)){
                    if(obj.has(field) && sf.endsWith(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("is".equals(operator)){
                    if(obj.has(field) && sf.equals(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }
            }else if("int".equals(type) || "float".equals(type)){
                if("is".equals(operator)){
                    if(obj.has(field) && obj.getDouble(field) == Double.parseDouble(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("between".equals(operator)){
                    if(obj.has(field) && obj.getDouble(field) >= Double.parseDouble(v0) && obj.getDouble(field) <= Double.parseDouble(v1)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("in".equals(operator)){
                    if(obj.has(field) && ArrayUtils.indexOf(value.toArray(), sf) >= 0){
                        return true;
                    }else{
                        return false;
                    }
                }else if("not in".equals(operator)){
                    if(obj.has(field) && ArrayUtils.indexOf(value.toArray(), sf) < 0){
                        return true;
                    }else{
                        return false;
                    }
                }
            }else if("date".equals(type)){
                if("is".equals(operator)){
                    if(obj.has(field) && obj.getLong(field) == Long.parseLong(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }else if("between".equals(operator)){
                    if(obj.has(field) && obj.getLong(field) >=  Long.parseLong(v0) && obj.getLong(field) <=  Long.parseLong(v1)){
                        return true;
                    }else{
                        return false;
                    }
                }
            }else{
                if("is".equals(operator)){
                    if(obj.has(field) && obj.getLong(field) == Long.parseLong(v0)){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }catch(Exception e){
            logger.log(Level.WARNING, "{0}", e);
        }
      
        return true;
    }
    
    
    
    public static List<JSONObject> sort(List<JSONObject> jsonArray, JSONObject json){
        final JSONArray searchs  = json != null && json.has("sort") ? json.getJSONArray("sort") : null;
        if(searchs == null){
            return jsonArray;
        }
        logger.log(Level.INFO, "{0}", searchs);
        try{
            int[] sortKeys = new int[searchs.size()];
            for(int i = 0; i < sortKeys.length; i++){
                sortKeys[i] = i;
            }
            final boolean sortAscending[]  = new boolean[searchs.size()];
            for(int i = 0; i < sortAscending.length; i++){
                expectOne(searchs.getJSONObject(i), "direction", "field");
                sortAscending[i] = searchs.getJSONObject(i).get("direction").toString().equals("asc");
            }
            SortBeanListUtil.sort2(jsonArray, sortKeys, sortAscending, false, new SortableBeanParse<JSONObject>() {
                @Override
                public List<String> parseBeanToList(JSONObject o) {
                    List<String>  list = new  ArrayList<String>();
                    for(int i = 0; i < sortAscending.length; i++){
                        list.add(o.getString(searchs.getJSONObject(i).getString("field")));
                    }
                    return list;
                }
                @Override
                public List<Comparable<?>> parse(JSONObject o) {
                    List<Comparable<?>>  list = new  ArrayList<Comparable<?>> ();
                    for(int i = 0; i < sortAscending.length; i++){
                        list.add(o.getString(searchs.getJSONObject(i).getString("field")));
                    }
                    return list;
                }
                
            }, SortBeanListUtil.NA_LAST);
        }catch(Exception e){
            logger.log(Level.INFO, "{0}",e);
        }
        return  jsonArray;
    }
    
    public static List<JSONObject> limit(List<JSONObject> jsonArray, JSONObject json){
        if(json != null && !json.isEmpty()){
            try{
                int offset = json.getInt("offset");
                int limit = json.getInt("limit");
                jsonArray =jsonArray.subList( offset , Math.min(offset + limit, jsonArray.size()));
            }catch(Exception e){
                logger.log(Level.INFO, "{0}",e);
            }
        }
        return jsonArray;
    }
}
