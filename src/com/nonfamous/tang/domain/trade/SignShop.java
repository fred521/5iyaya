package com.nonfamous.tang.domain.trade;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdSignShop.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class SignShop extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1809114399664206806L;

	// ×Ö¶ÎÃèÊö:±àºÅ
	private java.lang.Long id;

	// ×Ö¶ÎÃèÊö:»áÔ±±àºÅ
	private java.lang.String shopId;

	// ×Ö¶ÎÃèÊö:»áÔ±id
	private java.lang.String memberId;

	public void setShopId(java.lang.String shopId) {
		this.shopId = shopId;
	}

	public java.lang.String getShopId() {
		return this.shopId;
	}

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}

	public java.lang.String getMemberId() {
		return this.memberId;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

}