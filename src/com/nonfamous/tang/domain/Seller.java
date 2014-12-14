package com.nonfamous.tang.domain;

/**
 * <p>
 * 卖家信息，卖家是会员的一种
 * </p>
 * 
 * @author:daodao
 * @version $Id: Seller.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class Seller extends Member {

	private static final long serialVersionUID = -1280413677649296117L;
	// 卖家的店铺，这是基于卖家一定会有店铺的业务关系来设计的
	private Shop shop;

	public Seller() {
		this.setMemberType(FLAG_SELLER);
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
