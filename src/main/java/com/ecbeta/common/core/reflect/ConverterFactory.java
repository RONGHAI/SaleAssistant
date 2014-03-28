package com.ecbeta.common.core.reflect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class ConverterFactory { 
	
	private static Map<String, ParamConverter<?,?>> _CACHE = new HashMap<String, ParamConverter<?,?>>();
	
	private static ParamConverter<?,?>  createConverter(Class<?> newClazz, boolean isArray) {
		if(String.class.equals(newClazz) && isArray){
			return new StringArrayConverter(); 
		}else if((String.class.equals(newClazz) ) && !isArray){
			return new StringConverter();
		} 
		
		
		else if(Boolean.class.equals(newClazz) && isArray){
			return new BooleanArrayConverter();
		}
		else if(Boolean.class.equals(newClazz) &&!isArray){
			return new BooleanConverter();
		} else if(Boolean.TYPE.equals(newClazz) && isArray){
			return new PrimitiveBooleanArrayConverter();
		}else if(Boolean.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveBooleanConverter();
		} 
		 
		else if(Byte.class.equals(newClazz) && isArray){
			return new ByteArrayConverter();
		}else if(Byte.class.equals(newClazz) && isArray){
			return new PrimitiveByteArrayConverter();
		}else if((Byte.class.equals(newClazz) || Byte.TYPE.equals(newClazz) ) && !isArray){
			return new ByteConverter();
		}
		
		else if(Byte.class.equals(newClazz) && isArray){
			return new ByteArrayConverter();
		}
		else if(Byte.class.equals(newClazz) &&!isArray){
			return new ByteConverter();
		} 
		else if(Byte.TYPE.equals(newClazz) && isArray){
			return new PrimitiveByteArrayConverter();
		}
		else if(Byte.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveByteConverter();
		} 
		
		
		else if(Character.class.equals(newClazz) && isArray){
			return new CharacterArrayConverter();
		}
		else if(Character.class.equals(newClazz) &&!isArray){
			return new CharacterConverter();
		} 
		else if(Character.TYPE.equals(newClazz) && isArray){
			return new PrimitiveCharArrayConverter();
		}
		else if(Character.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveCharConverter();
		} 
		
		else if(Double.class.equals(newClazz) && isArray){
			return new DoubleArrayConverter();
		}
		else if(Double.class.equals(newClazz) &&!isArray){
			return new DoubleConverter();
		} 
		else if(Double.TYPE.equals(newClazz) && isArray){
			return new PrimitiveDoubleArrayConverter();
		}
		else if(Double.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveDoubleConverter();
		} 
 
		
		
		else if(Float.class.equals(newClazz) && isArray){
			return new FloatArrayConverter();
		}
		else if(Float.class.equals(newClazz) &&!isArray){
			return new FloatConverter();
		} 
		else if(Float.TYPE.equals(newClazz) && isArray){
			return new PrimitiveFloatArrayConverter();
		}
		else if(Float.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveFloatConverter();
		} 
 
		
		
		else if(Integer.class.equals(newClazz) && isArray){
			return new IntegerArrayConverter();
		}
		else if(Integer.class.equals(newClazz) &&!isArray){
			return new IntegerConverter();
		} 
		else if(Integer.TYPE.equals(newClazz) && isArray){
			return new PrimitiveIntegerArrayConverter();
		}
		else if(Integer.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveIntegerConverter();
		} 
		
		else if(Long.class.equals(newClazz) && isArray){
			return new LongArrayConverter();
		}
		else if(Long.class.equals(newClazz) &&!isArray){
			return new LongConverter();
		} 
		else if(Long.TYPE.equals(newClazz) && isArray){
			return new PrimitiveLongArrayConverter();
		}
		else if(Long.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveLongConverter();
		} 
		
		else if(Short.class.equals(newClazz) && isArray){
			return new ShortArrayConverter();
		}
		else if(Short.class.equals(newClazz) &&!isArray){
			return new ShortConverter();
		} 
		else if(Short.TYPE.equals(newClazz) && isArray){
			return new PrimitiveShortArrayConverter();
		}
		else if(Short.TYPE.equals(newClazz) &&!isArray){
			return new PrimitiveShortConverter();
		} 
		
		 
		
		else if(Date.class.equals(newClazz) && isArray){
			return new DateArrayConverter(); 
		}else if((Date.class.equals(newClazz)   ) && !isArray){
			return new DateConverter();
		}
		
		
		else if(InternetAddress.class.equals(newClazz) && isArray){
			return new EmailArrayConverter(); 
		}else if((InternetAddress.class.equals(newClazz)   ) && !isArray){
			return new EmailConverter();
		}
		return null;
		
	}
	
	public static ParamConverter<?,?>  createConverter(Class<?> clazz) throws ConverterException{
		boolean isArray = clazz.isArray();
		Class<?>  newClazz = clazz;
		String clazzName = clazz.getName();
		if(isArray){
			newClazz = clazz.getComponentType();
		}
		if(!isPrimitive(newClazz)){
			 throw new ConverterException("Unsupport type "+ clazz);
		}
		ParamConverter<?,?> converter = _CACHE.get(clazzName);
		if(converter != null){
			return converter;
		}else{
			converter =  createConverter(newClazz, isArray);
			if(converter == null){
				 throw new ConverterException("Unsupport type "+ clazz);
			}
			_CACHE.put( clazzName , converter);
			return converter;
		} 
	}
	
	public static void main(String[] args){
	//	ParamConverter  m = 	x(String.class);
	}
	
	
	public static boolean isPrimitive(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.equals(String.class) 
				|| clazz.equals(Boolean.class) || clazz.equals(Byte.class)
				|| clazz.equals(Character.class) || clazz.equals(Double.class)
				|| clazz.equals(Float.class) || clazz.equals(Integer.class) 
				|| clazz.equals(Long.class) || clazz.equals(Short.class)  || clazz.equals(Date.class)
				|| clazz.equals(InternetAddress.class) ;
	}
      
}
