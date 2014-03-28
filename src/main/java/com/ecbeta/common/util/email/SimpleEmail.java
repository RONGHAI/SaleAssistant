package com.ecbeta.common.util.email;

public class SimpleEmail extends Email {
	@Override
	public Email setMessage(String msg) 
    {
        if (EmailUtils.isEmpty(msg))
        {
        	msg  = "";
        } 
        setContent(msg, TEXT_PLAIN);
        return this;
    }
}
