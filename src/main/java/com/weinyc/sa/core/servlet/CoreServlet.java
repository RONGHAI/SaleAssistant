package com.weinyc.sa.core.servlet;

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

import com.weinyc.sa.core.engine.AbstractServicer;
import com.weinyc.sa.core.engine.AbstractController;
import com.weinyc.sa.core.annotation.ServicerType;
import com.weinyc.sa.core.db.DatabaseHandler;
import com.weinyc.sa.core.db.NavigationUtil;
import com.weinyc.sa.core.engine.RequestManager;
import com.weinyc.sa.core.engine.ServicerFactory;
import com.weinyc.sa.core.reflect.ReflectUtils;
import com.weinyc.sa.core.viewer.bean.NavigationBean;
import com.weinyc.sa.app.dao.NavigationDAO;
import com.weinyc.sa.common.constants.Constants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component("CoreServlet")
public class CoreServlet extends HttpServlet implements org.springframework.web.HttpRequestHandler {

    private static final long serialVersionUID = 8170475189258718371L;
    private static final Logger logger = Logger.getLogger(CoreServlet.class.getName());
    private String jspPath = "/WEB-INF/jsp/";

    @Autowired
    protected ApplicationContext appContext;

    @Autowired
    protected NavigationDAO navigationDAO;

    @Autowired
    protected DataSource dataSource;
    
    @Autowired
    protected DatabaseHandler databaseHandler;

    @Override
    public void init() throws ServletException {
        try {
            jspPath = (getServletConfig().getInitParameter("jspPath"));
            if (jspPath.charAt(jspPath.length() - 1) != '/') {
                jspPath += "/";
            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        logger.log(Level.INFO, "jsp path is " + jspPath, "");
        getNavigationBeans(false);

    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.service(request, response);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AbstractController worker = RequestManager.getInstance().createWorker(request, response, this);
        this.injectServicers(request, response, worker);
        worker.processRequest();
    }

    public ApplicationContext getAppContext() {
        if (appContext == null) {
            this.appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        }
        return this.appContext;
    }

    public NavigationDAO getNavigationDAO() {
        if (navigationDAO == null) {
            this.navigationDAO = (NavigationDAO) this.getAppContext().getBean("navigationDAO");
        }
        return this.navigationDAO;
    }
    protected List<NavigationBean> getNavigationBeans() {
        return getNavigationBeans(false);
    }
    @SuppressWarnings("unchecked")
    protected List<NavigationBean> getNavigationBeans(boolean init) {
        List<NavigationBean> naviBeans = (List<NavigationBean>) this.getServletContext().getAttribute(Constants.ALL_NAVIGATIONBEANS);
        if (naviBeans == null || init) {
            naviBeans = NavigationUtil.convert(this.getNavigationDAO().find());
            this.getServletContext().setAttribute(Constants.ALL_NAVIGATIONBEANS, naviBeans);
        }
        return naviBeans;
    }

    public static NavigationBean find(List<NavigationBean> navBeans, String navTier, String worker) {
        if (navBeans == null || navBeans.isEmpty()) {
            return null;
        }
        for (NavigationBean b : navBeans) {
            if ((navTier != null && b.getNavTier("_").equals(navTier)) || (worker != null && worker.equals(b.getWorker()))) {
                return b;
            }
            NavigationBean cf = find(b.getChildren(), navTier, worker);
            if (cf != null) {
                return cf;
            }
        }
        return null;
    }

    protected Map<String, NavigationBean> getCache() {
        @SuppressWarnings("unchecked")
        Map<String, NavigationBean> cache = (Map<String, NavigationBean>) this.getServletContext().getAttribute("ALL_NAVIGATIONBEANS_CACHE");
        if (cache == null) {
            cache = new HashMap<>();
            this.getServletContext().setAttribute("ALL_NAVIGATIONBEANS_CACHE", cache);
        }
        return cache;
    }

    protected NavigationBean findFromCache(String key) {
        Map<String, NavigationBean> cache = getCache();
        NavigationBean bean = cache.get(key);
        return bean;
    }

    protected NavigationBean save2Cache(String key, NavigationBean bean) {
        getCache().put(key, bean);
        return bean;
    }

    public NavigationBean find(String navTier) {
        NavigationBean bean = this.findFromCache(navTier);
        if (bean != null) {
            return bean;
        }
        List<NavigationBean> navBeans = this.getNavigationBeans();
        return this.save2Cache(navTier, find(navBeans, navTier, null));
    }

    public NavigationBean findByWorker(String worker) {
        NavigationBean bean = this.findFromCache(worker);
        if (bean != null) {
            return bean;
        }
        List<NavigationBean> navBeans = this.getNavigationBeans();
        return this.save2Cache(worker, find(navBeans, null, worker));
    }

    protected DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = (DataSource) this.getAppContext().getBean("dataSource");
        }
        return dataSource;
    }
    
    protected DatabaseHandler getDatabaseHandler(){
        if(databaseHandler == null){
            databaseHandler = (DatabaseHandler) this.getAppContext().getBean("databaseHandler");
        }
        if(databaseHandler == null){
            this.databaseHandler = new DatabaseHandler(this.getDataSource());
        }
        return databaseHandler;
    }
    
    protected void injectServicers(HttpServletRequest request, HttpServletResponse response, AbstractController worker) {
        Collection<Field> fields = ReflectUtils.getDeclaredFields((Map<String, Field>) null, worker.getClass(), false).values();
        List<NavigationBean> naviBeans = this.getNavigationBeans( Boolean.parseBoolean(request.getParameter(Constants.FORCE_INIT)));
        for (Field field : fields) {
            if (!field.isAnnotationPresent(ServicerType.class)) {
                continue;
            }
            String fieldClassName = field.getAnnotation(ServicerType.class).value();
            if (StringUtils.isEmpty(fieldClassName)) {
                fieldClassName = field.getType().getName();
            }
            try {
                AbstractServicer servicer = ServicerFactory.getService(request.getSession(), 
                            fieldClassName, field.getName(), this.getAppContext(), field.getAnnotation(ServicerType.class).spring(), worker, this.getDatabaseHandler());
                servicer.setDatabaseHandler(this.getDatabaseHandler());
                servicer.setNavigationBeans(naviBeans);
                if (ServicerFactory.isNewInstance(request.getSession(), fieldClassName, field.getName(), worker) || Boolean.parseBoolean(request.getParameter(Constants.FORCE_INIT))) {
                    servicer.init(worker.getNavigationBean());
                }
                Method setter = ReflectUtils.findSetter(worker, field, null, null);
                ReflectUtils.updateFieldValue(worker, field, setter, servicer);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }

    }

    public String getJspPath() {
        return jspPath;
    }
}
