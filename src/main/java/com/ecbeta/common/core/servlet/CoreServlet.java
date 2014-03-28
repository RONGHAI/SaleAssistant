package com.ecbeta.common.core.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ecbeta.common.constants.Constants;
import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.annotation.ServicerType;
import com.ecbeta.common.core.db.NavigationUtil;
import com.ecbeta.common.core.engine.RequestManager;
import com.ecbeta.common.core.engine.ServicerFactory;
import com.ecbeta.common.core.reflect.ReflectUtils;
import com.ecbeta.common.core.viewer.bean.NavigationBean;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreServlet extends HttpServlet{
    private static final long serialVersionUID = 8170475189258718371L;
    private static final Logger logger =   Logger.getLogger(CoreServlet.class.getName()) ;
    private String jspPath="";
    public void init() throws ServletException{
        try{ 
            jspPath =  ( getServletConfig().getInitParameter("jspPath") );
            if(jspPath.charAt(jspPath.length() - 1) != '/'){
                jspPath += "/";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        AbstractWorker worker = RequestManager.getInstance().createWorker(request, response, this);
        this.injectServicers(request, response, worker);
        worker.processRequest();
    }
    
    @SuppressWarnings("unchecked")
    protected  List<NavigationBean>  getNavigationBean(){
        List<NavigationBean> naviBeans = ( List<NavigationBean> ) this.getServletContext().getAttribute(Constants.ALL_NAVIGATIONBEANS);
        if(naviBeans == null){
            naviBeans  = NavigationUtil.find();
            this.getServletContext().setAttribute(Constants.ALL_NAVIGATIONBEANS  , naviBeans);
        }
        return naviBeans;
    }
    
    protected void injectServicers(HttpServletRequest request, HttpServletResponse response, AbstractWorker worker){
        Collection<Field> fields = ReflectUtils.getDeclaredFields((Map<String, Field>)null, worker.getClass(), false).values();
        List<NavigationBean> naviBeans  = this.getNavigationBean();
        for(Field field : fields){
            if(!field.isAnnotationPresent(ServicerType.class)) continue;
            String fieldClassName = field.getAnnotation(ServicerType.class).value();
            if(StringUtils.isEmpty(fieldClassName)){
                fieldClassName = field.getType().getName();
            }
            try {
                AbstractServicer servicer = ServicerFactory.getService(request.getSession(), fieldClassName , field.getName(), worker); 
                if(ServicerFactory.isNewInstance(request.getSession(), fieldClassName , field.getName(), worker)){
                    servicer.setNavigationBeans(naviBeans);
                    servicer.init(worker.getNavigationBean());
                } 
                Method setter = ReflectUtils.findSetter(worker, field, null, null); 
                ReflectUtils.updateFieldValue(worker, field, setter,  servicer);
            } catch (    IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
        
        
    }

    public String getJspPath () {
        return jspPath;
    }
}
