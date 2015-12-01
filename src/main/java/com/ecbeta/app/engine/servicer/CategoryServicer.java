package com.ecbeta.app.engine.servicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.ronghai.sa.dao.impl.CategoryDAOImpl;
import me.ronghai.sa.model.AbstractModel;
import me.ronghai.sa.model.Category;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.JSONUtils;
import com.ecbeta.common.util.SortBeanListUtil;
import com.ecbeta.common.util.SortableBeanParse;

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
    
    private void sort(List<Category> categories){
        if(categories == null){
            return;
        }
        SortBeanListUtil.sort3(categories, new int[]{0}, new boolean[]{true} , new SortableBeanParse<Category>(){
            @Override
            public List<Comparable<?>> parse(Category o) {
                List<Comparable<?>>  list = new ArrayList<>();
                list.add(o.getId());
                return list;
            }

            @Override
            public List<String> parseBeanToList(Category o) {
                return null;
            }
        });

    }

    private void refresh() {
        this.categories = this.find();
        this.sort(this.categories);
        Map<Long, Category> catz = new HashMap<>();
        for(Category cat : this.categories){
            catz.put(cat.getId(), cat);
        }
        for(Category cat : this.categories){
            if(cat.getParentId() != null && cat.getParentId().longValue() > 0L){
                catz.get(cat.getParentId()).addChild(cat);
            }
        }
        for(Category cat : this.categories){
            this.sort(cat.getChildren());
        }
        List<Category> catList = new ArrayList<Category>();
        for(Category cat : this.categories){
            if(!cat.isLevelOne()){
                continue;
            }
            catList.addAll(cats(cat));
        }
        this.categories = catList;
    }
    
    private List<Category> cats(Category cat){
        List<Category> c  = new ArrayList<>();
        c.add(cat);
        if(cat.getChildren() != null && !cat.getChildren().isEmpty()){
            for(Category child : cat.getChildren()){
                c.addAll(cats(child));
            }
        }else{
            return c;
        }
        return c;
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
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.categories, json);
    }
    
    public JSONArray getJSONArrayWithoutSort(JSONObject json){
        JSONArray jsonArray =  JSONUtils.toJSONArrayWithoutSort(this.categories, json);
        return jsonArray;
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
            if(category.getParentId() != null && category.getParentId().longValue() > 0L){
                category.setLevel(this.find(category.getParentId()).getLevel() + 1);
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
