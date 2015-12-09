package com.weinyc.sa.common.util.email;

import java.util.concurrent.Callable;

public class HtmlEmailTask implements Callable<Boolean>{ 
	HtmlEmail htmlEmail = null;
	public HtmlEmailTask(HtmlEmail htmlEmail){
		this.htmlEmail = htmlEmail;
	}
	public Boolean call() throws Exception {
		try{
			htmlEmail.send();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		 
	}
	
}