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

	public static final int SearchStatusAll = 0;// ���ж���

	public static final int SearchStatusBothConfirm = 1;// ˫����ȷ��

	public static final int SearchStatusWaitHimConfirm = 2;// �ȴ��Է�ȷ��

	public static final int SearchStatusWaitMeConfirm = 3;// �ȴ��ҷ�ȷ��

	public static final int SearchStatusCloed = 4;// ����������

	private String memberId;

	private String type;

	/**
	 * ����״̬,ָҳ���ϵ��������� ���ж��� ˫����ȷ�� �ȴ��Է�ȷ�� �ȴ��ҷ�ȷ�� ����������
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
