package com.ecbeta.common.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class PrimitiveByteArrayConverter  implements ParamConverter<byte[],String[]> { 
	public byte[] convert(String[] o) throws ConverterException {
		if(o == null) return null;
		byte[] b = new byte[o.length];
		PrimitiveByteConverter converter = new PrimitiveByteConverter();
		for(int i = 0 ; i < b.length; i++){ 
			b[i] =  converter.convert(o[i]).byteValue(); 
		}
		return b;
	} 
	public Class<String[]> clazz() {
		return STRING_ARRAY_CLASS;
	}
	public Class<byte[]> returnClazz() { 
		return byte[].class;
	}


}
