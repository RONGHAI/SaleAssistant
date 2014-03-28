package com.ecbeta.common.core.reflect;
/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */

public class PrimitiveShortConverter extends StringParamConverter<Short> {

 

	public Short convert(String o) throws ConverterException {
		if(isEmpty(o)) 	throw new ConverterException("null exception");
		o = trim(o);
		try{
			return Short.parseShort(o);
		}catch(Exception e){ 
			throw new ConverterException(e);
		}
	}

	public Class<Short> returnClazz() {
 		return Short.TYPE;
	}

}
