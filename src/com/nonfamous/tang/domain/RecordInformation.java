package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

@SuppressWarnings("serial")
public class RecordInformation extends DomainBase {

	private String  userId ;
	private Long length ;
	private boolean isValidated = false ;
	
	public void setValidated( boolean b ){
		isValidated = b ;
	}
	
	public boolean isValidated( ){
		return isValidated ;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	
	
}
