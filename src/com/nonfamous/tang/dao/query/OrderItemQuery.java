package com.nonfamous.tang.dao.query;

import java.util.List;

import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;

/**
 * 
 * @author fish
 * 
 */
public class OrderItemQuery extends QueryBase {

	private static final long serialVersionUID = 2591999392916452841L;

	private String goodsId;
	
	private String memberId;



	private List<TradeOrderItem> orderItems;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public List<TradeOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TradeOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	


}
