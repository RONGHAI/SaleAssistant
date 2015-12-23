package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.app.engine.servicer.TrackingServicer;
import com.weinyc.sa.app.engine.servicer.CurrencyServicer;
import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.OrderServicer;
import com.weinyc.sa.app.model.Order;
import net.sf.json.JSONObject;
public class OrderController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.OrderServicer", spring="")
    private OrderServicer servicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.CurrencyServicer", spring="")
    private CurrencyServicer currencyServicer;
    
    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.TrackingServicer", spring="")
    private TrackingServicer trackingServicer;

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
    public AbstractServicer getServicer (String swithServicer){
        if("currencyServicer".equals(swithServicer)){
            return this.currencyServicer;
        }else  if("trackingServicer".equals(swithServicer)){
            return this.trackingServicer;
        }
        return this.getServicer();
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
   
}
