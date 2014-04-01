package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.List;
import me.ronghai.sa.engine.service.ClientService;
import me.ronghai.sa.model.Client;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientServicer extends AbstractServicer {

    @Autowired
    private me.ronghai.sa.engine.service.ClientService clientService;
    
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }
    
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
        this.clients = this.clientService.find();
    }
    
    
    @Override
    public void destory(){
        
    }
}
