
package com.weinyc.sa.core.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.weinyc.sa.core.annotation.Relationship;
import com.weinyc.sa.core.reflect.ReflectUtils;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.model.ModelMeta;
import com.weinyc.sa.core.annotation.Jsonable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronghai
 */
public abstract class AbstractModel {
    private static final Logger logger = Logger.getLogger(AbstractModel.class.getName());
    public static final int DISABLED_YES = 1, DISABLED_NO = 0;
    public abstract void setDisabled(boolean disabled);
    public abstract boolean isDisabled();
    
    public abstract boolean isChanged();
    public abstract void setChanged(boolean changed);
    
    
    public int getDisabled(){
        return isDisabled()? DISABLED_YES : DISABLED_NO;
    }
    
    public abstract Long getId() ;
    public abstract void setId(Long id) ;
    @Jsonable
    public Long getRecid(){
        return this.getId();
    }
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[ id=" + this.getId() + " ]";
    }
    
    
    public Object toJson(){
        return this;
    }
    @SuppressWarnings("unchecked")
    public static <T extends AbstractModel>  T fromJson(JSONObject json, Class<T> clazz){               
        T bean =  (T) JSONObject.toBean( json, clazz);
       
        Map<String, Field> fields = new HashMap<>(); 
        fields = ReflectUtils.getDeclaredFields(fields, clazz, true);
        for(Field field : fields.values()){
            if(field.isAnnotationPresent(Relationship.class)){ 
                Object oldValue =  ReflectUtils.value(bean, field, null);
                if(oldValue == null) continue;
                Relationship r = field.getAnnotation(Relationship.class);
                if(field.getType().isArray() ){
                    Object value = oldValue;
                    int size = Array.getLength(value);
                    for(int i = 0; i < size; i++){
                        Object t = Array.get(value, i);
                        Array.set(value, i, JSONUtils.toBean( JSONObject.fromObject(t),  r.clazz() ));
                    }
                }else if(List.class.isAssignableFrom(field.getType())){
                    List<Object> value  =  (List<Object>)oldValue;
                    int size  = value.size();
                    for(int i = 0; i < size; i++){
                        Object t = value.get(i);
                        t =  JSONUtils.toBean( JSONObject.fromObject(t),  r.clazz());
                        value.set(i, t);
                    }
                }
                else{
                    try {
                        ReflectUtils.updateFieldValue(bean, field, null,  JSONUtils.toBean( JSONObject.fromObject(oldValue),  r.clazz() ) );
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                       logger.log(Level.ALL, null, e);
                    }
                }
                
            }
        }
        return bean;
    }
    
    public abstract <T extends AbstractModel>   ModelMeta<T> modelMeta();
    
}
