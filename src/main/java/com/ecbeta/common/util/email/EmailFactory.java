package com.ecbeta.common.util.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;

public class  EmailFactory {
	
	
	public static final String SUPORT_SENDER  = "SUPPORT@Lake5Media.com";
	public static final String NMM_SENDER  = SUPORT_SENDER;
	public static final String MTVN_SENDER  = "MTVN.Support@Lake5Media.com";
	public static void sendEmail(String value, String subject ) throws Exception{
        HtmlEmail htmlEmail = EmailFactory.createHtmlEmail("ronghai.wei@lake5media.com");
        htmlEmail.addTo("ronghai.wei@lake5media.com");
        htmlEmail.setSubject(subject);
        htmlEmail.setTextMessage(value);
        htmlEmail.setHtmlMessage(value);
        htmlEmail.send();
    }
     
    
	public static HtmlEmail createHtmlEmail(String from){
		HtmlEmail htmlEmail = new HtmlEmail();
		
		/*String mailHost = AppConfig.getProperty("L5M_MAILHOST");
		
		if(mailHost == null){
			mailHost = "172.17.61.90"; 
		}
		if(mailHost.equals("172.17.61.90")){
			htmlEmail.setSmtpPort(587);
		}
		 
		htmlEmail.setMailHost(mailHost);*/
 
		
		try {
			htmlEmail.setFrom( from );
		} catch (AddressException e) { 
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
		htmlEmail.setCharset("UTF-8");
		htmlEmail.addHeader( "X-Mailer" , from );  
		return htmlEmail;
	}
 
	public static void main(String are[]){
		 HtmlEmail htmlEmail =  createHtmlEmail("");
		 
		 StringBuilder sb = new StringBuilder();
		 
		 sb.append("<html> ");
		 
		 try {
			htmlEmail.addTo("Wei.Ronghai@qinghetech.com");
		} catch (AddressException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 
		 try {
			sb.append("<body><div class='red'><img src=\"data:image/png;base64,"+HtmlHelper.encode("C:/Red-dot-5px.png")+"\" /> </div></body></html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 htmlEmail.setHtmlMessage(sb.toString());
		 htmlEmail.setSubject("New Version");
		 try {
			htmlEmail.send();
		 } catch (Exception e1) { 
			e1.printStackTrace();
		 } 
		
	   // EmailSender sender = new EmailSender();
	//	sender.send("Wei.Ronghai@qinghetech.com", null , null, "Older Version ", "<html><body><font color='red'>This is a test mail</font></body></html>"); 
	}
	
	
	
}
