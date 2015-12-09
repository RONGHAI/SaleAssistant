/*
 * Copyright (C) 2014 Weiwei
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.common.util.JSONUtils;
import com.weinyc.sa.app.engine.servicer.NavigationServicer;
import com.weinyc.sa.app.model.Navigation;

/**
 *
 * @author Weiwei
 */
public class NavigationController     extends AbstractController{

    
    @ServicerType(value="com.weinyc.sa.app.engine.servicer.NavigationServicer", spring="navigationService")
    private NavigationServicer servicer;
    
    
    @Override
    public String getFORM_NAME () {
        return "NavigationForm";
    }
 
    @Override
    public String getJSP_TOGO_PERIX () {
        return "Navigation";
    }
    
    
    @Override
    public AbstractServicer getServicer () {
        return servicer;
    }

    public void setServicer (NavigationServicer servicer) {
        this.servicer = servicer;
    }
    
    @Override
    public void submitAction(){
        
    }
    

  
   
    public Object getColumnsAction(){
        return this.getColumns(); 
    }
    
    
    public String getColumns(){
        return JSONUtils.toString(Navigation.COLUMNS);
    }
    
   
}
