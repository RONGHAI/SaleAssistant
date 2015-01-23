package com.ecbeta.app.engine.servicer;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import me.ronghai.sa.dao.impl.CategoryDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Category;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServicer extends AbstractServicer  {

    @Autowired
    private me.ronghai.sa.dao.impl.CategoryDAOImpl categoryDAO;

    public CategoryDAOImpl getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAOImpl categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
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
        this.categories = this.find();
    }

    @Override
    public void destory() {

    }

   
    public Category update(Category entity) {
        Category c = categoryDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public Category find(Object id) {
        return categoryDAO.find(id);
    }

    public List<Category> find() {
        return categoryDAO.find(" WHERE disabled = false ");
    }


    public void remove(Category c) {
        this.categoryDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        if(ids == null) return;
        this.categoryDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(){
        return JSONUtils.toJSONArray(this.categories);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.categories;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 == this.categoryDAO.remove(false, new ArrayList<>(ids))){
            return false;
        };
        this.refresh();
        return true;
    }

    public Category save(Category c) {
        this.categoryDAO.persistent(c);
        this.refresh();
        return c;
    }

    @Override
    public boolean saveOrUpdate(JSONArray jsonArray) {
        if(jsonArray == null || jsonArray.isEmpty() ) return false;
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject newJsonObj = it.next();
            Category category = Category.fromJson(newJsonObj);
            Long id  = category.getId();
            if(this.categoryDAO.exsit(id)){
                category .setUpdateTime(new Date());
            }else{
                category.setId(null);
                category.setAddTime(new Date());
                category .setUpdateTime(new Date());
            }
            this.saveOrUpdate(category);
        }
        this.refresh();
        return true;
    }

    private Category saveOrUpdate(Category category) {
        if(category.getId() == null){
           return this.save (category);
        }else{
           return this.update(category);
        }
    }

}
