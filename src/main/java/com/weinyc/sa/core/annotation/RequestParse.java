/**
 * 
 */
package com.weinyc.sa.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ronghai
 * Nov 14, 2012 2:43:44 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 */
@Target( {ElementType.METHOD , ElementType.FIELD })   
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface RequestParse {
    /** 
     * tag id & parameter name. or path parameter name.  from request or path. 
     */
    public String id() default "";
    public ParseMethodType parseType() default ParseMethodType.ParseRequest;
    public String actions() default "";
    public boolean canJson() default false;
    
    public String getter() default "";
    public String setter() default "";
    public String parser() default "";
    public int order() default 0;
}
