package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.app.bean.OrderStatus;
import com.weinyc.sa.app.engine.servicer.ClientAddressServicer;
import com.weinyc.sa.app.engine.servicer.ClientServicer;
import com.weinyc.sa.app.engine.servicer.TrackingServicer;
import com.weinyc.sa.app.engine.servicer.CurrencyServicer;
import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.OrderServicer;
import com.weinyc.sa.app.engine.servicer.ProductServicer;
import com.weinyc.sa.app.model.Order;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class OrderController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.OrderServicer", spring="")
    private OrderServicer servicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ProductServicer", spring="")
    private ProductServicer productServicer;

    public ProductServicer getProductServicer() {
        return productServicer;
    }

    public void setProductServicer(ProductServicer productServicer) {
        this.productServicer = productServicer;
    }
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CurrencyServicer", spring="")
    private CurrencyServicer currencyServicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.TrackingServicer", spring="")
    private TrackingServicer trackingServicer;

     
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ClientServicer", spring="clientService")
    private ClientServicer clientServicer;

    public ClientServicer getClientServicer() {
        return clientServicer;
    }

    public void setClientServicer(ClientServicer clientServicer) {
        this.clientServicer = clientServicer;
    }

    public ClientAddressServicer getAddressServicer() {
        return addressServicer;
    }

    public void setAddressServicer(ClientAddressServicer addressServicer) {
        this.addressServicer = addressServicer;
    }
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.ClientAddressServicer", spring="")
    private ClientAddressServicer addressServicer;
    
    
    
    
    public TrackingServicer getTrackingServicer() {
        return trackingServicer;
    }

    public void setTrackingServicer(TrackingServicer trackingServicer) {
        this.trackingServicer = trackingServicer;
    }

    public CurrencyServicer getCurrencyServicer() {
        return currencyServicer;
    }

    public void setCurrencyServicer(CurrencyServicer currencyServicer) {
        this.currencyServicer = currencyServicer;
    }
    
    
    
    
    @Override
    public String getFORM_NAME () {
        return "OrderForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Order";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (OrderServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    
   
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Order.COLUMNS);
    }
    
    
    public Object listCurrenciesAction(){
        JSONObject json = this.getJSONObject();
        return this.currencyServicer.getJSONArray(json);
    }
    
    
    public Object listClientsAction(){
        JSONObject json = this.getJSONObject();
        return this.clientServicer.getJSONArray(json);
    }
    
    public Object listOrderStatusesAction(){
        JSONArray jsonArray = new JSONArray();
        for(OrderStatus r : OrderStatus.values()){
            jsonArray.add(r.toJSON());
        }
        return jsonArray;
    }
}

