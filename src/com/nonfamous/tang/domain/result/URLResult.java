package com.nonfamous.tang.domain.result;

/**
 * <p>
 * Include all the information related to the SupplierURL result
 * </p>
 * 
 * @author:Jason
 */
public class URLResult extends ResultBase {

	private static final long serialVersionUID = 7623393416413453518L;
	
	private String urlId;
	
	
	/***************************************Result for updating SupplierURL*************************************/
	
	public String getURLId() {
		return urlId;
	}

	public void setURLId(String urlId) {
		this.urlId = urlId;
	}
	
}
