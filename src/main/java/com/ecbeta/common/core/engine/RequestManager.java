package com.ecbeta.common.core.engine;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.reflect.ReflectUtils;
import com.ecbeta.common.core.servlet.CoreServlet;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import com.ecbeta.common.util.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestManager implements Serializable, Cloneable {
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
    
    public AbstractWorker createWorker(final HttpServletRequest request,
            final HttpServletResponse response,
            final CoreServlet serv) {
        
        String navTier = request.getParameter(Constants.NAV_TIERS);
        NavigationBean navigationBean = serv.find(navTier);
        String workerName = navigationBean == null ? null : navigationBean.getWorker();
        if(StringUtils.isEmpty(workerName)){
            workerName = Constants.DEFAULT_WORKER;
            navigationBean = serv.findByWorker(workerName);
        }
        try{
            Class<?> clazz = ReflectUtils.classForName(workerName);
            AbstractWorker w = (AbstractWorker) clazz.newInstance();
            request.setAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME, w);
            w.init(request, response, navigationBean, serv.getJspPath(), serv, serv.getServletContext());
            return w;
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
            logger.log(Level.SEVERE, null, e);
        }
        
        return null;
        
    }

}
