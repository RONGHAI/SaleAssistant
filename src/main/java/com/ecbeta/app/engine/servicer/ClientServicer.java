package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.annotation.Dao;
import com.ecbeta.common.core.viewer.bean.NavigationBean;

public class ClientServicer extends AbstractServicer {

    @Dao
    private me.ronghai.sa.engine.service.ClientService clientService;
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
