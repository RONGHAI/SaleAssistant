package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.Dao;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import me.ronghai.sa.engine.service.ClientService;

public class ClientServicer extends AbstractServicer {

    @Dao
    private me.ronghai.sa.engine.service.ClientService clientService;

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
    /**
     * 
     */
    private static final long serialVersionUID = 4874672762001288584L;
    @Override
    public void init(NavigationBean navBean){
        
    }
    
    
    @Override
    public void destory(){
        
    }
}
