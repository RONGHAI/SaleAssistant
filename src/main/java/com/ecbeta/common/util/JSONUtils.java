/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.common.util;
import com.ecbeta.common.bean.MapJSONBean;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class JSONUtils {
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
        return json.toJson();
    }


}
