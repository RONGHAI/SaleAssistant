package me.ronghai.sa.model;

import static com.ecbeta.common.util.JSONUtils.expectMore;
import static com.ecbeta.common.util.JSONUtils.expectOne;

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

import com.ecbeta.common.core.viewer.bean.W2UIColumnBean;
import com.ecbeta.common.util.JSONUtils;

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
        expectMore(json, "categories");
        if (json.has("recid") && !json.has("id")) {
            json.put("id", json.get("recid"));
        }
        return Product.fromJson(json, Product.class);
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "english_name")
    private String english;

    @Column(name = "chinese_name")
    private String chinese;

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "add_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @Column(name = "update_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "note")
    private String note;
    
    
    private transient List<Category> categories;
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

    private transient boolean changed;

    public static final JSONArray COLUMNS;

    static {
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true).toJson());
        COLUMNS.add(new W2UIColumnBean("name", "Name", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("code", "Code", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("english", "English", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("chinese", "Chinese", "20%", true, "text", JSONObject.fromObject("{ type: 'text'  }")).toJson());
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
        return disabled;
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
        this.disabled = disabled;
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
        if( this.categories != null){
            map.put("categories", JSONUtils.toJSONObjects( this.categories));
        }
        if(this.attachments != null){
            map.put("attachments", JSONUtils.toJSONObjects( this.attachments));
        }
        map.put("id", this.id);
        return map;
    }

    @Override
    public String toString() {
        return "me.ronghai.sa.model.Product[ id=" + id + " ]";
    }
}
