package com.nonfamous.tang.domain;

/**
 * <p>
 * ��̨�б���ʾ�õĻ�Ա��Ϣ��������Ա��Ϣ�͵�����Ϣ��ֻ�ǵ���Ϊ�б�ʹ�õ�
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
