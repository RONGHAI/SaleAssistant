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
@Entity(name="navigations")
public class Navigation   extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    
    @Column(name = "tier_1")
    private long tier_1;

    public long getTier_1() {
        return tier_1;
    }

    public void setTier_1(long tier_1) {
        this.tier_1 = tier_1;
    }

    public long getTier_2() {
        return tier_2;
    }

    public void setTier_2(long tier_2) {
        this.tier_2 = tier_2;
    }

    public long getTier_3() {
        return tier_3;
    }

    public void setTier_3(long tier_3) {
        this.tier_3 = tier_3;
    }

    public long getTier_4() {
        return tier_4;
    }

    public void setTier_4(long tier_4) {
        this.tier_4 = tier_4;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    @Column(name = "tier_2")
    private long tier_2;
    
    @Column(name = "tier_3")
    private long tier_3;
    
    @Column(name = "tier_4")
    private long tier_4;
    
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "worker")
    private String worker;
    
    @Column(name = "[order]")
    private int order;
    
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

    public Navigation() {
    }

    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    
    @Override
    public Object toJson(){
        JSONObject map = new JSONObject();
        map.put("id", this.id);
        map.put("label", this.label);
        map.put("worker", this.worker);
        map.put("tier_1", this.tier_1);
        map.put("tier_2", this.tier_2);
        map.put("tier_3", this.tier_3);
        map.put("tier_4", this.tier_4);
        map.put("order", this.order);
        map.put("recid", this.getRecid());
        System.out.println(map);
         
        return map;
    }
    
    public static  Navigation fromJson(JSONObject json){               
        expectOne(json, "label");
        expectOne(json, "worker");
        expectOne(json, "order");
        expectOne(json, "tier_1");
        expectOne(json, "tier_2");
        expectOne(json, "tier_3");
        expectOne(json, "tier_4");
        expectOne(json, "recid");
        expectOne(json, "id"); 
        if(json.has("recid") && !json.has("id")){
            json.put("id", json.get("recid"));
        }
        return Navigation.fromJson(json, Navigation.class);
    }
     
      public static final JSONArray COLUMNS;
    static{
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "10%", true ).toJson());
        COLUMNS.add(new W2UIColumnBean("worker", "Worker", "40%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("label", "Label", "30%", true, "text", JSONObject.fromObject("{ type: 'text' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("order", "order", "10%", true, "int", JSONObject.fromObject("{ type: 'int', min: 0 }")).toJson());
        COLUMNS.add(new W2UIColumnBean("tier_1", "Tier 1", "10%", true, "int", JSONObject.fromObject("{ type: 'int', min: 0 }")).toJson());
        COLUMNS.add(new W2UIColumnBean("tier_2", "Tier 2", "10%" ,true ,  "int", JSONObject.fromObject("{ type: 'int', min: 0 }")).toJson());
        COLUMNS.add(new W2UIColumnBean("tier_3", "Tier 3", "10%", true,  "int", JSONObject.fromObject("{ type: 'int', min: 0 }")).toJson());
        COLUMNS.add(new W2UIColumnBean("tier_4", "Tier 4", "10%", true,  "int", JSONObject.fromObject("{ type: 'int', min: 0 }")).toJson());
    }
    
    
    private static ModelMeta<Navigation> modelMeta;
    @Override
    public   ModelMeta<Navigation> modelMeta(){
        return _getModelMeta();
    }
    public static   ModelMeta<Navigation> _getModelMeta(){
        if(modelMeta == null){
            modelMeta = new ModelMeta<>(Navigation.class);
        }
        return modelMeta;
    }
     
}

