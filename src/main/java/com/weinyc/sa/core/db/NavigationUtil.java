/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.core.db;

import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.app.model.Navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author ronghai
 */
public class NavigationUtil {

    private static NavigationBean covert(Navigation na){
        NavigationBean bean = new NavigationBean();
        bean.setWorker(na.getWorker());
        bean.setLabel(na.getLabel());
        bean.setOrder(na.getOrder());
        bean.setId(na.getId().intValue());
        bean.setI18n(na.getI18n());
        bean.setNavTier(new int[]{(int) na.getTier_1(), (int) na.getTier_2(), (int) na.getTier_3(), (int) na.getTier_4()});
        return bean;
    }
    
    public static List<NavigationBean> convert(List<Navigation> navs) {        
        LinkedHashMap<String, NavigationBean> navBeans = new LinkedHashMap<>();
        
        List<NavigationBean> all = new ArrayList<>();
        for(Navigation na: navs){
            NavigationBean bean = covert(na);
            navBeans.put(bean.getNavTier("_"), bean);
            all.add(bean);
        }
        HashSet<String> removeKeys = new HashSet<>();
        for(NavigationBean bean : all){
            String parantKey = bean.getParentNavTier("_");
            NavigationBean navBean = navBeans.get(parantKey);
            if(navBean != null){
                navBean.addChild(bean);
                removeKeys.add(bean.getNavTier("_"));
            }
        }
        for(String key : removeKeys){
             navBeans.remove(key);
        } 
        List<NavigationBean> navBeanList = new ArrayList<>(navBeans.values());
        return navBeanList;
    }
    
    public final static Comparator<NavigationBean> comparator = new Comparator<NavigationBean>(){
        @Override
        public int compare(NavigationBean o1, NavigationBean o2) {
            if(o1.getOrder() != o2.getOrder()){
                return o1.getOrder() - o2.getOrder();
            }else{
                return o1.getId() - o2.getId();
            }
        }
        
    };
    
    public static void sort(List<NavigationBean> navBeans){
        if(navBeans == null) return;
        Collections.sort(navBeans, comparator);
        for(NavigationBean bean : navBeans){
            Collections.sort(bean.getChildren(), comparator);
        }
    } 
}
