package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * 银行支付结果返回
 * </p>
 * 
 * @author:daodao
 * @version $Id: PayResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class PayResult extends ResultBase {

	private static final long serialVersionUID = -4804101301915154246L;

	/** 错误的交易订单状态，不允许进行支付 */
	public static final String ERROR_TRADE_ORDER_STATUS = "ERROR_TRADE_ORDER_STATUS";

	/** 支付已经成功，不能再次进行支付 */
	public static final String ERROR_PAY_HAS_CREATED = "ERROR_PAY_HAS_CREATED";

	/** 没有卖家银行账号，不允许进行支付 */
	public static final String ERROR_NO_SELLER_ACCOUNT = "ERROR_NO_SELLER_ACCOUNT";

	/** 无法找到相应卖家信息，不允许进行支付 */
	public static final String ERROR_NO_SELLER_INFO = "ERROR_NO_SELLER_INFO";

	/** 不允许向自己支付 */
	public static final String ERROR_SAME_BUYER_SELLER = "ERROR_SAME_BUYER_SELLER";

	// 修改支付状态相关
	/** 错误的支付状态，不允许作该状态修改 */
	public static final String ERROR_PAY_STATUS = "ERROR_PAY_STATUS";

	/** 错误的订单状态，不允许修改支付状态 */
	public static final String ERROR_ORDER_STATUS = "ERROR_ORDER_STATUS";

	private TradePay tradePay;

	/**
	 * 获取出错信息
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_TRADE_ORDER_STATUS)) {
			return "错误的交易订单状态，不允许进行支付!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_PAY_HAS_CREATED)) {
			return "支付已经成功，不能再次进行支付!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_SELLER_ACCOUNT)) {
			return "卖家银行账号不存在，不允许进行支付!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_SELLER_INFO)) {
			return "无法找到相应卖家信息，不允许进行支付!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_SAME_BUYER_SELLER)) {
			return "不允许向自己支付!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_PAY_STATUS)) {
			return "错误的支付状态，不允许作该状态修改!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_ORDER_STATUS)) {
			return "错误的订单状态，不允许作该状态修改 !";
		}
		return super.getErrorMessage();
	}

	public TradePay getTradePay() {
		return tradePay;
	}

	public void setTradePay(TradePay tradePay) {
		this.tradePay = tradePay;
	}

}
