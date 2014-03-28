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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("CoreServlet")
public class CoreServlet extends HttpServlet  implements org.springframework.web.HttpRequestHandler{
    private static final long serialVersionUID = 8170475189258718371L;
    private static final Logger logger =   Logger.getLogger(CoreServlet.class.getName()) ;
    private String jspPath="";
    
    @Autowired
    protected ApplicationContext appContext;
    
    @Autowired
    protected NavigationService navigationService;
    
    public void init() throws ServletException{
        try{ 
            jspPath =  ( getServletConfig().getInitParameter("jspPath") );
            if(jspPath.charAt(jspPath.length() - 1) != '/'){
                jspPath += "/";
            }
        }catch(Exception e){
            logger.log(Level.SEVERE, null, e);
        }
    }
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.service(request, response);
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
                AbstractServicer servicer = ServicerFactory.getService(request.getSession(), fieldClassName , field.getName(), this.appContext, worker); 
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

/*

1. Implement HttpRequestHandler
First of all, your servlet class must implement the org.springframework.web.HttpRequestHandler interface and provide an implementation for the handleRequest() method just like you would override doPost().
2. Declare the servlet as a Spring Bean
You can do this by either adding the @Component("myServlet") annotation to the class, or declaring a bean with a name myServlet in applicationContext.xml.
Collapse | Copy Code

@Component("myServlet")
public class MyServlet implements HttpRequestHandler {
...

3. Declare in web.xml a servlet named exactly as the Spring Bean
The last step is to declare a new servlet in web.xml that will have the same name as the previously declared Spring bean, in our case myServlet. The servlet class must be org.springframework.web.context.support.HttpRequestHandlerServlet.
Collapse | Copy Code

<servlet>
    <display-name>MyServlet</display-name>
    <servlet-name>myServlet</servlet-name>
    <servlet-class>
        org.springframework.web.context.support.HttpRequestHandlerServlet
    </servlet-class>
</servlet>
 
<servlet-mapping>
    <servlet-name>myServlet</servlet-name>
    <url-pattern>/myurl</url-pattern>
</servlet-mapping>

Now you are ready to inject any spring bean in your servlet class.
Collapse | Copy Code

@Component("myServlet")
public class MyServlet implements HttpRequestHandler {
        
        @Autowired
        private MyService myService;

*/