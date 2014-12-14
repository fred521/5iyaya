package com.nonfamous.tang.domain.trade;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeOrderItem.java Thu Sep 13 23:20:41 CST 2007 fred $
 */
public class TradeOrderItem extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5140045607052800308L;

	// 字段描述:
	private java.lang.Long id;

	// 字段描述:
	private java.lang.String orderNo;

	// 字段描述:商品id
	private java.lang.String goodsId;

	// 字段描述:店铺id
	private java.lang.String shopId;

	// 字段描述:商品名称
	private java.lang.String goodsName;

	// 字段描述:商品批量或单个单价，单位分
	private java.lang.Long batchPrice;

	// 字段描述:购买商品数量
	private java.lang.Long totalNum;

	// 字段描述:该商品购买金额=(商品数量/ 商品起批数量)*商品批量或单个价格
	private java.lang.Long totalFree;

	// 字段描述:创建人
	private java.lang.String creator;

	// 字段描述:最后修改时间
	private java.util.Date gmtModify;

	// 字段描述:最后修改人
	private java.lang.String modifier;

	// 字段描述:创建时间
	private java.util.Date gmtCreate;
	
	//trade_order 表的订单状态
	private String orderStatus;
	//买家的ID
	private String buyerLoginId;
	//下单时间
	private String orderDate;


	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	private static final boolean nullAbleLongCompare(Long a, Long b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.longValue() == b.intValue();
	}

	private static final String PointeTo = "->";

	/**
	 * 设置item的新属性，并且如果有变化，则需要记录变化的情况，并且返回，如果没有变化，返回null
	 * 
	 * @param newTotalNum
	 * @param newBatchPrice
	 * @param newTotalFree
	 * @return
	 */
	public String getLog(Long newTotalNum, Long newBatchPrice, Long newTotalFree) {
		StringBuilder sb = new StringBuilder("商品:[")
				.append(this.getGoodsName());
		boolean changed = false;
		if (!nullAbleLongCompare(this.totalNum, newTotalNum)) {
			changed = true;
			sb.append(" 数量").append(this.totalNum == null ? 0L : this.totalNum);
			sb.append(PointeTo).append(newTotalNum == null ? 0L : newTotalNum);
			this.totalNum = newTotalNum;
		}
		if (!nullAbleLongCompare(this.batchPrice, newBatchPrice)) {
			changed = true;
			sb.append(" 单价").append(
					this.batchPrice == null ? 0L : getMoney(this.batchPrice).toString());
			sb.append(PointeTo).append(
					newBatchPrice == null ? 0L : getMoney(newBatchPrice).toString());
			this.batchPrice = newBatchPrice;
		}
		if (!nullAbleLongCompare(this.totalFree, newTotalFree)) {
			changed = true;
			sb.append(" 金额").append(
					this.totalFree == null ? 0L : getMoney(this.totalFree));
			sb.append(PointeTo)
					.append(newTotalFree == null ? 0L : getMoney(newTotalFree));
			this.totalFree = newTotalFree;
		}
		if (!changed) {
			return null;
		}
		sb.append(']');
		return sb.toString();
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	public java.lang.String getOrderNo() {
		return this.orderNo;
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

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	public java.lang.String getGoodsName() {
		return this.goodsName;
	}

	public void setBatchPrice(java.lang.Long batchPrice) {
		this.batchPrice = batchPrice;
	}

	public java.lang.Long getBatchPrice() {
		return this.batchPrice;
	}

	public void setTotalNum(java.lang.Long totalNum) {
		this.totalNum = totalNum;
	}

	public java.lang.Long getTotalNum() {
		return this.totalNum;
	}

	public void setTotalFree(java.lang.Long totalFree) {
		this.totalFree = totalFree;
	}

	public java.lang.Long getTotalFree() {
		return this.totalFree;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	public java.lang.String getCreator() {
		return this.creator;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public java.util.Date getGmtModify() {
		return this.gmtModify;
	}

	public void setModifier(java.lang.String modifier) {
		this.modifier = modifier;
	}

	public java.lang.String getModifier() {
		return this.modifier;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}

	public String getBuyerLoginId() {
		return buyerLoginId;
	}

	public void setBuyerLoginId(String buyerLoginId) {
		this.buyerLoginId = buyerLoginId;
	}


}