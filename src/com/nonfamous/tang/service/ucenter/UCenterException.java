package com.nonfamous.tang.service.ucenter;

public class UCenterException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code; 
	
	public UCenterException(){}
	
	public UCenterException(int code){
		this.code = code;
	}
	
	public UCenterException(String msg){
		super(msg);
	}
	
	public UCenterException(String msg, int code){
		super(msg);
		this.code = code;
	}
	
	public UCenterException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	public UCenterException(String msg, Throwable cause, int code){
		super(msg, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
