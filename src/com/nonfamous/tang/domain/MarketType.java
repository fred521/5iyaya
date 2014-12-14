package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: MarketType.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class MarketType extends DomainBase {

	private static final long serialVersionUID = -7745104471188082129L;

	// 字段描述:市场分类 01、02
	private java.lang.String marketType;

	// 字段描述:市场名称
	private java.lang.String marketName;

	// 字段描述:市场三维地址
	private java.lang.String marketGis;

	// 字段描述:市场地址
	private java.lang.String marketAddress;

	// 显示顺序
	private Integer showOrder;

	public void setMarketType(java.lang.String marketType) {
		this.marketType = marketType;
	}

	public java.lang.String getMarketType() {
		return this.marketType;
	}

	public void setMarketName(java.lang.String marketName) {
		this.marketName = marketName;
	}

	public java.lang.String getMarketName() {
		return this.marketName;
	}

	public void setMarketGis(java.lang.String marketGis) {
		this.marketGis = marketGis;
	}

	public java.lang.String getMarketGis() {
		return this.marketGis;
	}

	public void setMarketAddress(java.lang.String marketAddress) {
		this.marketAddress = marketAddress;
	}

	public java.lang.String getMarketAddress() {
		return this.marketAddress;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

}