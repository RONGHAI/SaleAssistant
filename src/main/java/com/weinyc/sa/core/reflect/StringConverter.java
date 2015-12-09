package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class StringConverter implements  ParamConverter<String, Object> { 
	public Class<Object> clazz() {
		return Object.class;
	} 
	public String convert(Object o) throws ConverterException {
		if(o == null ) return null;
		return o.toString();
	}
	public Class<String> returnClazz() {
		 return String.class;
	} 
}
