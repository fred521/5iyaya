package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bocom.netpay.b2cAPI.BOCOMSetting;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.URLConfig;
import com.nonfamous.tang.bank.comm.BankCommData;
import com.nonfamous.tang.bank.comm.BankCommSignHelper;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradePay;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.home.TradePayService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 * 支付action
 * </p>
 * 
 * @author:alan
 * @version $Id: BankPayAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */
public class BankPayAction extends MultiActionController {
	private TradePayService tradePayService;

	private TradeOrderService tradeOrderService;

	private URLConfig appServer;

	private BankCommSignHelper bankcommSignHelper;

	/**
	 * 选择支付银行
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView select_bank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// 用户id
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);

		// 订单id
		String orderId = rvp.getParameter("orderid").getString();

		if (StringUtils.isBlank(memberId) || StringUtils.isBlank(orderId)) {
			throw new ServiceException("订单参数传递错误");
		}
		// 获取订单
		TradeOrder order = tradeOrderService.getTradeOrder(orderId);

		if (order == null) {
			throw new ServiceException("获取订单失败");
		}

		if (!StringUtils.equals(order.getBuyerId(), memberId)) {
			throw new ServiceException("订单支付为非购买本人操作");
		}

		ModelAndView mv = new ModelAndView("/home/bank/selectBank");
		mv.addObject("order", order);
		return mv;
	}

	/**
	 * 根据所选择的银行，产生不同银行所需要的订单支付信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView e_bank_payment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// 用户id
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);

		// 订单id
		String orderId = rvp.getParameter("orderid").getString();

		if (StringUtils.isBlank(memberId) || StringUtils.isBlank(orderId)) {
			throw new ServiceException("订单参数传递错误");
		}
		// 获取订单
		TradeOrder order = tradeOrderService.getTradeOrder(orderId);

		if (order == null) {
			throw new ServiceException("获取订单失败");
		}

		if (!StringUtils.equals(order.getBuyerId(), memberId)) {
			throw new ServiceException("订单支付为非购买本人操作");
		}

		TradePay tradePay = tradePayService.getTradePayByOrderNo(orderId);
		if (tradePay == null) {
			throw new ServiceException("不存在支付流水");
		}
		if (StringUtils.equals(tradePay.getPayStatus(),
				TradePay.PAY_STATUS_SUCCESS)) {
			throw new ServiceException("该订单已经成功支付");
		}

		// TODO:不同的银行有不通的封装方式
		BankCommData commData = new BankCommData(order);
		commData.setMerURL(new StringBuffer().append(appServer.getURL())
				.append("/pay_call_back/e_b_comm_success.htm").toString());
		// commData.setMerURL("http://219.82.150.16:8080/pay_call_back/e_b_comm_success.htm");
		String sign = bankcommSignHelper.sign(commData);
		ModelAndView mv = new ModelAndView("/home/bank/bankPayment");
		mv.addObject("order", order);
		mv.addObject("bankData", commData);
		mv.addObject("orderURL", BOCOMSetting.OrderURL);
		mv.addObject("sign", sign);
		return mv;
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setAppServer(URLConfig appServer) {
		this.appServer = appServer;
	}

	public void setBankcommSignHelper(BankCommSignHelper bankcommSignHelper) {
		this.bankcommSignHelper = bankcommSignHelper;
	}

}
