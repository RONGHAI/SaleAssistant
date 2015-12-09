package com.weinyc.sa.core.reflect;

/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class PrimitiveDoubleArrayConverter  implements ParamConverter<double[],String[]>{

 

	public double[] convert(String[] o) throws ConverterException {
		if(o == null) return null;
		double[] b = new double[o.length];
		PrimitiveDoubleConverter converter = new PrimitiveDoubleConverter();
		for(int i = 0 ; i < b.length; i++){ 
			b[i]  =  converter.convert(o[i]).doubleValue(); 
		}
		return b;
	}
	public Class<String[]> clazz() {
		return STRING_ARRAY_CLASS;
	}
	public Class<double[]> returnClazz() { 
		return double[].class;
	}
}
