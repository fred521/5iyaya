package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.bank.BankNameEnum;
import com.nonfamous.tang.bank.comm.BankCommReturnData;
import com.nonfamous.tang.bank.comm.BankCommSignHelper;
import com.nonfamous.tang.domain.trade.TradePay;
import com.nonfamous.tang.service.home.TradePayService;

/**
 * <p>
 * 支付action
 * </p>
 * 
 * @author:alan
 * @version $Id: BankPayReturnAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */
public class BankPayReturnAction extends MultiActionController {
	private static Log logger = LogFactory.getLog(BankPayReturnAction.class);

	private TradePayService tradePayService;

	private BankCommSignHelper bankcommSignHelper;

	/**
	 * 交通银行订单支付银行回调方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView e_b_comm_success(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			RequestValueParse rvp = new RequestValueParse(request);

			// 银行返回字符串
			String notifyMsg = rvp.getParameter("notifyMsg").getString();

			if (logger.isDebugEnabled()) {
				logger.debug("银行返回字符串:" + notifyMsg);
			}

			if (StringUtils.isBlank(notifyMsg)) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500错误
				return null;
			}

			BankCommReturnData returnData = new BankCommReturnData(notifyMsg);
			// 校验返回信息
			if (!bankcommSignHelper.verify(returnData)) {
				logger.debug("银行返回字符串校验未通过");
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500错误
				return null;
			}

			// 获取支付流水
			TradePay tradePay = tradePayService.getTradePayByOrderNo(returnData
					.getOrderNo());

			if (tradePay == null) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500错误
				logger.error("根据订单号找不到指定的订单信息：订单号＝" + returnData.getOrderNo());
				return null;
			}
			if (StringUtils.equals(tradePay.getPayStatus(),
					TradePay.PAY_STATUS_SUCCESS)) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500错误
				logger.error("该订单已经成功支付：订单号＝" + returnData.getOrderNo());
				return null;
			}

			tradePayService.changePayStatus(tradePay.getId(),
					TradePay.PAY_STATUS_SUCCESS, "8888",
					BankNameEnum.Bank_Comm, returnData.getSerialNo());

		} catch (Exception e) {
			logger.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500错误
			return null;
		}
		
		ModelAndView mv = new ModelAndView("/home/bank/paySuccess");
		return mv;
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public void setBankcommSignHelper(BankCommSignHelper bankcommSignHelper) {
		this.bankcommSignHelper = bankcommSignHelper;
	}

}
