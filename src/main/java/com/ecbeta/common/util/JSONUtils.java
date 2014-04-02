/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.common.util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Collection;

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
	 
}
