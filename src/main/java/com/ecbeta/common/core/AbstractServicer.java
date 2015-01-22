package com.ecbeta.common.core;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ecbeta.common.core.bean.BaseServicerParaBean;
import com.ecbeta.common.core.db.DatabaseHandler;
import com.ecbeta.common.core.delegate.ProcessDelegate;
import com.ecbeta.common.core.viewer.bean.ExportInformationBean;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.core.viewer.bean.PanelTab;

public abstract class AbstractServicer implements Serializable{
    
    protected transient DatabaseHandler databaseHandler;
    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    
    /*
    protected ApplicationContext appContext;

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }
    */
    public List<NavigationBean> navigationBeans;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected BaseServicerParaBean baseServicerParameterBean = new BaseServicerParaBean();
    
    private ExportInformationBean exportInformationBean;
   
    protected ProcessDelegate processDelegate;
    
    public void beforeBinding (HttpServletRequest request, String btnClicked, String actionMethod) {
        if(processDelegate != null){
            processDelegate.beforeBinding(request, btnClicked, actionMethod);
        }
    }
    
    public void afterBinding (HttpServletRequest request, String btnClicked, String actionMethod) {
        if(processDelegate != null){
            processDelegate.afterBinding(request, btnClicked, actionMethod);
        }
    }
    
    public void afterProcessing (HttpServletRequest request, String btnClicked, String actionMethod) {
        if(processDelegate != null){
            processDelegate.afterProcessing(request, btnClicked, actionMethod);
        }
    }
    public void beforeProcessing (HttpServletRequest request, String btnClicked, String actionMethod) {
        if(processDelegate != null){
            processDelegate.beforeProcessing(request, btnClicked, actionMethod);
        }
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
