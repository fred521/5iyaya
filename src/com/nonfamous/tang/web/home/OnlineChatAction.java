//===================================================================
// Created on 2007-6-14
//===================================================================
package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;

/**
 * <p>
 * ’‚¿Ô–¥◊¢ Õ
 * </p>
 * 
 * @author alan
 * @version $Id: OnlineChatAction.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */

public class OnlineChatAction extends MultiActionController {

	private ShopService shopService;

	private MemberService memberService;
	
	private String       enterpriseAccount = "test07";

	public ModelAndView chat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("member", memberService.getMemberById(request
				.getParameter("member_id")));
		request.setAttribute("enterpriseAccount", enterpriseAccount);
		return new ModelAndView("home/online/chat");
	}

	public ModelAndView chatUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("shop", shopService.shopSelectByMemberId(request
				.getParameter("member_id")));
		return new ModelAndView("home/online/rightUserInfo");
	}

	public ModelAndView chatGoodsList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("home/online/rightGoodsList");
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setEnterpriseAccount(String enterpriseAccount) {
		this.enterpriseAccount = enterpriseAccount;
	}

	
}
