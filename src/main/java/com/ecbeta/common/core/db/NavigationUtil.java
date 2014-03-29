/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.common.core.db;

import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.ArrayList;
import java.util.List;
import me.ronghai.sa.model.Navigation;

/**
 *
 * @author ronghai
 */
public class NavigationUtil {

    public static List<NavigationBean> convert(List<Navigation> navs) {
        List<NavigationBean> navBeans = new ArrayList<>();
        for(Navigation na: navs){
            NavigationBean bean = new NavigationBean();
            bean.setWorker(na.getWorker());
            bean.setLabel(na.getLabel());
            bean.setNavTier(new int[]{(int)na.getTier_1(), (int)na.getTier_2(), (int)na.getTier_3(), (int)na.getTier_4()});
            navBeans.add(bean);
        }
        return navBeans;
    }
    
}
