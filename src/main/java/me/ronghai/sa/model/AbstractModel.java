
package me.ronghai.sa.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ecbeta.common.core.annotation.Relationship;
import com.ecbeta.common.core.reflect.ReflectUtils;
import com.ecbeta.common.util.JSONUtils;
import com.ecbeta.common.util.annotation.Jsonable;

/**
 *
 * @author ronghai
 */
public abstract class AbstractModel {

    public abstract void setDisabled(boolean disabled);
    public abstract boolean isDisabled();
    
    public abstract boolean isChanged();
    public abstract void setChanged(boolean changed);
    
    public abstract Long getId() ;
    public abstract void setId(Long id) ;
    @Jsonable
    public Long getRecid(){
        return this.getId();
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
                        e.printStackTrace();
                    }
                }
                
            }
        }
        return bean;
    }
    
    public abstract <T extends AbstractModel>   ModelMeta<T> modelMeta();
    
}
