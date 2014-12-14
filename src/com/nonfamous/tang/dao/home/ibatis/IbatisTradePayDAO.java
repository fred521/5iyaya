package com.nonfamous.tang.dao.home.ibatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.TradePayDAO;
import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * 银行支付订单实现类
 * </p>
 * 
 * @author:daodao
 * @version $Id: IbatisTradePayDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisTradePayDAO extends SqlMapClientDaoSupport implements
		TradePayDAO {
	public static final String SPACE = "TRADEPAY_SPACE.";

	public Long insertTradePay(TradePay tradePay) {
		if (tradePay == null) {
			throw new NullPointerException("tradePay can't be null");
		}
		return (Long) this.getSqlMapClientTemplate().insert(
				SPACE + "TRADEPAY_INSERT", tradePay);
	}

	@SuppressWarnings("unchecked")
	public TradePayQuery findTradePayList(TradePayQuery query) {
		if (query == null) {
			throw new NullPointerException("TradePayQuery can't be null");
		}
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "QUERY_TRADEPAY_COUNT", query);
		query.setTotalItem(total);
		if (total == 0) {
			query.setTradePayList(Collections.EMPTY_LIST);
			return query;
		}
		List<TradePay> find = this.getSqlMapClientTemplate().queryForList(
				SPACE + "QUERY_TRADEPAY_LIST", query);
		query.setTradePayList(find);
		return query;
	}

	public void updatePayStatus(Long payId, String status, String modifier,
			String payBank,String serialNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("payStatus", status);
		param.put("modifier", modifier);
		param.put("id", payId);
		param.put("payBank", payBank);
		param.put("serialNo", serialNo);
		this.getSqlMapClientTemplate().update(
				SPACE + "TRADEPAY_UPDATE_PAY_STATUS_BY_ID", param);
	}

	public void updateTransStatus(Long payId, String status, String modifier) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("transStatus", status);
		param.put("modifier", modifier);
		param.put("id", payId);
		this.getSqlMapClientTemplate().update(
				SPACE + "TRADEPAY_UPDATE_TRANS_STATUS_BY_ID", param);

	}

	public TradePay findTradePayById(Long payId) {
		if (payId == null) {
			throw new NullPointerException("payId can't be null");
		}
		return (TradePay) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_TRADEPAY_BY_ID", payId);
	}

	public TradePay findTradePayByOrderNo(String tradeOrderNo) {
		if (tradeOrderNo == null) {
			throw new NullPointerException("tradeOrderNo can't be null");
		}
		return (TradePay) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_TRADEPAY_BY_TRADE_ORDER_NO", tradeOrderNo);
	}

	public void changePayDate(Long id, String modifier) {
		if (id == null || modifier == null) {
			throw new NullPointerException("id or modifier can't be null");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("modifier", modifier);
		param.put("id", id);
		this.getSqlMapClientTemplate().update(
				SPACE + "TRADEPAY_UPDATE_PAY_DATE_BY_ID", param);
	}
}
