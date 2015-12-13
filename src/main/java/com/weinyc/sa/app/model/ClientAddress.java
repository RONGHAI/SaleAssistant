
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
import com.weinyc.sa.common.constants.Constants;
import com.weinyc.sa.core.model.AbstractModel;
/**
 *
 * @author ronghai
 */
@Entity(name="client_addresses")
public class ClientAddress extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;
    
    private transient Client client;
    
    @Column(name = "address", nullable=true)
    private String address;
    
    @Column(name = "phone", nullable=true)
    private String phone;
    
    @Column(name = "zip_code", nullable=true)
    private String zipcode;
    

    @Column(name = "disabled")
    private int disabled;

    @Column(name = "[default]")
    private int defaultAdress;
    
    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    public ClientAddress() {
    }

    public ClientAddress(Long id) {
        this.id = id;
    }

    
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    
    @Override
    public boolean isDisabled() {
        return disabled == DISABLED_YES;
    }

    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled ? DISABLED_YES : DISABLED_NO;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientAddress)) {
            return false;
        }
        ClientAddress other = (ClientAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"[ id=" + id + " ]";
    }
    
    
    private transient  boolean changed;

    @Override
    public boolean isChanged() {
        return changed;
    }
    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    

    public static final JSONArray COLUMNS;
    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", /*"10%",*/ true ).toJson());
        COLUMNS.add(new W2UIColumnBean("clientId", "Client", "20%",  Constants.SAJS_PREFIX+".render_client",true, "text" , null ).toJson());
        COLUMNS.add(new W2UIColumnBean("address", "Address", "30%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("zipcode", "Zipcode", "10%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("phone", "Phone", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("defaultAdress", "Default Address", "20%", true,  null , JSONObject.fromObject("{ type: 'checkbox'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("note", "Note", "10%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());

        //COLUMNS.add(new W2UIColumnBean("wangwang", "Wangwang", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qq", "QQ", "20%", true, "int", JSONObject.fromObject("{ type: 'int', min: 10000 }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qqName", "QQ Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'   }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("birthday", "Birthday", "20%" ,"date:mm/dd/yyyy", true , "date" , JSONObject.fromObject("{ type: 'date' }") ).toJson());
        //COLUMNS.add(new W2UIColumnBean("gender", "Gender", "20%", true, "text", JSONObject.fromObject("{ type: 'list', items:[{id:'M', text : \"Male\"}, {id:'F', text : \"Female\"}, {id:'U', text : \"U\"}]  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("phone", "Phone", "120px", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
    }
    
    @Override
    public Object toJson(){
     //   JSONUtils.toJSON(this);
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        map.put("zipcode", this.zipcode);
        map.put("address", this.address);
        map.put("clientId", this.clientId);
        map.put("phone", this.phone);
        map.put("note", this.note);
        map.put("defaultAdress", this.isDefaultAdress() );
        return map;
    }
   
    public static  ClientAddress fromJson(JSONObject json){               
        expectOne(json, "zipcode", "address", "clientId", "phone", "recid",  "defaultAdress", "note");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        if(Boolean.parseBoolean(json.getString("defaultAdress"))){
            json.put("defaultAdress", "1");
        }else{
            json.put("defaultAdress", "0");
        }
        return ClientAddress.fromJson(json, ClientAddress.class);
    }
    
    private static ModelMeta<ClientAddress> modelMeta;
    @SuppressWarnings("unchecked")
    @Override
    public   ModelMeta<ClientAddress> modelMeta(){
        return _getModelMeta();
    }
    public static   ModelMeta<ClientAddress> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(ClientAddress.class);
        }
        return modelMeta;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isDefaultAdress() {
        return defaultAdress == 1;
    }

    public void setDefaultAdress(boolean defaultAdress) {
        this.defaultAdress = defaultAdress ? 1 : 0; 
    }
    
    public int getDefaultAdress() {
        return defaultAdress;
    }

    public void setDefaultAdress(int defaultAdress) {
        this.defaultAdress = defaultAdress;
    }
}
