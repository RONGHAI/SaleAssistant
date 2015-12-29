
package com.weinyc.sa.app.model;

import static com.weinyc.sa.app.model.Order.COLUMNS;
import com.weinyc.sa.common.constants.Constants;
import com.weinyc.sa.common.util.JSONUtils;
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
@Entity(name="trackings")
public class Tracking extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final JSONArray COLUMNS;
    public static final JSONArray ORDER_COLUMNS;
    private static ModelMeta<Tracking> modelMeta;
    static {
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        //COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("wangwang", "Wangwang", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qq", "QQ", "20%", true, "int", JSONObject.fromObject("{ type: 'int', min: 10000 }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qqName", "QQ Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'   }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("birthday", "Birthday", "20%" ,"date:mm/dd/yyyy", true , "date" , JSONObject.fromObject("{ type: 'date' }") ).toJson());
        //COLUMNS.add(new W2UIColumnBean("gender", "Gender", "20%", true, "text", JSONObject.fromObject("{ type: 'list', items:[{id:'M', text : \"Male\"}, {id:'F', text : \"Female\"}, {id:'U', text : \"U\"}]  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("phone", "Phone", "120px", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
    }
    static {
        ORDER_COLUMNS = JSONArray.fromObject(COLUMNS);
        ORDER_COLUMNS.add(new W2UIColumnBean("carrierId", "Carrier", "20%", Constants.SAJS_PREFIX+".render_carrier", true, null, 
                    JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".carriers()' }")).toJson());
        ORDER_COLUMNS.add(new W2UIColumnBean("trackingNumber", "Tracking Number", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        //ORDER_COLUMNS.add(new W2UIColumnBean("type", "Type", "20%", Constants.SAJS_PREFIX+".render_package_type",true,   "list" , JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".package_types()' }") ).toJson());
        W2UIColumnBean col = new W2UIColumnBean("type", "Tracking Type", "20%", Constants.SAJS_PREFIX+".render_package_type", true,  "list" , JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".package_types()' }"));
        col.setOptions(JSONObject.fromObject("{ items:'"+Constants.SAJS_PREFIX+".package_types()' }"));
        ORDER_COLUMNS.add(col);
        ORDER_COLUMNS.add(new W2UIColumnBean("_recid", "Operation", "45%" , Constants.SAJS_PREFIX+".render_tracking_buttons" , false , null, null, false).toJson());
        // ORDER_COLUMNS.remove(ORDER_COLUMNS.size() - 1);
    }

    public static Tracking fromJson(JSONObject json) {
        for(Object o : COLUMNS.toArray()){
            JSONObject ob = (JSONObject)o;
            expectOne(json, ob.getString("field"));
        }
        for(Object o : ORDER_COLUMNS.toArray()){
            JSONObject ob = (JSONObject)o;
            expectOne(json, ob.getString("field"));
        }
        expectOne(json, "recid");
        expectOne(json, "id");
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Tracking.fromJson(json, Tracking.class);
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public static ModelMeta<Tracking> _getModelMeta() {
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Tracking.class);
        }
        return modelMeta;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name="carrier_id")
    private Long carrierId;
    
    private transient Carrier carrier;
    
    @Column(name = "package_number")
    private String trackingNumber;
    
    @Column(name = "package_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
  
    @Column(name="related_id")
    private Long relatedId;

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
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
    private transient boolean changed;

    public Tracking() {
    }

    public Tracking(Long id) {
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
        if (!(object instanceof Tracking)) {
            return false;
        }
        Tracking other = (Tracking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
 
    
    

    @Override
    public boolean isChanged() {
        return changed;
    }
    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    
    
    @Override
    public Object toJson(){
        JSONObject map = JSONUtils.toJSON(this);
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        return map;
    }
   
    @Override
    public  ModelMeta<Tracking> modelMeta(){
        return _getModelMeta();
    }

}
