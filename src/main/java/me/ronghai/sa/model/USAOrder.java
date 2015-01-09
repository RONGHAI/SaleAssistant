
package me.ronghai.sa.model;

import com.ecbeta.common.core.viewer.bean.W2UIColumnBean;
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
import static com.ecbeta.common.util.JSONUtils.expectOne;
/**
 *
 * @author ronghai
 */
@Entity(name="usa_orders")
public class USAOrder extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    public USAOrder() {
    }

    public USAOrder(Long id) {
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
        return disabled;
    }

    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
        if (!(object instanceof USAOrder)) {
            return false;
        }
        USAOrder other = (USAOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "me.ronghai.sa.model.USAOrder[ id=" + id + " ]";
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
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true ).toJson());
        //COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("wangwang", "Wangwang", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qq", "QQ", "20%", true, "int", JSONObject.fromObject("{ type: 'int', min: 10000 }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("qqName", "QQ Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'   }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("birthday", "Birthday", "20%" ,"date:mm/dd/yyyy", true , "date" , JSONObject.fromObject("{ type: 'date' }") ).toJson());
        //COLUMNS.add(new W2UIColumnBean("gender", "Gender", "20%", true, "text", JSONObject.fromObject("{ type: 'list', items:[{id:'M', text : \"Male\"}, {id:'F', text : \"Female\"}, {id:'U', text : \"U\"}]  }")).toJson());
        //COLUMNS.add(new W2UIColumnBean("phone", "Phone", "120px", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
    }
    
    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        return map;
    }
   
    public static  USAOrder fromJson(JSONObject json){               
        /*expectOne(json, "name");
        expectOne(json, "wangwang");
        expectOne(json, "qq");
        expectOne(json, "qqName");
        expectOne(json, "birthday");
        expectOne(json, "gender");
        expectOne(json, "phone");*/
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return USAOrder.fromJson(json, USAOrder.class);
    }
    
    private static ModelMeta<USAOrder> modelMeta;
    @Override
    public   ModelMeta<USAOrder> modelMeta(){
        return _getModelMeta();
    }
    public static   ModelMeta<USAOrder> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(USAOrder.class);
        }
        return modelMeta;
    }
}
