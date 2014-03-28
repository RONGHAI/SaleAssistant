/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.app.engine.worker;

import com.ecbeta.app.engine.servicer.BareServicer;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.annotation.ServicerType;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HomeWorker extends AbstractWorker{
    @ServicerType("com.ecbeta.app.engine.servicer.BareServicer")
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
