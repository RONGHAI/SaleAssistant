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
@Entity(name="accounts")
public class Account extends AbstractModel implements Serializable {

    public static   ModelMeta<Account> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Account.class);
        }
        return modelMeta;
    }
    public static  Account fromJson(JSONObject json){               
        expectOne(json, "merchantId");
        expectOne(json, "email");
        expectOne(json, "username");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Account.fromJson(json, Account.class);
    }

    public static final JSONArray COLUMNS;


    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        COLUMNS.add(new W2UIColumnBean("username", "UserName", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("email", "Email", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("merchantId", "Merchant", "20%", Constants.SAJS_PREFIX+".render_merchant", true, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".merchants()' }")).toJson());
        
    }

    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        map.put("username", username);
        map.put("email", email);
        map.put("merchantId", this.merchantId);
        map.put("merchant", merchant);
        return map;
    }
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "user_name")
    private String username;

    @Column(name = "merchant_id")
    private Long merchantId;

    protected transient Merchant merchant;

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

    private transient  boolean changed;

    
    private static ModelMeta<Account> modelMeta;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
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

    
    public Merchant getMerchant() {
        return merchant;
    }


    public String getNote() {
        return note;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUsername() {
        return username;
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
        return disabled.intValue() == DISABLED_YES;
    }

    @SuppressWarnings("unchecked")
    @Override
    public   ModelMeta<Account> modelMeta(){
        return _getModelMeta();
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    
    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled ? DISABLED_YES : DISABLED_NO;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
  
    public void setNote(String note) {
        this.note = note;
    }
   
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
    @Override
    public String toString() {
        return this.getClass().getName()+"[ id=" + id + " ]";
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    
}
