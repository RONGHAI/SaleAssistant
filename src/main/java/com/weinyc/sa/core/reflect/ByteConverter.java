package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class ByteConverter extends StringParamConverter<Byte> { 
	public Byte convert(String o) throws ConverterException {
		if(isEmpty(o)) return null;
		o = trim(o);
		try{
			return Byte.valueOf(o);
		}catch(NumberFormatException e){ 
			throw new ConverterException(e);
		}
	}

	public Class<Byte> returnClazz() { 
		return Byte.class;
	} 
 
}
