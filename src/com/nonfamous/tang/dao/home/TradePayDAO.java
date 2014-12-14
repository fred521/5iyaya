package com.nonfamous.tang.dao.home;

import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * 银行支付订单DAO接口
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface TradePayDAO {
	/**
	 * 新增支付订单
	 * 
	 * @param tradePay
	 * @return
	 */
	public Long insertTradePay(TradePay tradePay);

	/**
	 * 查找支付订单
	 * 
	 * @param query
	 * @return
	 */
	public TradePayQuery findTradePayList(TradePayQuery query);

	/**
	 * 获取支付订单
	 * 
	 * @param payId
	 * @return
	 */
	public TradePay findTradePayById(Long payId);

	/**
	 * 修改支付状态
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 * @param payBank  支付银行
	 * @param serialNo 银行返回流水号
	 */
	public void updatePayStatus(Long payId, String status, String modifier,String payBank,String serialNo);

	/**
	 * 修改转账状态
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 */
	public void updateTransStatus(Long payId, String status, String modifier);

	/**
	 * 根据交易订单号获取支付信息
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public TradePay findTradePayByOrderNo(String tradeOrderNo);

	/**
	 * 修改支付时间
	 * 
	 * @param id
	 * @param memberId
	 */
	public void changePayDate(Long id, String modifier);
}
