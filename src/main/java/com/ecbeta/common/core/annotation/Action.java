/**
 * 
 */
package com.ecbeta.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ronghai
 * Nov 8, 2013 5:05:14 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 */
@Target( {ElementType.METHOD , ElementType.FIELD })   
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface Action {
    public String [] values() default "";
    public String refreshZones() default "" ;
    public boolean needForwordToJsp () default true;
    public boolean clearResult() default true;
}
