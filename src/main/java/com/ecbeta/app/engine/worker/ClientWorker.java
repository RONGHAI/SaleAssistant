package com.ecbeta.app.engine.worker;

import com.ecbeta.app.engine.servicer.ClientServicer;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.annotation.ServicerType;
import java.util.HashMap;

public class ClientWorker extends AbstractWorker{

    
    @ServicerType("com.ecbeta.app.engine.servicer.ClientServicer")
    private ClientServicer servicer;
    
    @Override
    public String getAppName () {
        return "Client";
    }

    @Override
    public String getFORM_NAME () {
        return "ClientForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Client";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (ClientServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
   

}
