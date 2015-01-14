package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.ronghai.sa.dao.impl.CurrencyDAOImpl;
import me.ronghai.sa.model.Currency;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrencyServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.CurrencyDAOImpl currencyDAO;

    public CurrencyDAOImpl getCurrencyDAO() {
        return currencyDAO;
    }

    public void setCurrencyDAO(CurrencyDAOImpl currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
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
        this.currencies = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Currency update(Currency entity) {
        Currency c = currencyDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Currency find(Object id) {
        return currencyDAO.find(id);
    }

    public List<Currency> find() {
        return currencyDAO.find(" WHERE disabled = false ");
    }


    public void remove(Currency c) {
        this.currencyDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        this.currencyDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }


    public void remove(Collection<Long> ids) {
        this.currencyDAO.remove(false, new ArrayList<>(ids));
        this.refresh();
    }

    public Currency save(Currency c) {
        this.currencyDAO.persistent(c);
        this.refresh();
        return c;
    }

    public void saveOrUpdate(JSONArray jsonArray) {
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Currency currency = Currency.fromJson(newJsonObj);
            Long id  = currency.getId();
            if(this.currencyDAO.exsit(id)){
                currency .setUpdateTime(new Date());
            }else{
                currency.setId(null);
                currency.setAddTime(new Date());
                currency .setUpdateTime(new Date());
            }
            this.saveOrUpdate(currency);
        };
        this.refresh();
    }

    private Currency saveOrUpdate(Currency currency) {
        if(currency.getId() == null){
           return this.save (currency);
        }else{
           return this.update(currency);
        }
    }

}