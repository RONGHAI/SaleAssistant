package com.weinyc.sa.core.engine;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weinyc.sa.core.reflect.ReflectUtils;
import com.weinyc.sa.core.servlet.CoreServlet;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.common.util.StringUtils;
import com.weinyc.sa.common.constants.Constants;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.ArrayUtils;

public class RequestManager implements Serializable, Cloneable {
    
    public static class PathInfo{
        private String navTier;

        public String getNavTier() {
            return navTier;
        }

        public void setNavTier(String navTier) {
            this.navTier = navTier;
        }
        
        public final static PathInfo createPathInfo(HttpServletRequest request){
            PathInfo bean = new PathInfo();
            String pathInfo = request.getPathInfo();
            if(StringUtils.isNotEmpty(pathInfo)){
                String paths[] =  pathInfo.split("/");
                if(paths != null){
                   paths = (String[]) ArrayUtils.removeElement(paths, "/");
                   paths = (String[]) ArrayUtils.removeElement(paths, "");
                   bean.navTier = paths.length > 0 ? paths[0] : "";
                }
            }
            
            if(StringUtils.isNotEmpty(request.getParameter(Constants.NAV_TIERS))){
                bean.navTier = request.getParameter(Constants.NAV_TIERS);
            }
            
            return bean;
        }
        
    }
    
    private static final Logger logger =   Logger.getLogger(RequestManager.class.getName()) ;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static RequestManager singleton = new RequestManager();

    private RequestManager() {
    }
    public static RequestManager getInstance() {
        if (singleton == null) {
            singleton = new RequestManager();
        }
        return singleton;
    }
    
    @Override
    public RequestManager clone() throws CloneNotSupportedException{
        return getInstance();
    }
    
    public AbstractController createWorker(final HttpServletRequest request,
            final HttpServletResponse response,
            final CoreServlet serv,   String workerName ) {
        NavigationBean  navigationBean = serv.findByWorker(workerName);
        Class<?> clazz;
        try{
            try {
                clazz = ReflectUtils.classForName(workerName);
            } catch (ClassNotFoundException e) {
                //logger.log(Level.SEVERE, null, e);
                workerName = Constants.CONTROLLER_PACKAGE+"."+workerName.substring(workerName.lastIndexOf(".")+1);
                clazz = ReflectUtils.classForName(workerName);
            }
            AbstractController w = (AbstractController) clazz.newInstance();
            request.setAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME, w);
            w.init(request, response, navigationBean, serv.getJspPath(), serv, serv.getServletContext());
            return w;
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
            logger.log(Level.SEVERE, null, e);
        }
        
        return null;
    }
    
    public AbstractController createWorker(final HttpServletRequest request,
            final HttpServletResponse response,
            final CoreServlet serv) {
        String navTier = PathInfo.createPathInfo(request).getNavTier();
        NavigationBean navigationBean = serv.find(navTier);
        String workerName = navigationBean == null ? null : navigationBean.getWorker();
        if(StringUtils.isEmpty(workerName)){
            workerName = Constants.DEFAULT_WORKER;
            navigationBean = serv.findByWorker(workerName);
        }
        Class<?> clazz;
        try{
            try {
                clazz = ReflectUtils.classForName(workerName);
            } catch (ClassNotFoundException e) {
                //logger.log(Level.SEVERE, null, e);
                workerName = Constants.CONTROLLER_PACKAGE+"."+workerName.substring(workerName.lastIndexOf(".")+1);
                clazz = ReflectUtils.classForName(workerName);
            }
            AbstractController w = (AbstractController) clazz.newInstance();
            request.setAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME, w);
            w.init(request, response, navigationBean, serv.getJspPath(), serv, serv.getServletContext());
            return w;
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
            logger.log(Level.SEVERE, null, e);
        }
        
        return null;
        
    }

}
