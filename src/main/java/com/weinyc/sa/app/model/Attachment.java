package com.weinyc.sa.app.model;

import static com.weinyc.sa.common.util.JSONUtils.expectOne;

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

import com.weinyc.sa.core.viewer.bean.W2UIColumnBean;
import com.weinyc.sa.core.model.AbstractModel;
/**
 *
 * @author ronghai
 */
@Entity(name="attachments")
public class Attachment extends AbstractModel implements Serializable {

    public static   ModelMeta<Attachment> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Attachment.class);
        }
        return modelMeta;
    }
    public static  Attachment fromJson(JSONObject json){               
        expectOne(json, "extension");
        expectOne(json, "mime");
        expectOne(json, "app");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Attachment.fromJson(json, Attachment.class);
    }

    public static final JSONArray COLUMNS;


    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        COLUMNS.add(new W2UIColumnBean("extension", "Suffix Name", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("mime", "MIME", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("app", "APP", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());

    }

    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        map.put("extension", this.extension);
        map.put("mime", this.mime);
        map.put("app", this.app);
        return map;
    }
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "mime")
    private String mime;
    
    public String getMime() {
        return mime;
    }
    public void setMime(String mime) {
        this.mime = mime;
    }

    @Column(name = "extension")
    private String extension;
    
    @Column(name = "app")
    private String app;
    
    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Column(name = "disabled")
    private Integer disabled;

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    @Column(name = "note")
    private String note;


    
    private static ModelMeta<Attachment> modelMeta;

    public Attachment() {
    }

    public Attachment(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Date getAddTime() {
        return addTime;
    }

    
   
    @Override
    public Long getId() {
        return id;
    }

    

    public String getNote() {
        return note;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
 

    @Override
    public boolean isDisabled() {
        return disabled == DISABLED_YES;
    }

    @SuppressWarnings("unchecked")
    @Override
    public   ModelMeta<Attachment> modelMeta(){
        return _getModelMeta();
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

   
    
    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled ? DISABLED_YES : DISABLED_NO;
    }

   
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    
    
  
    public void setNote(String note) {
        this.note = note;
    }
   
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
 
    @Override
    public String toString() {
        return this.getClass().getName()+"[ id=" + id + " ]";
    }
    @Override
    public boolean isChanged() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void setChanged(boolean changed) {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
