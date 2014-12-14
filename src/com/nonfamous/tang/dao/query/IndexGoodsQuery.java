package com.nonfamous.tang.dao.query;

import java.util.Date;

/**
 * 
 * @author fred
 * 
 */
public class IndexGoodsQuery extends QueryBase {

	private static final long serialVersionUID = -5760609218134704814L;
	
	private Date begin;

	private Date end;
	
	private String goodsId;
	
	private Integer starNum;

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

	private String shopId;

	private String memberId;

	private String goodsName;

	private String goodsPic;

	private Date gmtAbandon;
	
	private String goodsStatus;
	
	private Date gmtCreate;
	
	private String goodsCat;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Date getGmtAbandon() {
		return gmtAbandon;
	}

	public void setGmtAbandon(Date gmtAbandon) {
		this.gmtAbandon = gmtAbandon;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getGoodsCat() {
		return goodsCat;
	}

	public void setGoodsCat(String goodsCat) {
		this.goodsCat = goodsCat;
	}

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
}
