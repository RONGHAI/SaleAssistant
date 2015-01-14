package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.AccountDAOImpl;
import me.ronghai.sa.model.Account;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.AccountDAOImpl accountDAO;

    public AccountDAOImpl getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAOImpl accountDAO) {
        this.accountDAO = accountDAO;
    }

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
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
        this.accounts = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Account update(Account entity) {
        Account c = accountDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Account find(Object id) {
        return accountDAO.find(id);
    }

    public List<Account> find() {
        return accountDAO.find(" WHERE disabled = false ");
    }


    public void remove(Account c) {
        this.accountDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.accountDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.accountDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Account save(Account c) {
        this.accountDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Account account = Account.fromJson(newJsonObj);
            Long id  = account.getId();
            if(this.accountDAO.exsit(id)){
                account .setUpdateTime(new Date());
            }else{
                account.setId(null);
                account.setAddTime(new Date());
            }
            this.saveOrUpdate(account);
        };
        this.refresh();
    }

    private Account saveOrUpdate(Account account) {
        if(account.getId() == null){
           return this.save (account);
        }else{
           return this.update(account);
        }
    }

}