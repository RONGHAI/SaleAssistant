package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import me.ronghai.sa.dao.impl.ClientDAOImpl;
import me.ronghai.sa.model.Client;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientServicer extends AbstractServicer  {

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

   
    public Client update(Client entity) {
        Client c = clientDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Client find(Object id) {
        return clientDAO.find(id);
    }

    public List<Client> find() {
        return clientDAO.find(" WHERE disabled = false ");
    }


    public void remove(Client c) {
        this.clientDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.clientDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        try {
            this.databaseHandler.update("DELETE FROM clients where id = 1 ");
            EntityTransaction et =  this.clientDAO.getEntityManager().getEntityManagerFactory().createEntityManager().getTransaction();
            et.begin();
            this.clientDAO.remove(false, ids);
            et.commit();
            this.refresh();
        }
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ClientServicer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client save(Client c) {
        System.out.println("save...");
        this.clientDAO.persistent(c);
        this.refresh();
        return c;
    }

}
