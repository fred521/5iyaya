package com.nonfamous.tang.dao.query;

import java.util.List;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * ����֧������Query��
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayQuery.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */
public class TradePayQuery extends QueryBase {

	private static final long serialVersionUID = 5511861138314044323L;

	/** ����֧�������б� */
	private List<TradePay> tradePayList;

	/** ��ʼʱ�� */
	private String startDate;

	/** ����ʱ�� */
	private String endDate;

	/** ��ҵ�¼id */
	private String buyLoginId;

	/** ���ҵ�¼id */
	private String sellerLoginId;

	/** ���id */
	private String buyId;

	/** ����id */
	private String sellerId;

	public String getBuyId() {
		return buyId;
	}

	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}

	public String getBuyLoginId() {
		return buyLoginId;
	}

	public void setBuyLoginId(String buyLoginId) {
		this.buyLoginId = buyLoginId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSellerLoginId() {
		return sellerLoginId;
	}

	public void setSellerLoginId(String sellerLoginId) {
		this.sellerLoginId = sellerLoginId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<TradePay> getTradePayList() {
		return tradePayList;
	}

	public void setTradePayList(List<TradePay> tradePayList) {
		this.tradePayList = tradePayList;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getAddEndDate() {
		if (endDate == null) {
			return null;
		}
		return DateUtils.dtSimpleFormat(getAddDate(DateUtils
				.string2Date(endDate)));
	}

	public boolean hasSearchCondition() {
		return (startDate != null || endDate != null || buyLoginId != null
				|| sellerLoginId != null || buyId != null || sellerId != null);
	}
}
