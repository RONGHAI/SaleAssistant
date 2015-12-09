package com.weinyc.sa.core.engine;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weinyc.sa.core.bean.BaseServicerParaBean;
import com.weinyc.sa.core.db.DatabaseHandler;
import com.weinyc.sa.core.delegate.ProcessDelegate;
import com.weinyc.sa.core.viewer.bean.ExportInformationBean;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.core.viewer.bean.PanelTab;
import com.weinyc.sa.core.model.AbstractModel;

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

    public boolean saveOrUpdate(JSONArray changes) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Collection<Long> collection) {
        throw new UnsupportedOperationException();
    }
    
    public JSONArray getJSONArray(JSONObject json){
        throw new UnsupportedOperationException();
    }
    public List<? extends AbstractModel> beans(){
        throw new UnsupportedOperationException();
    }
}
