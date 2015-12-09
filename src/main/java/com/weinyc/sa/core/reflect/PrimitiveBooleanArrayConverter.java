package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class PrimitiveBooleanArrayConverter  implements ParamConverter<boolean[],String[]>{

	 
	public boolean[] convert(String[] o) throws ConverterException {
		if(o == null) return null;
		boolean[] b = new boolean[o.length];
		PrimitiveBooleanConverter converter = new PrimitiveBooleanConverter();
		for(int i = 0 ; i < b.length; i++){ 
			b[i]  =  converter.convert(o[i]).booleanValue(); 
		}
		return b;
	} 
	public Class<String[]> clazz() {
		return STRING_ARRAY_CLASS;
	}
	public Class<boolean[]> returnClazz() {
	 
		return boolean[].class;
	}
}
