package com.nonfamous.tang.dao.query;


public class HelperQuery extends QueryBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4313136504546361104L;

	private String helperId;

	private String helperTitle;
	
	private String helperType;
	
	private String memberId;

	private String helperStatus;
	
	private String creator;
	
	public String getHelperStatus() {
		return helperStatus;
	}

	public void setHelperStatus(String helperStatus) {
		this.helperStatus = helperStatus;
	}


	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getHelperId() {
		return helperId;
	}

	public void setHelperId(String helperId) {
		this.helperId = helperId;
	}
	public String getHelperTitle() {
		return helperTitle;
	}

	public void setHelperTitle(String helperTitle) {
		this.helperTitle = helperTitle;
	}

	public String getHelperType() {
		return helperType;
	}

	public void setHelperType(String helperType) {
		this.helperType = helperType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
