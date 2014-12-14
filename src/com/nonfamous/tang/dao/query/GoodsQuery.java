package com.nonfamous.tang.dao.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.GoodsBaseInfo;

public class GoodsQuery extends QueryBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4313136504546361104L;

	private String goodsName;

	private String goodsCat;

	private String catPath;

	private String name;

	private String nick;

	private String mobile;

	private String memberId;

	private String shopId;

	private String shopOwner;

	private String shopName;

	private String loginId;

	private Set<String> goodsStatusSet = new HashSet<String>();
	
	private Set<String> goodsTypeSet = new HashSet<String>();

	List<GoodsBaseInfo> goods;

	private String orderBy;

	public List<GoodsBaseInfo> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsBaseInfo> goods) {
		this.goods = goods;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGoodsStatus() {
		if (this.goodsStatusSet == null || this.goodsStatusSet.isEmpty()) {
			return null;
		}
		return this.goodsStatusSet.iterator().next();
	}

	public void setGoodsStatus(String goodsStatus) {
		if (goodsStatus == null) {
			return;
		}
		this.goodsStatusSet.add(goodsStatus);
	}

	public String getGoodsType() {
		if (this.goodsTypeSet == null || this.goodsTypeSet.isEmpty()) {
			return null;
		}
		return this.goodsTypeSet.iterator().next();
	}

	public void setGoodsType(String goodsType) {
		if (goodsType == null) {
			return;
		}
		this.goodsTypeSet.add(goodsType);
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCatPath() {
		return catPath;
	}

	public void setCatPath(String catPath) {
		this.catPath = catPath;
	}

	public String getGoodsCat() {
		return goodsCat;
	}

	public void setGoodsCat(String goodsCat) {
		this.goodsCat = goodsCat;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Set<String> getGoodsStatusSet() {
		return goodsStatusSet;
	}

	public void setGoodsStatusSet(Set<String> goodsStatusSet) {
		this.goodsStatusSet = goodsStatusSet;
	}

	public Set<String> getGoodsTypeSet() {
		return goodsTypeSet;
	}

	public void setGoodsTypeSet(Set<String> goodsTypeSet) {
		this.goodsTypeSet = goodsTypeSet;
	}

	public List<String> getGoodsStatusList() {
		if (this.goodsStatusSet == null) {
			return null;
		}
		return new ArrayList<String>(goodsStatusSet);
	}

	public List<String> getGoodsTypeList() {
		if (this.goodsTypeSet == null) {
			return null;
		}
		return new ArrayList<String>(goodsTypeSet);
	}
	
	public String getShopOwner() {
		return shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getMemberSearchCondition() {
		if (StringUtils.isNotBlank(loginId) || StringUtils.isNotBlank(nick)
				|| StringUtils.isNotBlank(mobile)) {
			return "member";
		}
		return null;
	}

	public String getShopSearchCondition() {
		if (StringUtils.isNotBlank(shopOwner)) {
			return "shop";
		}
		return null;
	}

}
