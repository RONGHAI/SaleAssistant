package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
/**
 * 
 * @author Ronghai
 * Parameter COnverter.
 * Convert string or string[] to realy type.
 * @param <T>
 */
public interface ParamConverter <E,T> { 
	
	public static final Class<String> STRING_CLASS = String.class;
	public static final Class<String[]> STRING_ARRAY_CLASS = String[].class;
	/**
	 * *@param o only is string or String[]  from request/parameter 
	 *  if array is true, o's type is String[], otherwise o's type is String
	 * @return
	 * @throws ConverterException
	 */
	public E convert(T  o ) throws ConverterException;
	public Class<T> clazz();
	public Class<E> returnClazz();
	 
}
