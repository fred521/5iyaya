package com.nonfamous.tang.dao.query;

import java.util.Date;
import java.util.List;

import com.nonfamous.tang.domain.Shop;

/**
 * 
 * @author victor
 * 
 */
public class SearchMemberShopQuery extends SearchQueryBase {
	private static final long serialVersionUID = 2254818574753999780L;

	public static final String MEMBER = "shopOwner";

	public static final String SHOP = "shopName";

	private String searchType;// 搜索类型(MEMBER，SHOP)

	private String marketType;// 所属市场分类

	private List<Shop> shopList;// 返回搜索结果

	private Date begin;

	private Date end;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

}
