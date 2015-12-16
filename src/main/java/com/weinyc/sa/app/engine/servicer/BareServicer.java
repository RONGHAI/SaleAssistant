/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.app.engine.servicer;

import com.weinyc.sa.core.dao.AbstractModelDAO;
import com.weinyc.sa.core.engine.AbstractServicer;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class BareServicer  extends AbstractServicer {
    private static final long serialVersionUID = 4874672762001288584L;
    @Override
    public AbstractModelDAO<?> getDAO(){
        return null;
    }
}
