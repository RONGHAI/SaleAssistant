package com.ecbeta.common.core.engine;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ecbeta.common.core.AbstractServicer;
import com.ecbeta.common.core.AbstractWorker;
import com.ecbeta.common.core.annotation.Dao;
import com.ecbeta.common.core.annotation.Spring;
import com.ecbeta.common.core.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Jay Lin
 *
 */
public class ServicerFactory {

    private static final Logger logger = Logger.getLogger(ServicerFactory.class.getName());
    private final static Set<String> newServiceInstanceIds = Collections.synchronizedSet(new HashSet<String>(100));

    private ServicerFactory() {
    }

    protected static String getServicerID(HttpSession session, String servicerClassName, String instanceName, AbstractWorker worker) {
        String id = session.getId() + "_" + servicerClassName + "_" + instanceName;

        if (worker != null) {
            id += "_" + worker.getClass().getName();
            if (StringUtils.isNotEmpty(worker.getNavTier())) {
                id += "_" + worker.getNavTier();
            }
        }

        return id;
    }

    public static AbstractServicer getServicer(HttpSession session, Class<?> servicerClass, String instanceName , ApplicationContext appContext) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return getService(session, servicerClass.getName(), instanceName, appContext, null);
    }

    public static AbstractServicer getService(HttpSession session, String className, 
            String instanceName,  ApplicationContext appContext, AbstractWorker worker) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String servicerId = getServicerID(session, className, instanceName, worker);
        Object servicer = session.getAttribute(servicerId);
        String date = buildDateString();
        if (servicer != null && servicer instanceof AbstractServicer) {
            newServiceInstanceIds.remove(date + "_" + servicerId);
            return (AbstractServicer) servicer;
        } else {
            Class<?> class1 = ReflectUtils.classForName(className);
            Object newInstance = class1.newInstance();
            injectDAOs((AbstractServicer) newInstance, appContext);
            autowired((AbstractServicer) newInstance, appContext);
            session.setAttribute(servicerId, newInstance);
            newServiceInstanceIds.add(date + "_" + servicerId);
            return (AbstractServicer) newInstance;
        }
    }

    protected static void injectDAOs(AbstractServicer ser, ApplicationContext appContext) {
        if(appContext == null ) return;
        Collection<Field> fields = ReflectUtils.getDeclaredFields((Map<String, Field>) null, ser.getClass(), false).values();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Dao.class) ) {
                continue;
            }
            String fieldName = field.getAnnotation(Dao.class).value();
            if (StringUtils.isEmpty(fieldName)) {
                fieldName = field.getName();
            }
            try {
                Method setter = ReflectUtils.findSetter(ser, field, null, null);
                ReflectUtils.updateFieldValue(ser, field, setter, appContext.getBean(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.log(Level.SEVERE, null, fieldName);
                logger.log(Level.SEVERE, null, e);
            }
        }

    }
    
    protected static void autowired(AbstractServicer ser, ApplicationContext appContext) {
        if(appContext == null ) return;
        Collection<Field> fields = ReflectUtils.getDeclaredFields((Map<String, Field>) null, ser.getClass(), false).values();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Spring.class) && !field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            
            String fieldName = field.getName();
            if(field.isAnnotationPresent(Spring.class) ){
                fieldName = field.getAnnotation(Spring.class).value();
                if (StringUtils.isEmpty(fieldName)) {
                    fieldName = field.getName();
                }
            }
             
            try {
                Method setter = ReflectUtils.findSetter(ser, field, null, null);
                ReflectUtils.updateFieldValue(ser, field, setter, appContext.getBean(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }

    }

    public static boolean isNewInstance(HttpSession session, String className, String instanceName, AbstractWorker worker) {
        cleanNewServiceInstanceIds();
        String servicerId = getServicerID(session, className, instanceName, worker);
        String date = buildDateString();
        return newServiceInstanceIds.contains(date + "_" + servicerId);
    }

    private static void cleanNewServiceInstanceIds() {
        int currentDate = Integer.parseInt(buildDateString());
        for (Iterator<String> it = newServiceInstanceIds.iterator(); it.hasNext();) {
            String s = it.next();
            String date = s.substring(0, s.indexOf("_"));
            int oldDate = Integer.parseInt(date);
            if ((currentDate - oldDate) > 2) {
                it.remove();
            }
        }
    }

    private static String buildDateString() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        return String.valueOf(year * 10000 + month * 100 + date);
    }
}
