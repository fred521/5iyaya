package com.nonfamous.tang.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Seller;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.web.common.Constants;

public class AdminMemerAction extends MultiActionController {

	private static final Log logger = LogFactory
			.getLog(AdminMemerAction.class);

	private MemberService memberService;

	private DefaultFormFactory formFactory;

	/**
	 * 买家列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView buyer_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "买家会员列表");
		MemberQuery query = requestToQuery(request);
		query.setMemberType(Member.FLAG_BUYER);

		// 搜索买家会员信息
		List memberList = memberService.queryMember(query);
		ModelAndView mv = new ModelAndView("admin/adminMember/buyerList");
		mv.addObject("memberList", memberList);
		mv.addObject("search", query);
		return mv;
	}

	/**
	 * 卖家列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView seller_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "卖家会员列表");
		MemberQuery query = requestToQuery(request);
		query.setMemberType(Member.FLAG_SELLER);

		// 搜索买家会员信息
		List memberList = memberService.queryMember(query);
		ModelAndView mv = new ModelAndView("admin/adminMember/sellerList");
		mv.addObject("memberList", memberList);
		mv.addObject("search", query);
		return mv;
	}

	/**
	 * 新增卖家会员显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView member_add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminMember/memberAdd");
		return mv;
	}

	/**
	 * 新增卖家会员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_member_add(HttpServletRequest request,
			HttpServletResponse response, Seller seller) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminMember/memberAdd");
		Form form = formFactory.getForm("memberRegAdmin", request);
		request.setAttribute("form", form);

		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String operator = cookyjar.get(Constants.AdminUserId_Cookie);

		if (form.isValide()) {
			String ip = request.getRemoteAddr();
			seller.setRegisterIp(ip);
			// 登录名不区分大小写，全部变成小写
			seller.setLoginId(seller.getLoginId().toLowerCase());
			seller.setMD5LoginPassword(seller.getLoginPassword());

			NewMemberResult result = memberService.addSeller(seller, operator);
			if (result.isSuccess()) {
				mv = new ModelAndView("admin/adminMember/memberAddSucc");
				mv.addObject("memberId", result.getMemberId());
			} else {
				// 渲染出错信息
				if (StringUtils.equals(result.getErrorCode(),
						NewMemberResult.ERROR_MEMBER_EXIST)) {
					form.getField("loginId").setMessage(
							result.getErrorMessage());
				} else if (StringUtils.equals(result.getErrorCode(),
						NewMemberResult.ERROR_MOBILE_EXIST)) {
					form.getField("mobile")
							.setMessage(result.getErrorMessage());
				} else {
					mv.addObject("errorMessage", result.getErrorMessage());
				}

			}
		}
		return mv;
	}

	/**
	 * 会员信息修改显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView member_edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminMember/memberEdit");
		Form form = formFactory.getForm("memberEditAdmin", request);
		request.setAttribute("form", form);

		RequestValueParse rvp = new RequestValueParse(request);

		String memberId = rvp.getParameter("memberId").getString();
		if (StringUtils.isEmpty(memberId)) {
			mv.addObject("errorMessage", "无法获取用户信息");
			return mv;
		}
		Member member = memberService.getMemberById(memberId);
		if (member == null) {
			mv.addObject("errorMessage", "无法获取用户信息");
			return mv;
		}
		// copy数据
		formFactory.formCopy(member, form);
		form.getField("lastLoginTime").setValue(
				DateUtils.simpleFormat(member.getGmtLastLogin()));
		form.getField("registerTime").setValue(
				DateUtils.simpleFormat(member.getGmtRegister()));
		return mv;
	}

	/**
	 * 会员信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_member_edit(HttpServletRequest request,
			HttpServletResponse response, Member member) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminMember/memberEdit");
		Form form = formFactory.getForm("memberEditAdmin", request);
		request.setAttribute("form", form);

		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String operator = cookyjar.get(Constants.AdminUserId_Cookie);

		if (form.isValide()) {
			if (StringUtils.isNotEmpty(member.getLoginPassword())) {
				member.setMD5LoginPassword(member.getLoginPassword());
			}
			// 修改会员信息
			UpdateMemberInfoResult result = memberService.updateMemberInfo(
					member, operator);
			if (result.isSuccess()) {
				mv.addObject("succMessage", "会员信息修改成功");
			} else {
				// 渲染出错信息
				if (StringUtils.equals(result.getErrorCode(),
						UpdateMemberInfoResult.ERROR_MOBILE_EXIST)) {
					form.getField("mobile")
							.setMessage(result.getErrorMessage());
				} else {
					mv.addObject("errorMessage", result.getErrorMessage());
				}
			}
		}
		return mv;
	}

	/**
	 * 删除会员
	 * 
	 * @param request
	 * @param response
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_member_del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return updateStatus(request, Member.STATUS_DELETED);
	}

	/**
	 * 禁用会员
	 * 
	 * @param request
	 * @param response
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_member_disable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return updateStatus(request, Member.STATUS_DISABLE);
	}

	/**
	 * 取消禁用会员
	 * 
	 * @param request
	 * @param response
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_member_undisable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return updateStatus(request, Member.STATUS_NORMAL);
	}
	
	/**
	 * 会员店铺信息显示
	 * 
	 * @param request
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list_shop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminMember/searchShop");
		RequestValueParse rvp = new RequestValueParse(request);
		/**
		 * searchFlag 在页面上用于返回是店铺名称还是用户昵称
		 * searchFlag == n 返回 昵称 
		 * 在查询中，用户判断是否只查询卖家
		 * searchFlag == n 查询所有用户 否则查询卖家（新增商品的时候只有卖家能新增）
		 */
        String searchFlag =  rvp.getParameter("type").getString();
		MemberQuery query = requestToQuery(request);
		
		if ( StringUtils.isBlank(searchFlag)){
			query.setMemberType(Member.FLAG_SELLER);
		}
		
		List memberList = memberService.quickQueryShop(query);
		request.setAttribute("memberList", memberList);
		request.setAttribute("type", searchFlag);
		request.setAttribute("search", query);
		return mv;
	}

	/**
	 * 修改状态公用方法
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ModelAndView updateStatus(HttpServletRequest request, String status)
			throws Exception {
		ModelAndView mv = null;
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String operator = cookyjar.get(Constants.AdminUserId_Cookie);
		String listFlag = rvp.getParameter("listFlag").getString();
		String memberId = rvp.getParameter("memberId").getString();
		if (listFlag == null || memberId == null) {
			logger.error("入参为空，无法获取会员id或者列表类型");
			return mv;
		}
		if (StringUtils.equals(listFlag, Member.FLAG_BUYER)) {
			mv = new ModelAndView("forward:admin/admin_member/buyer_list.htm");
		}
		if (StringUtils.equals(listFlag, Member.FLAG_SELLER)) {
			mv = new ModelAndView("forward:admin/admin_member/seller_list.htm");
		}
		UpdateMemberInfoResult result = memberService.updateMemberStatus(
				memberId, status, operator);
		if (!result.isSuccess()) {
			mv.addObject("errorMessage", result.getErrorMessage());
		}

		return mv;
	}

	/**
	 * 获取query信息
	 * 
	 * @param request
	 * @return
	 */
	private MemberQuery requestToQuery(HttpServletRequest request) {
		MemberQuery query = new MemberQuery();
		RequestValueParse rvp = new RequestValueParse(request);
		// 关键字类型
		String keyType = rvp.getParameter("keyType").getString();
		// 关键字
		String keyWord = rvp.getParameter("keyWord").getString();
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);

		if (StringUtils.equals(keyType, "loginId")) {
			query.setLoginId(keyWord);
		} else if (StringUtils.equals(keyType, "shopName")) {
			query.setShopName(keyWord);
		} else if (StringUtils.equals(keyType, "shopOwner")) {
			query.setShopOwner(keyWord);
		} else if (StringUtils.equals(keyType, "nick")) {
			query.setNick(keyWord);
		} else if (StringUtils.equals(keyType, "name")) {
			query.setRealName(keyWord);
		} else if (StringUtils.equals(keyType, "mobile")) {
			query.setMobile(keyWord);
		}

		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));

		}
		return query;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

}
