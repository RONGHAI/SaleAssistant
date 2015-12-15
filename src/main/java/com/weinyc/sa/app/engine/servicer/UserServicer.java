package com.weinyc.sa.app.engine.servicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.weinyc.sa.app.dao.impl.UserDAOImpl;
import com.weinyc.sa.app.model.User;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.model.AbstractModel;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
/**
 * @author weiwei
 */
public class UserServicer extends AbstractServicer  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1023761597814267555L;
	@Autowired
    private com.weinyc.sa.app.dao.impl.UserDAOImpl userDAO;

    public UserDAOImpl getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void init(NavigationBean navBean) {
        this.refresh();
    }

    private void refresh() {
        this.users = this.find();
    }

    @Override
    public void destory() {

    }

   
    public User update(User entity) {
        User c = userDAO.update(entity);
        this.refresh();
        return c;
    }

 
    public User find(Object id) {
        return userDAO.find(id);
    }

    public List<User> find() {
        return userDAO.find(" WHERE disabled = 0 ");
    }


    public void remove(User c) {
        this.userDAO.remove(c, false);
        this.refresh();
    }


    public void remove(Long... ids) {
        if(ids == null) return;
        this.userDAO.remove(false, Arrays.asList(ids));
        this.refresh();
    }

    @Override
    public JSONArray getJSONArray(JSONObject json){
        return JSONUtils.toJSONArray(this.users, json);
    }
    
    @Override
    public List<? extends AbstractModel> beans(){
        return this.users;
    }
    
    @Override
    public boolean remove(Collection<Long> ids) {
        if(ids == null || ids.isEmpty() ) return false;
        if( 0 ==  this.userDAO.remove(false, new ArrayList<>(ids))){
            return false;
        }
        this.refresh();
        return true;
    }

    public User save(User c) {
        this.userDAO.persistent(c);
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
            User user = User.fromJson(newJsonObj);
            Long id  = user.getId();
            if(this.userDAO.exsit(id)){
                user .setUpdateTime(new Date());
            }else{
                user.setId(null);
                user.setAddTime(new Date());
            }
            this.saveOrUpdate(user);
        };
        this.refresh();
        return true;
    }

    private User saveOrUpdate(User user) {
        if(user.getId() == null){
           return this.save (user);
        }else{
           return this.update(user);
        }
    }

}
