package com.nonfamous.tang.domain;

/**
 * <p>
 * �����Ϣ������ǻ�Ա��һ��
 * </p>
 * 
 * @author:daodao
 * @version $Id: Buyer.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class Buyer extends Member {

	private static final long serialVersionUID = -97559080249201299L;

	public Buyer() {
		this.setMemberType(FLAG_BUYER);
	}
}
