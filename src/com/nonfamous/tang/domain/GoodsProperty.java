package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;



public class GoodsProperty extends DomainBase {
	private int id;
	private String value;
	private GoodsPropertyType goodsPropertyType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public GoodsPropertyType getGoodsPropertyType() {
		return goodsPropertyType;
	}
	public void setGoodsPropertyType(GoodsPropertyType goodsPropertyType) {
		this.goodsPropertyType = goodsPropertyType;
	}
	
}
