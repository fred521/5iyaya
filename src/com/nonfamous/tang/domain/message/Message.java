package com.nonfamous.tang.domain.message;

public class Message {
	
	public final static String ERROR = "error";
	
	public final static String WARN = "warn";
	
	public final static String INFO = "info";
	
	private String type;
	
	private String detail;
	
	private String title;
	
	private String debugInfo;
	
	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDebugInfo() {
		return debugInfo;
	}
	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
}
