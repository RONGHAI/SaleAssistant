package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.app.bean.Role;
import com.weinyc.sa.app.engine.servicer.UserServicer;
import com.weinyc.sa.app.model.User;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class UserController extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.UserServicer", spring="")
    private UserServicer servicer;
    
    @Override
    public String getFORM_NAME () {
        return "UserForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "User";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (UserServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(User.COLUMNS, 36);
    }
    
   
    public Object loadRolesAction(){
        JSONArray jsonArray = new JSONArray();
        for(Role r : Role.values()){
            jsonArray.add(r.toJSON());
        }
        return jsonArray;
    }
}
