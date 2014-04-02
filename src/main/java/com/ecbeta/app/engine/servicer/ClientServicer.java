package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import me.ronghai.sa.dao.impl.ClientDAOImpl;
import me.ronghai.sa.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ClientServicer extends AbstractServicer implements me.ronghai.sa.engine.service.ClientService {

    @Autowired
    private me.ronghai.sa.dao.impl.ClientDAOImpl clientDAO;

    public ClientDAOImpl getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    /**
     *
     */
    private static final long serialVersionUID = 4874672762001288584L;

    @Override
    public void init(NavigationBean navBean) {
        this.refresh();
    }

    private void refresh() {
        this.clients = this.find();
    }

    @Override
    public void destory() {

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Client update(Client entity) {
        Client c = clientDAO.update(entity);
        this.refresh();
        return c;
    }

    @Override
    public Client find(Object id) {
        return clientDAO.find(id);
    }

    @Override
    public List<Client> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Client c) {
        this.clientDAO.remove(c, false);
        this.refresh();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Long... ids) {
        this.clientDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void remove(Collection<Long> ids) {
        this.clientDAO.remove(false, ids);
        this.refresh();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Client save(Client c) {
        System.out.println("save...");
        this.clientDAO.persistent(c);
        this.refresh();
        return c;
    }

}
