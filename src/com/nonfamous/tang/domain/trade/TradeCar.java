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

	// ×Ö¶ÎÃèÊö:»áÔ±±àºÅ
	private java.lang.Long id;

	// ×Ö¶ÎÃèÊö:ÉÌÆ·id
	private java.lang.String goodsId;

	// ×Ö¶ÎÃèÊö:µêÆÌid
	private java.lang.String shopId;

	// ×Ö¶ÎÃèÊö:´´½¨ÈËid
	private java.lang.String owner;

	// ×Ö¶ÎÃèÊö:´´½¨Ê±¼ä
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