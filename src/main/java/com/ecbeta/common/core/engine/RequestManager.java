package com.ecbeta.common.core.engine;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.reflect.ReflectUtils;
import com.ecbeta.common.core.servlet.CoreServlet;

public class RequestManager implements Serializable, Cloneable {

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
    
    public RequestManager clone(){
        return getInstance();
    }
    
    public AbstractWorker createWorker(final HttpServletRequest request,
            final HttpServletResponse response,
            final CoreServlet serv) {
        String workerName = request.getParameter(Constants.REQUEST_WORKER);
        if(StringUtils.isEmpty(workerName)){
            workerName = Constants.DEFAULT_WORKER;
        }
        try{
            Class<?> clazz = ReflectUtils.classForName(workerName);
            AbstractWorker w = (AbstractWorker) clazz.newInstance();
            request.setAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME, workerName);
            w.init(request, response, serv.getJspPath(), serv, serv.getServletContext());
            return w;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
        
    }

}
