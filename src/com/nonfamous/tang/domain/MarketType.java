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

	// �ֶ�����:�г����� 01��02
	private java.lang.String marketType;

	// �ֶ�����:�г�����
	private java.lang.String marketName;

	// �ֶ�����:�г���ά��ַ
	private java.lang.String marketGis;

	// �ֶ�����:�г���ַ
	private java.lang.String marketAddress;

	// ��ʾ˳��
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