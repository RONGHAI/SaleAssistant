package com.ecbeta.common.core;

import com.ecbeta.common.core.annotation.Dao;
import java.io.Serializable;
import java.util.List;

import com.ecbeta.common.core.bean.BaseServicerParaBean;
import com.ecbeta.common.core.viewer.bean.ExportInformationBean;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.core.viewer.bean.PanelTab;
import org.springframework.context.ApplicationContext;

public abstract class AbstractServicer implements Serializable{
    
    protected ApplicationContext appContext;

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }
    
    public List<NavigationBean> navigationBeans;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected BaseServicerParaBean baseServicerParameterBean = new BaseServicerParaBean();
    
    private ExportInformationBean exportInformationBean;
    public void beforeBinding(String btn){
        
    }
    public void destory(){
        
    }
    public BaseServicerParaBean getBaseServicerParameterBean () {
        return baseServicerParameterBean;
    }
    public ExportInformationBean getExportInformationBean () {
        return exportInformationBean;
    }
    public PanelTab[] getPanels(){
        return null;
    }
    public void init(NavigationBean navigationBean){
        
    }
    public boolean isDisplayStage(){
        return this.baseServicerParameterBean.isDisplayStage();
    }
    public void processFilter(){
        
    }
    public void retrieveData(){
        
    }
    public void setBaseServicerParameterBean (BaseServicerParaBean baseServicerParameterBean) {
        this.baseServicerParameterBean = baseServicerParameterBean;
    }
    public void setExportInformationBean (ExportInformationBean exportInformationBean) {
        this.exportInformationBean = exportInformationBean;
    }
    public void sort(int ... panels){
    }
    public void updateDisplay(String btn){
        
    }
    
    
    public List<String> updateExportInformationBean(){
        return null;
    }
    public List<NavigationBean> getNavigationBeans () {
        return navigationBeans;
    }
    public void setNavigationBeans (List<NavigationBean> navigationBeans) {
        this.navigationBeans = navigationBeans;
    }
}
