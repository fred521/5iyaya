package com.nonfamous.tang.domain;

/**
 * <p>
 * 后台列表显示用的会员信息，包含会员信息和店铺信息，只是单独为列表使用的
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberList.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class MemberList extends Member {

	private static final long serialVersionUID = -67292242130229139L;
	private Shop shop;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
