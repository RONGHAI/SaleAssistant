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
@Entity(name="carriers")
public class Carrier extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    
    @Column(name = "website")
    private String website;
    
    @Column(name = "track_url")
    private String trackURL;
    
    @Column(name =  "track_method")
    private String trackMethod;
    
    @Column(name = "disabled")
    private boolean disabled;

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Column(name = "add_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Carrier() {
    }

    public Carrier(Long id, String name, String website, String trackURL, String trackMethod, boolean disabled, Date addTime, Date updateTime, String note) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.trackURL = trackURL;
        this.trackMethod = trackMethod;
        this.disabled = disabled;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.note = note;
    }
    
    public Carrier(String name, String website, String trackURL, String trackMethod, boolean disabled, Date addTime, Date updateTime, String note) {
        this.name = name;
        this.website = website;
        this.trackURL = trackURL;
        this.trackMethod = trackMethod;
        this.disabled = disabled;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.note = note;
    }
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTrackURL() {
        return trackURL;
    }

    public void setTrackURL(String trackURL) {
        this.trackURL = trackURL;
    }

    public String getTrackMethod() {
        return trackMethod;
    }

    public void setTrackMethod(String trackMethod) {
        this.trackMethod = trackMethod;
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
        COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("website", "Homesite", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("trackURL", "Tracking URL", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("trackMethod", "HTTP", "20%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());

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
   
    public static  Carrier fromJson(JSONObject json){               
        expectOne(json, "name");
        expectOne(json, "website");
        expectOne(json, "trackURL");
        expectOne(json, "trackMethod");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Carrier.fromJson(json, Carrier.class);
    }
    
    private static ModelMeta<Carrier> modelMeta;
    @Override
    public   ModelMeta<Carrier> modelMeta(){
        return _getModelMeta();
    }
    public static   ModelMeta<Carrier> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Carrier.class);
        }
        return modelMeta;
    }
}