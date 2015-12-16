package com.weinyc.sa.app.model;

import static com.weinyc.sa.common.util.JSONUtils.expectMore;
import static com.weinyc.sa.common.util.JSONUtils.expectOne;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weinyc.sa.core.annotation.Relationship;
import com.weinyc.sa.core.viewer.bean.W2UIColumnBean;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.common.constants.Constants;
import com.weinyc.sa.core.model.AbstractModel;

/**
 *
 * @author ronghai
 */
@Entity(name = "products")
public class Product extends AbstractModel implements Serializable {

    public static ModelMeta<Product> _getModelMeta() {
        if (modelMeta == null) {
            modelMeta = new ModelMeta<>(Product.class);
        }
        return modelMeta;
    }

    public static Product fromJson(JSONObject json) {
        expectOne(json, "name");
        expectOne(json, "code");
        expectOne(json, "english");
        expectOne(json, "chinese");
        expectOne(json, "recid");
        expectOne(json, "id");
    //    expectOne(json, "description");
        expectMore(json, "id", "categories"/*, Category.class*/);
        if (json.has("recid") && !json.has("id")) {
            json.put("id", json.get("recid"));
        }
        System.out.println(json);
        System.out.println(">>>>");
        return Product.fromJson(json, Product.class);
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code" , nullable = true)
    private String code;

    @Column(name = "english_name")
    private String english;

    @Column(name = "chinese_name")
    private String chinese;
    
    @Column(name = "description", nullable = true)
    private String description;
    


    @Column(name = "disabled")
    private Integer disabled;

    @Column(name = "add_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note" , nullable = true)
    private String note;
    
    
    @Relationship(clazz=Category.class)
    private transient List<Category> categories;
    
    @Relationship(clazz=Attachment.class)
    private transient List<Attachment> attachments;
    
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private transient boolean changed;

    public static final JSONArray COLUMNS;

    static {
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true).toJson());
        COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("code", "Code", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("english", "English", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("chinese", "Chinese", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        
        
        W2UIColumnBean col = new W2UIColumnBean("categories", "Category", "20%", Constants.SAJS_PREFIX+".render_cats", false, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".categories()' }"));
        col.setSearchable(false);
        COLUMNS.add(col.toJson());
    }

    private static ModelMeta<Product> modelMeta;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Date getAddTime() {
        return addTime;
    }

    public String getChinese() {
        return chinese;
    }

    public String getCode() {
        return code;
    }

    public String getEnglish() {
        return english;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
    public boolean isChanged() {
        return changed;
    }

    @Override
    public boolean isDisabled() {
        return disabled != null && disabled == DISABLED_YES;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelMeta<Product> modelMeta() {
        return _getModelMeta();
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @param disabled
     */
    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled ? DISABLED_YES : DISABLED_NO;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Object toJson() {
        JSONObject map = new JSONObject();
        map.put("recid", this.getRecid());
        map.put("name", this.name);
        map.put("code", this.code);
        map.put("english", this.english);
        map.put("chinese", this.chinese);
        map.put("description", this.description);
        if( this.categories != null){
            map.put("categories", JSONUtils.toJSONObjects( this.categories));
        }
        if(this.attachments != null){
            map.put("attachments", JSONUtils.toJSONObjects( this.attachments));
        }
        map.put("id", this.id);
        return map;
    }

     
}
