package com.nonfamous.tang.dao.query;

@SuppressWarnings("serial")
public class CommendTextQuery extends QueryBase{
	
	
	
	   private java.lang.String  commendText;
	   private java.lang.Long    commendPage;
	   private java.lang.String    commendCode;
	   
	   
	 public void setCommendText(java.lang.String commendText) {
	        this.commendText = commendText;
	    }

	    public java.lang.String getCommendText() {
	        return this.commendText;
	    }
	    

	    public java.lang.Long getCommendPage() {
	        return commendPage;
	    }

	    public void setCommendPage(java.lang.Long commendPage) {
	        this.commendPage = commendPage;
	    }

	    public java.lang.String getCommendCode() {
	        return commendCode;
	    }

	    public void setCommendCode(java.lang.String commendCode) {
	        this.commendCode = commendCode;
	    }
}
