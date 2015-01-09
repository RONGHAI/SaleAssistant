/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.model;

import com.ecbeta.common.core.viewer.bean.W2UIColumnBean;
import static com.ecbeta.common.util.JSONUtils.expectOne;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author ronghai
 */
@Entity(name = "system_informations")
public class Property  extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "disabled")
    private boolean disabled;

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Column(name = "add_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "note")
    private String note;
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "code")
    private String code;

    @Column(name = "[value]")
    private String value;

    private transient boolean changed;

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
  
    
    
    public static final JSONArray COLUMNS;
    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        COLUMNS.add(new W2UIColumnBean("code", "Code", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("value", "Value", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
    }
    
    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("code", this.code);
        map.put("value", this.value);
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        return map;
    }
   
    public static  Property fromJson(JSONObject json){               
        expectOne(json, "code");
        expectOne(json, "value");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Property.fromJson(json, Property.class);
    }

    private static ModelMeta<Property> modelMeta;
    @Override
    public   ModelMeta<Property> modelMeta(){
        return _getModelMeta();
    }
    public static   ModelMeta<Property> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Property.class);
        }
        return modelMeta;
    }
}
