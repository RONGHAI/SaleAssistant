/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.core.context;

import me.ronghai.sa.util.SaleAssistantConstants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author L5M
 */
public final class SaleAssistanceApplicationContext {

    private static ClassPathXmlApplicationContext applicationContext;

    public final static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);
    }

    public final static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(name, requiredType);
    }

    public final static <T> T getBean(Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(requiredType);
    }

    public final static Object getBean(String name, Object... args) throws BeansException {
        return getApplicationContext().getBean(name, args);
    }

    public synchronized static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(SaleAssistantConstants.ContextFiles);
        }
        return applicationContext;
    }
}
