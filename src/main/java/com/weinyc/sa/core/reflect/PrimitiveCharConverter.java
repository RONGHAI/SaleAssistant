package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class PrimitiveCharConverter  extends StringParamConverter<Character> { 
	public Character convert(String o) throws ConverterException {
		if(o == null) return 0;
		 char charValue = 0;
         if (o.length() > 0) {
             charValue = o.charAt(0);
         }
         return  charValue;  
	}

	public Class<Character> returnClazz() {
		 return Character.TYPE;
	} 
	
	
 
}
 
