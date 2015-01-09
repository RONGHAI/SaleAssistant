
package me.ronghai.sa.model;

import net.sf.json.JSONObject;

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
    public Long getRecid(){
        return this.getId();
    }
    
    
    public Object toJson(){
        return this;
    }
    public static <T extends AbstractModel>  T fromJson(JSONObject json, Class<T> clazz){               
        return (T) JSONObject.toBean( json, clazz);
    }
    
    public abstract <T extends AbstractModel>   ModelMeta<T> modelMeta();
    
}
