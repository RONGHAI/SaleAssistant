/*
 * Copyright (C) 2015 L5M
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.weinyc.sa.app.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;

import org.springframework.jdbc.core.RowMapper;

import com.weinyc.sa.core.reflect.ReflectUtils;
import com.weinyc.sa.app.dao.impl.CarrierDAOImpl;

/**
 *
 * @author L5M
 * @param <T>
 */
public class ModelMeta<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Map<String, Field> columnFields;
    protected Map<String, Method> field2Setter, field2Getter;

    public Map<String, Field> getColumnFields() {
        return columnFields;
    }

    public Map<String, Method> getField2Setter() {
        return field2Setter;
    }

    public Map<String, Method> getField2Getter() {
        return field2Getter;
    }

    protected Class<T> clazz;

    
    protected RowMapper<T> rowMapper;
    
    public ModelMeta(Class<T> clazz) {
        this.clazz = clazz;
        this.columnFields = new HashMap<>();
        this.field2Setter = new HashMap<>();
        this.field2Getter = new HashMap<>();
        Map<String, PropertyDescriptor> fieldName2PropertyDescriptor = ReflectUtils.findFieldName2PropertyDescriptor(clazz);

        List<Field> cf = ReflectUtils.getDeclaredFields((List<Field>) null, clazz, false);
        for (Field field : cf) {
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = (Column) field.getAnnotation(Column.class) ;
                String cname;
                if (annotation != null && org.apache.commons.lang.StringUtils.isNotEmpty(annotation.name())) {
                    cname = annotation.name();
                    cname = cname.replaceAll("[\\[\\]]", "`");
                } else {
                    cname = field.getName();
                }
                this.columnFields.put(cname, field);
                this.field2Getter.put(field.getName(), ReflectUtils.findGetter(this.clazz, field,
                        fieldName2PropertyDescriptor, null));

                this.field2Setter.put(field.getName(), ReflectUtils.findSetter(this.clazz, field,
                        fieldName2PropertyDescriptor, null));
            }
        }
    }
    
    public static Object get(ResultSet rs, String cname, Class<?> type) throws SQLException{     
        if(type != null && type.equals(java.util.Date.class)){
            type = java.sql.Date.class;
        }
        return rs.getObject(cname, type);
    }
    
    public RowMapper<T> getRowMapper() {
        if (this.rowMapper == null) {
            this.rowMapper = new RowMapper<T>() {
                @Override
                public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                    T bean = null;
                    try {
                        bean = clazz.newInstance();
                        for (Map.Entry<String, Field> entry : columnFields.entrySet()) {
                            String cname = entry.getKey().replaceAll("`", "");
                            Method setter = field2Setter.get(entry.getValue().getName());
                            Class<?> t = ReflectUtils.findPropertyType(entry.getValue(), setter);
                            ReflectUtils.updateFieldValue(bean, entry.getValue(), setter, get(rs, cname, t));
                        }
                    }
                    catch (InvocationTargetException | IllegalArgumentException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(CarrierDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return bean;
                }
            };

        }
        return this.rowMapper;
    }
}
