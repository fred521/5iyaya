package com.nonfamous.tang.domain;

/**
 * <p>
 * ������Ϣ�������ǻ�Ա��һ��
 * </p>
 * 
 * @author:daodao
 * @version $Id: Seller.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class Seller extends Member {

	private static final long serialVersionUID = -1280413677649296117L;
	// ���ҵĵ��̣����ǻ�������һ�����е��̵�ҵ���ϵ����Ƶ�
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
