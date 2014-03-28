package com.ecbeta.common.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public abstract class ArrayParamConverter<T> implements ParamConverter<T[],String[]> {  
	public Class<String[]> clazz() {
		 return STRING_ARRAY_CLASS;
	}


}
