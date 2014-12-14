package com.nonfamous.tang.domain;

/**
 * 
 * @author victor
 * 
 */
public class SearchGoodsResult extends GoodsBaseInfo {

	private static final long serialVersionUID = -95486029709853150L;

	private String shopName;

	private String address;

	private String marketName;

	private String marketType;

	private String batchPriceYuan;
	
	private String marketPriceYuan;
	
	private Integer starNum;
		
	
	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

	private String gisAddress;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBatchPriceYuan() {
		return batchPriceYuan;
	}

	public void setBatchPriceYuan(String batchPrice) {
		this.batchPriceYuan = batchPrice;
	}

	public String getGisAddress() {
		return gisAddress;
	}

	public void setGisAddress(String gisAddress) {
		this.gisAddress = gisAddress;
	}

	public String getMarketPriceYuan() {
		return marketPriceYuan;
	}

	public void setMarketPriceYuan(String marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}

}
