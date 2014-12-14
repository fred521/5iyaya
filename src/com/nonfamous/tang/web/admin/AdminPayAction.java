package com.nonfamous.tang.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradePay;
import com.nonfamous.tang.service.home.TradePayService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 * 后台支付管理
 * </p>
 * 
 * @author:daodao
 * @version $Id: AdminPayAction.java,v 1.1 2008/07/11 00:47:07 fred Exp $
 */
public class AdminPayAction extends MultiActionController {
	private DefaultFormFactory formFactory;

	private TradePayService tradePayService;

	/**
	 * 支付列表
	 * 
	 * @param request
	 * @param response
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public ModelAndView pay_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Form form = formFactory.getForm("searchPay", request);
		request.setAttribute("form", form);
		ModelAndView mv = new ModelAndView("admin/adminTrade/tradePayList");

		RequestValueParse rvp = new RequestValueParse(request);
		// 当前页
		String currentPage = rvp.getParameter("currentPage").getString();
		TradePayQuery query = new TradePayQuery();
		// 设置query的值
		formFactory.formCopy(form, query);
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));
		}

		query = tradePayService.queryTradePayList(query);
		mv.addObject("search", query);
		return mv;
	}

	/**
	 * 修改支付状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_pay_status(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String operator = cookyjar.get(Constants.AdminUserId_Cookie);
		String payId = rvp.getParameter("payId").getString();
		ModelAndView mv = new ModelAndView(
				"forward:admin/admin_pay/pay_list.htm");

		PayResult result = tradePayService.changePayStatus(new Long(payId),
				TradePay.PAY_STATUS_SUCCESS, operator,"INNER","0");//内部调整
		if (!result.isSuccess()) {
			mv.addObject("errorMessage", result.getErrorMessage());
		}
		return mv;
	}

	/**
	 * 修改转账状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_trans_status(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String operator = cookyjar.get(Constants.AdminUserId_Cookie);
		String payId = rvp.getParameter("payId").getString();
		ModelAndView mv = new ModelAndView(
				"forward:admin/admin_pay/pay_list.htm");

		PayResult result = tradePayService.changeTransStatus(new Long(payId),
				TradePay.TRANS_STATUS_SUCCESS, operator);
		if (!result.isSuccess()) {
			mv.addObject("errorMessage", result.getErrorMessage());
		}
		return mv;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}
}
