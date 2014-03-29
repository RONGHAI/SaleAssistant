package com.ecbeta.common.core.db;

import java.util.List;

import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.util.ArrayList;

public class NavigationUtil {
    
    
    public final static  List<NavigationBean> find(){
        return new ArrayList<NavigationBean>();
    }
    public final static NavigationBean find(String []n){
        return new NavigationBean();
    }
    
}
