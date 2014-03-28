package com.ecbeta.common.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class BooleanArrayConverter extends ArrayParamConverter<Boolean> {

	 
	public Boolean[] convert(String[] o) throws ConverterException {
		if(o == null) return null;
		Boolean[] b = new Boolean[o.length];
		BooleanConverter converter = new BooleanConverter();
		for(int i = 0 ; i < b.length; i++){ 
			b[i] = converter.convert(o[i]);
		}
		return b;
	}

	public Class<Boolean[]> returnClazz() {
 		return Boolean[].class;
	} 
 
}
