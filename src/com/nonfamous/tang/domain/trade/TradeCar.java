package com.nonfamous.tang.domain.trade;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeCar.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradeCar extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1613985899758297870L;

	// �ֶ�����:��Ա���
	private java.lang.Long id;

	// �ֶ�����:��Ʒid
	private java.lang.String goodsId;

	// �ֶ�����:����id
	private java.lang.String shopId;

	// �ֶ�����:������id
	private java.lang.String owner;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}

	public java.lang.String getGoodsId() {
		return this.goodsId;
	}

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

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}

}