package com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * 订单支付服务接口
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayService.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public interface TradePayService {
	/**
	 * 订单支付
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public PayResult orderPay(String tradeOrderNo, String memberId);
	
	/**
	 * 支付工具支付
	 * @param tradeOrderNo
	 * @param memberId
	 * @return
	 */
	public PayResult toolsPay(String tradeOrderNo, String memberId);

	/**
	 * 快速支付
	 * 
	 * @param order
	 * @param note
	 * @param operator
	 * @return
	 */
	public PayResult quickPay(TradeOrder order, String note);

	/**
	 * 获取订单列表
	 * 
	 * @param memberId
	 * @return
	 */
	public TradePayQuery queryTradePayList(TradePayQuery query);

	/**
	 * 修改支付状态
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 * @param payBank  支付银行
	 * @param serialNo 银行返回流水号
	 */
	public PayResult changePayStatus(Long payId, String payStatus,
			String modifier,String payBank,String serialNo);

	/**
	 * 修改转账状态（目前为后台使用，不做任何限制）
	 * 
	 * @param payId
	 * @param transStatus
	 * @param modifier
	 */
	public PayResult changeTransStatus(Long payId, String transStatus,
			String modifier);
	
	/**
	 * 根据订单号获取支付流水
	 * @param orderNo
	 * @return TradePay
	 */
	public TradePay getTradePayByOrderNo(String orderNo);
	
	
}
