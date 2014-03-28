package com.ecbeta.common.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public abstract class StringParamConverter<E> implements ParamConverter<E, String> { 
	public Class<String> clazz(){
		return STRING_CLASS;
	}
	
	public boolean isEmpty(String  o){
		return o == null || o.trim().equals("") ;
	}
	
	public String trim(String o){
		if(o == null ) return o;
		return o.trim();
	}
}
