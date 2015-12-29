package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.app.bean.OrderStatus;
import com.weinyc.sa.app.engine.servicer.CarrierServicer;
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
import com.weinyc.sa.app.model.Product;
import com.weinyc.sa.app.model.Tracking;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OrderController extends AbstractController {

    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.OrderServicer", spring = "")
    public OrderServicer servicer;

    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.ProductServicer", spring = "")
    public ProductServicer productServicer;


    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.CurrencyServicer", spring = "")
    public CurrencyServicer currencyServicer;

    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.TrackingServicer", spring = "")
    public TrackingServicer trackingServicer;

    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.ClientServicer", spring = "clientService")
    public ClientServicer clientServicer;
    
    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.CarrierServicer", spring = "clientService")
    public CarrierServicer carrierServicer;

    public CarrierServicer getCarrierServicer() {
        return carrierServicer;
    }

    public void setCarrierServicer(CarrierServicer carrierServicer) {
        this.carrierServicer = carrierServicer;
    }
    
    @ServicerType(value = "com.weinyc.sa.app.engine.servicer.ClientAddressServicer", spring = "")
    public ClientAddressServicer addressServicer;

    public ProductServicer getProductServicer() {
        return productServicer;
    }

    public void setProductServicer(ProductServicer productServicer) {
        this.productServicer = productServicer;
    }

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
    public String getFORM_NAME() {
        return "OrderForm";
    }

    @Override
    public String getJSP_TOGO_PERIX() {
        return "Order";
    }

    @Override
    public AbstractServicer getServicer() {
        return servicer;
    }

    public void setServicer(OrderServicer servicer) {
        this.servicer = servicer;
    }

    @Override
    public void submitAction() {

    }

    public Object getColumnsAction() {
        return this.getColumns();
    }

    public String getColumns() {
        return JSONUtils.toString(Order.COLUMNS);
    }

    public Object listCurrenciesAction() {
        JSONObject json = requestJSON;
        return this.currencyServicer.getJSONArray(json);
    }

    public Object listClientsAction() {
        JSONObject json = requestJSON;
        return this.clientServicer.getJSONArray(json);
    }

    public Object listCarriersAction(){
        JSONObject json = requestJSON;
        return this.carrierServicer.getJSONArray(json);
    }
    
    public Object listOrderStatusesAction() {
        JSONArray jsonArray = new JSONArray();
        for (OrderStatus r : OrderStatus.values()) {
            jsonArray.add(r.toJSON());
        }
        return jsonArray;
    }

    @Override
    public AbstractServicer getServicer(String swithServicer) {
        JSONObject json = requestJSON;
        AbstractServicer servicer = super.getServicer(swithServicer);
        //
        if(servicer instanceof TrackingServicer){
            ((TrackingServicer)servicer).setOrder(this.servicer.find(new Long(json.getString("order"))));
        }
        return servicer;
    }

    public Object subGridsAction() {
        JSONArray json = new JSONArray();

        {
            JSONObject obj = new JSONObject();
            obj.put("grid", "productGrid");
            obj.put("label", "Add Product");
            obj.put("servicer", "productServicer");
            obj.put("columns",  JSONUtils.toString(Product.ORDER_COLUMNS) );
            json.add(obj);
        }

      /*  {
            JSONObject obj = new JSONObject();
            obj.put("grid", "usaTrackingGrid");
            obj.put("label", "Add US Tracking");
            obj.put("servicer", "trackingServicer");
            obj.put("columns", JSONUtils.toString(Tracking.ORDER_COLUMNS));
            json.add(obj);

        }
              
         */

        {
            JSONObject obj = new JSONObject();
            obj.put("grid", "trackingGrid");
            obj.put("label", "Add Tracking");
            obj.put("servicer", "trackingServicer");
            obj.put("columns",JSONUtils.toString( Tracking.ORDER_COLUMNS));
            json.add(obj);
        }

        return json;
    }

}
