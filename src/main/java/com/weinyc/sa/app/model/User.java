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

import com.weinyc.sa.core.model.AbstractModel;
import com.weinyc.sa.core.viewer.bean.W2UIColumnBean;

/**
*
* @author weiwei
*/
@Entity(name="users")
public class User extends AbstractModel implements Serializable {


    public static   ModelMeta<User> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(User.class);
        }
        return modelMeta;
    }
    public static  User fromJson(JSONObject json){
    	expectOne(json, "password");
        expectOne(json, "email");
        expectOne(json, "name");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return User.fromJson(json, User.class);
    }

    public static final JSONArray COLUMNS;


    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("email", "Email", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("password", "Password", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
    }

    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        map.put("name", name);
        map.put("email", email);
        map.put("password", this.password);
        return map;
    }
    
    
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note",nullable=true)
    private String note;

    private transient  boolean changed;
    
    private static ModelMeta<User> modelMeta;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Date getAddTime() {
        return addTime;
    }

    
    
    public String getEmail() {
        return email;
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

    public String getUsername() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @SuppressWarnings("unchecked")
    @Override
    public   ModelMeta<User> modelMeta(){
        return _getModelMeta();
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setName(String name) {
        this.name = name;
    }
   
    @Override
    public String toString() {
        return this.getClass().getName()+"[ id=" + id + " ]";
    }
    
}
