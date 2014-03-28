package com.ecbeta.common.core.reflect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class DateConverter extends StringParamConverter<Date> { 
	
	public final static String FORMATS[] = new String[]{"MM/dd/yy", "MM/dd/yyyy", "dd-MMM-yy" , "dd-MMM-yyyy"  , "dd-MM-yy" , "dd-MM-yyyy" , "yyyy.MM.dd.HH.mm.ss" , "E, dd MMM yyyy HH:mm:ss Z" };
	
	
	public Date convert(String o) throws ConverterException {
		 if( o == null ) return null; 
		o = trim(o);
		 Date date =  null;
		 for(String format : FORMATS ){
			 try{
				 DateFormat formatter = new SimpleDateFormat(format);
				 date = (Date)formatter.parse(o);
				 break;
			 }catch(ParseException e){
				 
			 } 
		 }
		System.out.println( date);
		if(date != null)
			return date; 
		throw new  ConverterException(" Can't convert " + o +" to java.util.Date. Only supports the following formats: " + java.util.Arrays.toString(FORMATS ));
 	}


	public Class<Date> returnClazz() {
		return Date.class;
	}
	
	public static void main(String []s ) throws ConverterException{
		System.out.println( new DateConverter().convert("16-DEC-2011"));
	}

}
