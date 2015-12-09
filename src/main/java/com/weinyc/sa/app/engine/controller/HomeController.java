/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.engine.controller;

import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.app.engine.servicer.BareServicer;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HomeController extends AbstractController{
    @ServicerType("com.weinyc.sa.app.engine.servicer.BareServicer")
    BareServicer  servicer;

    @Override
    public BareServicer getServicer() {
        return servicer;
    }

    public void setServicer(BareServicer servicer) {
        this.servicer = servicer;
    }
    @Override
    public String getFORM_NAME() {
        return "HOME_FORM";
    }

    @Override
    public String getJSP_TOGO_PERIX() {
        return "Home";
    }
 
    
}
