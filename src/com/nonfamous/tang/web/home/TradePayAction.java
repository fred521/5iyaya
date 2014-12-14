package com.nonfamous.tang.web.home;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.Money;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.home.TradePayService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 * ֧��action
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */
public class TradePayAction extends MultiActionController {

	private TradePayService tradePayService;

	private ShopService shopService;

	private DefaultFormFactory formFactory;

	/**
	 * �ҵĸ���
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView pay_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// �û�id
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		TradePayQuery query = new TradePayQuery();
		query.setBuyId(memberId);
		String page = rvp.getParameter("page").getString();
		query.setCurrentPageString(page);
		// ֧����ʷ
		query = tradePayService.queryTradePayList(query);
		request.setAttribute("query", query);
		ModelAndView mv = new ModelAndView("/home/trade/myPayHistory");
		return mv;
	}
	
	/**
	 * �ҵ��տ�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView receive_pay_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// �û�id
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		TradePayQuery query = new TradePayQuery();
		query.setSellerId(memberId);
		String page = rvp.getParameter("page").getString();
		query.setCurrentPageString(page);
		// ֧����ʷ
		query = tradePayService.queryTradePayList(query);
		request.setAttribute("query", query);
		ModelAndView mv = new ModelAndView("/home/trade/myReceivePay");
		return mv;
	}

	/**
	 * ����֧����ʾ
	 * 
	 * @param request
	 * @param respons
	 * @return
	 * @throws Exception
	 */
	public ModelAndView quick_pay(HttpServletRequest request,
			HttpServletResponse respons) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// �û�id
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		// ��ȡ��ǰ�û���ǩԼ����
		List<Shop> mySignShopList = shopService.getMySignShopList(memberId);
		request.setAttribute("mySignShopList", mySignShopList);
		ModelAndView mv = new ModelAndView("/home/trade/quickPay");
		return mv;
	}

	/**
	 * ��������֧������
	 * 
	 * @param request
	 * @param respons
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_quick_pay(HttpServletRequest request,
			HttpServletResponse respons, TradeOrder order) throws Exception {
		Form form = formFactory.getForm("quickPay", request);
		request.setAttribute("form", form);
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		// ��ȡ��ǰ�û���ǩԼ����
		List<Shop> mySignShopList = shopService.getMySignShopList(memberId);
		request.setAttribute("mySignShopList", mySignShopList);
		ModelAndView mv = new ModelAndView("/home/trade/quickPay");

		// �û�id
		order.setBuyerId(memberId);
		// �û���¼id
		String loginId = rvp.getCookyjar().get(Constants.MemberLoinName_Cookie);
		order.setBuyerLoginId(loginId);
		if (form.isValide()) {
			// ����������
			String note = form.getField("note").getValue();
			// ֧�����
			Money payFee = new Money(form.getField("payMoney").getValue());
			order.setPayFee(payFee.getCent());
			// ��������
			order.setOrderDate(DateUtils.shortDate(new Date()));
			// ����֧��
			PayResult result = tradePayService.quickPay(order, note);
			if (result.isSuccess()) {
				mv = new ModelAndView("redirect:/pay/select_bank.htm");
				mv.addObject("orderid",order.getOrderNo());
				// mv.addObject("succMessage", "����֧���ɹ���");
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		return mv;
	}
	
	/**
	 * ֧���ɹ�
	 * @param request
	 * @param respons
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public ModelAndView quick_pay_success(HttpServletRequest request,
			HttpServletResponse respons, TradeOrder order) throws Exception {
		return new ModelAndView("home/trade/quickPaySucc");
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

}
