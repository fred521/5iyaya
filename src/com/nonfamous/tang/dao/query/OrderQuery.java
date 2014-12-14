package com.nonfamous.tang.dao.query;

import java.util.List;

import com.nonfamous.tang.domain.trade.TradeOrder;

/**
 * 
 * @author fish
 * 
 */
public class OrderQuery extends QueryBase {

	private static final long serialVersionUID = 8039367469194806754L;

	public static final int SearchStatusAll = 0;// 所有订单

	public static final int SearchStatusBothConfirm = 1;// 双方已确认

	public static final int SearchStatusWaitHimConfirm = 2;// 等待对方确认

	public static final int SearchStatusWaitMeConfirm = 3;// 等待我方确认

	public static final int SearchStatusCloed = 4;// 订单已作废

	private String memberId;

	private String type;

	/**
	 * 搜索状态,指页面上的搜索条件 所有订单 双方已确认 等待对方确认 等待我方确认 订单已作废
	 */
	private int searchStatus;

	private List<TradeOrder> orders;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public List<TradeOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<TradeOrder> orders) {
		this.orders = orders;
	}

	public String getType() {
		return type;
	}

	public void setType(String searchType) {
		this.type = searchType;
	}

	public int getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(int searchStatus) {
		this.searchStatus = searchStatus;
	}
}
