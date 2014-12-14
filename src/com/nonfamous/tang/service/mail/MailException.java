package com.nonfamous.tang.service.mail;

import java.io.Serializable;

import com.nonfamous.tang.exception.ServiceException;

public class MailException extends ServiceException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public MailException(String msg){
		super(msg);
	}
	
	public MailException(Throwable cause){
		super(cause);
	}
	
	public MailException(String message, Throwable cause){
		super(message, cause);
	}
}
