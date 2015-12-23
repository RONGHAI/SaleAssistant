package com.weinyc.sa.app.model;

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
@Entity(name = "orders")
public class Order extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final JSONArray COLUMNS;
    private static ModelMeta<Order> modelMeta;
    static {
        COLUMNS = new JSONArray();
        COLUMNS.add(new W2UIColumnBean("recid", "ID", "20%", true).toJson());
        COLUMNS.add(new W2UIColumnBean("orderNumer", "Order Number", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("orderStatus", "Order Status", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("clientId", "Client", "20%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("cost", "Cost", "15%", true, "currency" , JSONObject.fromObject("{ type: 'currency' , currencyPrefix: '' }")).toJson());
        COLUMNS.add(new W2UIColumnBean("discount", "Discount", "15%", true, "currency" , JSONObject.fromObject("{ type: 'currency' , currencyPrefix: ''  }")).toJson());
        W2UIColumnBean col = new W2UIColumnBean("currencyId", "Currency", "20%", Constants.SAJS_PREFIX+".render_currency", false, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".currencies()' }"));
        col.setSearchable(false);
        COLUMNS.add(col.toJson());
        
       
        COLUMNS.add(new W2UIColumnBean("shippingFee", "Shipping", "15%", true, "currency" , JSONObject.fromObject("{ type: 'currency' , currencyPrefix: ''  }")).toJson());
        COLUMNS.add(new W2UIColumnBean("duty", "Duty", "15%", true, "text" , JSONObject.fromObject("{ type: 'text'  }")).toJson());
        col = new W2UIColumnBean("shippingFeeCurrencyId", "Currency", "20%", Constants.SAJS_PREFIX+".render_currency", false, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".currencies()' }"));
        col.setSearchable(false);
        COLUMNS.add(col.toJson());
        
        COLUMNS.add(new W2UIColumnBean("salePrice", "Sale Price", "15%", true, "currency" , JSONObject.fromObject("{ type: 'currency' , currencyPrefix: ''  }")).toJson());    
        col = new W2UIColumnBean("salePriceCurrencyId", "Currency", "20%", Constants.SAJS_PREFIX+".render_currency", false, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".currencies()' }"));
        col.setSearchable(false);
        COLUMNS.add(col.toJson());
        
        
        COLUMNS.add(new W2UIColumnBean("netProfit", "Net Profit", "15%", true, "currency" , JSONObject.fromObject("{ type: 'currency' , currencyPrefix: '' }")).toJson());
        col = new W2UIColumnBean("netProfitCurrencyId", "Currency", "20%", Constants.SAJS_PREFIX+".render_currency", false, null,  JSONObject.fromObject("{ type: 'select', items:'"+Constants.SAJS_PREFIX+".currencies()' }"));
        col.setSearchable(false);
        COLUMNS.add(col.toJson());
        COLUMNS.add(new W2UIColumnBean("recid", "Add Product", "20%" , Constants.SAJS_PREFIX+".render_button" , false , null, null, false).toJson());
        COLUMNS.add(new W2UIColumnBean("recid", "Add Tracking", "20%" , Constants.SAJS_PREFIX+".render_button" , false , null, null, false).toJson());
        COLUMNS.add(new W2UIColumnBean("recid", "Add US Tracking", "20%" , Constants.SAJS_PREFIX+".render_button" , false , null, null, false).toJson());        
    }
    
    /*
ALTER TABLE orders ALTER COLUMN currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN sale_price_currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN shipping_fee_currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN net_profit_currency_id  DROP NOT NULL;
*/

    public static Order fromJson(JSONObject json) {  
        
        for(Object o : COLUMNS.toArray()){
            JSONObject ob = (JSONObject)o;
            expectOne(json, ob.getString("field"));
        }
        
        expectOne(json, "recid");
        expectOne(json, "id");
        if (json.has("recid") && !json.has("id")) {
            json.put("id", json.get("recid"));
        }
        return Order.fromJson(json, Order.class);
    }

    public static ModelMeta<Order> _getModelMeta() {
        if (modelMeta == null) {
            modelMeta = new ModelMeta<>(Order.class);
        }
        return modelMeta;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumer;
    
    @Column(name = "currency_id")
    private Long currencyId;

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getSalePriceCurrencyId() {
        return salePriceCurrencyId;
    }

    public void setSalePriceCurrencyId(Long salePriceCurrencyId) {
        this.salePriceCurrencyId = salePriceCurrencyId;
    }

    public Long getShippingFeeCurrencyId() {
        return shippingFeeCurrencyId;
    }

    public void setShippingFeeCurrencyId(Long shippingFeeCurrencyId) {
        this.shippingFeeCurrencyId = shippingFeeCurrencyId;
    }

    public Long getNetProfitCurrencyId() {
        return netProfitCurrencyId;
    }

    public void setNetProfitCurrencyId(Long netProfitCurrencyId) {
        this.netProfitCurrencyId = netProfitCurrencyId;
    }
     
    @Column(name = "sale_price_currency_id")
    private Long salePriceCurrencyId;
      
    @Column(name = "shipping_fee_currency_id")
    private Long shippingFeeCurrencyId;
    
    @Column(name = "net_profit_currency_id")
    private Long netProfitCurrencyId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "client_id")
    private Long clientId;

    private transient Client client;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "duty")
    private Double duty;

    @Column(name = "net_profit")
    private Double netProfit;
    @Column(name = "disabled")
    private Integer disabled;
    @Column(name = "add_time", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date addTime;
    
    @Column(name = "update_time", nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;
    
    @Column(name = "note")
    private String note;
    
    private transient boolean changed;

    public Order() {
    }
    
    public Order(Long id) {
        this.id = id;
    }
    
    public String getOrderNumer() {
        return orderNumer;
    }

    public void setOrderNumer(String orderNumer) {
        this.orderNumer = orderNumer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getDuty() {
        return duty;
    }

    public void setDuty(Double duty) {
        this.duty = duty;
    }


    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
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
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
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
    public Object toJson() {
        JSONObject map = JSONUtils.toJSON(this);
        map.put("recid", this.getRecid());
        map.put("id", this.id);
        
        return map;
    }

    @Override
    public ModelMeta<Order> modelMeta() {
        return _getModelMeta();
    }

}
