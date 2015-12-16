package com.weinyc.sa.app.model;

import com.weinyc.sa.core.viewer.bean.W2UIColumnBean;
import com.weinyc.sa.core.model.AbstractModel;

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
import static com.weinyc.sa.common.util.JSONUtils.expectOne;

/**
 *
 * @author ronghai
 */
@Entity(name = "currencies")
public class Currency extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Column(name = "sign")
    private String sign;

    @Column(name = "exchange_rate")
    private double rate;

    @Column(name = "disabled")
    private Integer disabled;

    @Column(name = "add_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    public Currency() {
    }

    public Currency(Long id) {
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
        return disabled != null && disabled == DISABLED_YES;
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
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    private transient boolean changed;

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public static final JSONArray COLUMNS;

    static {
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true).toJson());
        COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("code", "Code", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("sign", "Sign", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("rate", "ExRate", "20%", true, "double", JSONObject.fromObject("{ type: 'double'   }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("birthday", "Birthday", "20%" ,"date:mm/dd/yyyy", true , "date" , JSONObject.fromObject("{ type: 'date' }") ).toJson());
        //COLUMNS.add(new W2UIColumnBean("gender", "Gender", "20%", true, "text", JSONObject.fromObject("{ type: 'list', items:[{id:'M', text : \"Male\"}, {id:'F', text : \"Female\"}, {id:'U', text : \"U\"}]  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("phone", "Phone", "120px", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
    }

    @Override
    public Object toJson() {
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        map.put("code", this.code);
        map.put("name", this.name);
        map.put("sign", this.sign);
        map.put("rate", this.rate);
        return map;
    }

    public static Currency fromJson(JSONObject json) {
        expectOne(json, "name");
        expectOne(json, "code");
        expectOne(json, "sign");
        expectOne(json, "rate");
        /*expectOne(json, "birthday");
         expectOne(json, "gender");
         expectOne(json, "phone");*/
        expectOne(json, "recid");
        expectOne(json, "id");
        if (json.has("recid") && !json.has("id")) {
            json.put("id", json.get("recid"));
        }
        return Currency.fromJson(json, Currency.class);
    }

    private static ModelMeta<Currency> modelMeta;

    @Override
    public ModelMeta<Currency> modelMeta() {
        return _getModelMeta();
    }

    public static ModelMeta<Currency> _getModelMeta() {
        if (modelMeta == null) {
            modelMeta = new ModelMeta<>(Currency.class);
        }
        return modelMeta;
    }
}
