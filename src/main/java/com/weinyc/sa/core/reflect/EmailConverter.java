package com.weinyc.sa.core.reflect;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
/**
 * Don't use this file. Ronghai may remove it.
 * @author Ronghai
 * Nov 21, 2012 3:30:39 PM
 * If you wanna change this file, please let me know and send modify information to me (Ronghai.Wei@Lake5Media.com)
 * Keep code clean and remove unused code.
 * @param <T>
 */
public class EmailConverter extends StringParamConverter<InternetAddress> { 
	
 	
	
	public InternetAddress convert(String o) throws ConverterException {
		 if( o == null ) return null; 
		o = trim(o);
		 try {
				InternetAddress emailAddr = new InternetAddress(o);
				emailAddr.validate();
				return emailAddr;
			} catch (AddressException ex) { 
				throw new ConverterException(ex);
			}
 	}

	public Class<InternetAddress> returnClazz() { 
		return InternetAddress.class;
	}


}
