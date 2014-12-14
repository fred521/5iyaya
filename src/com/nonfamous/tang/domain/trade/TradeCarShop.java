package com.nonfamous.tang.domain.trade;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeCarShop.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradeCarShop extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7409935378120364979L;

	// ◊÷∂Œ√Ë ˆ:µÍ∆Ãid
	private java.lang.String shopId;

	// ◊÷∂Œ√Ë ˆ:¥¥Ω®»Àid
	private java.lang.String owner;

	public void setShopId(java.lang.String shopId) {
		this.shopId = shopId;
	}

	public java.lang.String getShopId() {
		return this.shopId;
	}

	public void setOwner(java.lang.String owner) {
		this.owner = owner;
	}

	public java.lang.String getOwner() {
		return this.owner;
	}

}