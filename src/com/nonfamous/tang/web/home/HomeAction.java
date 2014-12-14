package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Buyer;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.service.MemberAuthToken;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.service.mail.MailException;
import com.nonfamous.tang.service.ucenter.UCenterService;
import com.nonfamous.tang.web.common.Constants;

public class HomeAction extends MultiActionController {

	private DefaultFormFactory formFactory;

	private MemberService memberService;

	private UCenterService ucenterService;

	private MemberDAO memberDAO;

	private IMailEngine mailEngine;
		
	/**
	 * 会员登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Form form = formFactory.getForm("memberlogin", request);
		String gotoUrl = rvp.getParameter("gotoUrl").getString();
		String isSave = rvp.getParameter("isSave").getString();
		String loginIp = request.getRemoteAddr();
		ModelAndView mv = new ModelAndView("/home/home/init");
		request.setAttribute("form", form);

		if (form.isValide()) {
			String loginId = form.getField("loginId").getValue().toLowerCase();
			String password = form.getField("password").getValue();
			LoginResult result = memberService
					.login(loginId, password, loginIp);
			// 登录是否成功
			if (result.isSuccess()) {
				Member loginMember = result.getMember();
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, loginMember
						.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, loginMember
						.getLoginId());
				cookyjar.set(Constants.User_Role, loginMember.getMemberType());
				// 保存是否需要保存loginId
				if (StringUtils.isEmpty(isSave)) {
					cookyjar.remove(Constants.MemberSaveLoginInfo_Cookie);
				}
				// 如果需要保存登录信息的话，则将标志位置成loginId
				if (StringUtils.equals(isSave, "Y")) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie,
							loginMember.getLoginId());
				}
				buildPermission(loginMember, cookyjar);
				// 成功跳转，根据gotoUrl做不通的跳转
				if (StringUtils.isNotEmpty(gotoUrl)) {
					response.sendRedirect(gotoUrl);
					return null;
				} else {
					mv = new ModelAndView("redirect:/member/index.htm");
					// 判断买家会员是否已经手机激活，未激活则跳转到激活页面
					if (StringUtils.equals(loginMember.getMemberType(),
							Member.FLAG_BUYER)
							&& !StringUtils.equals(loginMember
									.getPhoneValidate(),
									Member.MOBILE_VALIDATE_PASS)) {
						mv = new ModelAndView("forward:/member/active.htm");
					}
				}

				UCenterWebUtil.getInstance(ucenterService).registerOrLoginUCenter(mv, loginId, password, result.getMember().getEmail());
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		return mv;
	}

	/**
	 * 登录-登录信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView reglogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Form form = formFactory.getForm("fastreg", request);
		ModelAndView mv = new ModelAndView("/home/home/regStep1");
		request.setAttribute("form", form);
		if (form.isValide()) {
			if (!checkPassLogin(request, form)) {
				return mv;
			}
			mv = new ModelAndView("/home/home/regStep2");
		}
		return mv;
	}

	/**
	 * 登录-个人资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView reginfo(HttpServletRequest request,
			HttpServletResponse response, Buyer buyer) throws Exception {
		Form form = formFactory.getForm("memberreg", request);
		ModelAndView mv = new ModelAndView("/home/home/regStep2");
		request.setAttribute("form", form);
		
		if("".equals(buyer.getLoginId())){
			RequestValueParse rvp = new RequestValueParse(request);
			Cookyjar cookyjar = rvp.getCookyjar();
			String memberId = cookyjar.get(Constants.MemberId_Cookie);
			String gotoUrl = rvp.getParameter("gotoUrl").getString();
			//不知道为什么从form里转来的mobile老带有逗号“，”。 以后待细查。
			String mobileNumber = form.getField("mobile").getValue();
			UpdateMemberInfoResult result = updateFullMemberInfo(memberId,mobileNumber, buyer);
			if(result.isSuccess()){
				response.sendRedirect(request.getContextPath() + gotoUrl);
				return null;
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
			return mv;
		}

		if (form.isValide()) {
			if (!checkPassInfo(form)) {
				return mv;
			}
			String ip = request.getRemoteAddr();
			buyer.setRegisterIp(ip);
			// 登录名不区分大小写，全部变成小写
			buyer.setLoginId(buyer.getLoginId().toLowerCase());
			buyer.setMD5LoginPassword(buyer.getLoginPassword());
			buyer.setLastLoginIp(ip);
			buyer.setLoginCount(1);
			NewMemberResult result = memberService.addBuyer(buyer);
			if (result.isSuccess()) {
				// 设置登录信息，注册即等于登录一次
				RequestValueParse rvp = new RequestValueParse(request);
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, result.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, buyer
						.getLoginId());
				// 如果是需要保存上次登录信息
				if (StringUtils.isNotEmpty(cookyjar
						.get(Constants.MemberSaveLoginInfo_Cookie))) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie, buyer
							.getLoginId());
				}
				// 设置权限信息
				buildPermission(buyer, cookyjar);
				// 显示手机号码
				request.setAttribute("mobile", buyer.getMobile());
				request.setAttribute("loginName", buyer.getLoginId());
				mv = new ModelAndView("/home/home/regSucc");

				// 判断用户是否已经在ucenter中注册过
				String loginId = buyer.getLoginId().toLowerCase();
				String password = form.getField("password").getValue();
				UCenterWebUtil.getInstance(ucenterService).registerOrLoginUCenter(mv, loginId, password, buyer.getEmail());
				
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		sendWelcomeMail(buyer.getLoginId(),buyer.getEmail());
		return mv;
	}
	/**
	 * 快速注册
	 * @param request
	 * @param response
	 * @param buyer
	 * @return
	 * @throws Exception
	 */
	public ModelAndView fastreg(HttpServletRequest request,
			HttpServletResponse response, Buyer buyer) throws Exception {
		Form form = formFactory.getForm("fastreg", request);
		ModelAndView mv = new ModelAndView("/home/home/regStep1");
		request.setAttribute("form", form);
		if (form.isValide()) {
			if (!checkPassLogin(request, form)) {
				return mv;
			}
			String ip = request.getRemoteAddr();
			buyer.setRegisterIp(ip);
			// 登录名不区分大小写，全部变成小写
			buyer.setLoginId(buyer.getLoginId().toLowerCase());
			buyer.setMD5LoginPassword(buyer.getLoginPassword());
			buyer.setLastLoginIp(ip);
			buyer.setLoginCount(1);
			//设置必须d的默认的用户信息
			setDefaultBuyInfo(buyer);
			NewMemberResult result = memberService.addBuyer(buyer);
			if (result.isSuccess()) {
				// 设置登录信息，注册即等于登录一次
				RequestValueParse rvp = new RequestValueParse(request);
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, result.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, buyer
						.getLoginId());
				// 如果是需要保存上次登录信息
				if (StringUtils.isNotEmpty(cookyjar
						.get(Constants.MemberSaveLoginInfo_Cookie))) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie, buyer
							.getLoginId());
				}
				// 设置权限信息
				buildPermission(buyer, cookyjar);
				// 显示手机号码
				request.setAttribute("mobile", buyer.getMobile());
				request.setAttribute("loginName", buyer.getLoginId());
				mv = new ModelAndView("/home/home/regSucc");
				
				// 判断用户是否已经在ucenter中注册过
				String loginId = buyer.getLoginId().toLowerCase();
				String password = form.getField("loginPassword").getValue();
				UCenterWebUtil.getInstance(ucenterService).registerOrLoginUCenter(mv, loginId, password, buyer.getEmail());
				
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		
		sendWelcomeMail(buyer.getLoginId(),buyer.getEmail());
		return mv;
	}

	/**
	 * 会员登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		
		// 清除memberId信息
		cookyjar.remove(Constants.MemberId_Cookie);
		cookyjar.remove(Constants.MemberPermissions_Cookie);
		// 不需要保存上次登录会员名，则清除cookie信息
		if (StringUtils.isEmpty(cookyjar
				.get(Constants.MemberSaveLoginInfo_Cookie))) {
			cookyjar.remove(Constants.MemberLoinName_Cookie);
		}
		// 显示登录名
		Form form = formFactory.getForm("memberlogin", request);
		request.setAttribute("form", form);
		// 直接用是否需要保存登录信息来显示就可以了，因为已经记录成loginId了
		form.getField("loginId").setValue(
				cookyjar.get(Constants.MemberSaveLoginInfo_Cookie));
		
		ModelAndView mv = new ModelAndView("/home/home/init");
		// 判断用户是否已经在ucenter中注册过
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		UCenterWebUtil.getInstance(ucenterService).logoutUCenter(mv, username);
		
		return mv;
	}

	/**
	 * 登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String gotoUrl = rvp.getParameter("gotoUrl").getString();
		request.setAttribute("gotoUrl", gotoUrl);
		// 显示登录名
		Form form = formFactory.getForm("memberlogin", request);
		request.setAttribute("form", form);
		// 直接用是否需要保存登录信息来显示就可以了，因为已经记录成loginId了
		form.getField("loginId").setValue(
				cookyjar.get(Constants.MemberSaveLoginInfo_Cookie));
		ModelAndView mv = new ModelAndView("/home/home/init");
		return mv;
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/regStep1");
		return mv;
	}

	/**
	 * 关于商朝首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/index");
		return mv;
	}

	/**
	 * 关于商朝之钱庄
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_money(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_money");
		return mv;
	}

	/**
	 * 关于商朝之短信平台
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_sms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_sms");
		return mv;
	}

	/**
	 * 关于商朝之96语音平台
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_96(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_96");
		return mv;
	}

	/**
	 * 关于商朝之招商城名录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_dir(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_dir");
		return mv;
	}

	/**
	 * 关于YY之商情杂志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_5iyya(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_5iyya");
		return mv;
	}

	/**
	 * 关于YY之web800电话
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_web800(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_web800");
		return mv;
	}

	/**
	 * 关于YY之网络摄像头
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_camera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_camera");
		return mv;
	}

	/**
	 * 关于YY之三维试衣间
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView aboutus_3d(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/aboutus/aboutus_3d");
		return mv;
	}

	/**
	 * 无权限时跳转的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView no_per(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/noPermission");
		return mv;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	/**
	 * 设置权限信息
	 * 
	 * @param loginMember
	 * @param cookyjar
	 */
	private void buildPermission(Member loginMember, Cookyjar cookyjar) {
		if (loginMember == null) {
			throw new NullPointerException(
					"member info is null,please check login info");
		}
		List<Constants.Permission> permissionList = new ArrayList<Constants.Permission>();
		permissionList.add(Constants.Permission.NonePermission);
		// 1、区分是买家还是卖家
		if (StringUtils.equals(loginMember.getMemberType(), Member.FLAG_SELLER)) {
			permissionList.add(Constants.Permission.ShopEdit);
			permissionList.add(Constants.Permission.NewsEdit);
			permissionList.add(Constants.Permission.MemberActive);
		}

		if (StringUtils.equals(loginMember.getMemberType(), Member.FLAG_BUYER)) {
			// 通过验证(通过验证同时具有发布分类信息的权限)

			permissionList.add(Constants.Permission.NewsEdit);
			permissionList.add(Constants.Permission.MemberActive);
			/*
			 * if (StringUtils.equals(loginMember.getPhoneValidate(),
			 * Member.MOBILE_VALIDATE_PASS)) {
			 * permissionList.add(Constants.Permission.NewsEdit); } else {
			 * permissionList.add(Constants.Permission.MemberActive); }
			 */
		}

		// 买家或者卖家只要激活就有订单权限
		/*
		 * if (StringUtils.equals(loginMember.getPhoneValidate(),
		 * Member.MOBILE_VALIDATE_PASS)) {
		 * permissionList.add(Constants.Permission.TradePay); }
		 */
		permissionList.add(Constants.Permission.TradePay);
		MemberAuthToken.buildPermissions(cookyjar, permissionList);
	} 

	/**
	 * 校验登录信息是否正常
	 * 
	 * @param form
	 * @return
	 */
	private boolean checkPassLogin(HttpServletRequest request, Form form) {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		// 判断校验码是否正确
		/*
		 * String checkCode = cookyjar.get(Constants.CheckCode_Cookie); String
		 * inputcheck = form.getField("checkcode").getValue(); if
		 * (!StringUtils.equals(checkCode, inputcheck)) {
		 * form.getField("checkcode").setMessage("校验码不正确"); return false; }
		 */
		// 将cookie去掉
		cookyjar.remove(Constants.CheckCode_Cookie);
		// 判断确认密码是否正确
		String password = form.getField("loginPassword").getValue();
		String conpassword = form.getField("conPassword").getValue();
		if (!StringUtils.equals(password, conpassword)) {
			form.getField("conPassword").setMessage("密码和确认密码不一致");
			return false;
		}
		// 判断用户名是否可用
		String loginId = form.getField("loginId").getValue().toLowerCase();
		int countMember = memberDAO.countSameMember(loginId, null);
		if (countMember > 0) {
			form.getField("loginId").setMessage("该用户名已经被其他人使用，请换一个用户名");
			return false;
		}
		// 判断手机号码是否可用
		//let's talk about it in the next turn when the user want to buy something.
		/*String mobile = form.getField("mobile").getValue();
		int countMobile = memberDAO.countSameMobile(mobile, null);
		if (countMobile > 0) {
			form.getField("mobile").setMessage("该手机已经被其他人使用，请检查你的手机号");
			return false;
		}*/
		return true;
	}

	/**
	 * 设置默认用户信息
	 * 
	 * @param buyer 快速注册时的用户信息
	 * @return
	 */
	private void setDefaultBuyInfo(Buyer buyer){
		buyer.setMobile(UUIDGenerator.generate());
		buyer.setName(Constants.Default_Member_Info);
		buyer.setPostCode(Constants.Default_Member_Info);
		buyer.setAddress(Constants.Default_Member_Info);
	}
	
	/**
	 * 设置默认用户信息
	 * 
	 * @param buyer 快速注册时的用户信息
	 * @return
	 */
	private UpdateMemberInfoResult updateFullMemberInfo(String memberId, String mobileNumber, Buyer buyer){
		Member member = this.memberService.getMemberById(memberId);
		member.setName(buyer.getName());
		member.setSex(buyer.getSex());
		member.setNick(buyer.getNick());
		member.setAddress(buyer.getAddress());
		member.setPostCode(buyer.getPostCode());
		member.setMobile(mobileNumber);
		member.setPhone(buyer.getPhone());
		return this.memberService.updateMemberInfo(member, buyer.getLoginId());
	}
	
	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView resetPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/resetPassword");
		return mv;
	}

	public ModelAndView findPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/findPassword");
		return mv;
	}

	public ModelAndView forgotUserName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/forgotUserName");
		return mv;
	}

	public ModelAndView getUserName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Form form = formFactory.getForm("getUserName", request);
		String email = form.getField("email").getValue();
		request.setAttribute("form", form);

		ModelAndView mv = new ModelAndView("/home/home/forgotUserName");
		if (!form.isValide()) {
			return mv;
		}
		Member member = memberService.getMemberByEmail(email);
		if (member == null) {
			form.getField("email").setMessage("您输入的电子邮件地址不正确，请重新输入。");
			return mv;
		}

		String subject = "爱丫提醒您：请确认您找回的会员名";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[] { email });
		mailInfo.setSubject(subject);
		Map model = new HashMap();
		model.put("username", member.getLoginId());
		model.put("email", email);
		mailEngine.sendVelocityMessage(mailInfo,
				"conf/love/mail/templates/getUserName.vm", model);
		mv = new ModelAndView("/home/home/forgotUserNameSucceed");

		return mv;
	}

	public ModelAndView forgotPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/findPassword");

		Form form = formFactory.getForm("forgotPassword", request);
		request.setAttribute("form", form);
		if (!form.isValide()) {
			return mv;
		}

		String loginId = form.getField("loginId").getValue();

		MemberQuery query = new MemberQuery();
		query.setMemberType(Member.FLAG_BUYER);
		query.setLoginId(loginId);
		List members = memberService.queryMember(query);
		if (members == null || members.isEmpty()) {
			form.getField("loginId").setMessage("会员名不存在");
			return mv;
		}

		mv = new ModelAndView("/home/home/forgotPassword");

		return mv;
	}

	public ModelAndView getPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/home/forgotPassword");
		Form form = formFactory.getForm("getPassword", request);
		request.setAttribute("form", form);
		if (!form.isValide()) {
			return mv;
		}
		String email = form.getField("email").getValue();

		Member member = memberService.getMemberByEmail(email);
		if (member == null) {
			form.getField("email").setMessage("您输入的电子邮件地址不正确，请重新输入。");
			return mv;
		}
		member.setEmail(email);
		String tempPassword = generateTempPassword();

		try {
			String subject = "爱丫提醒您：请确认您需要修改的密码";
			MailInfo mailInfo = new MailInfo();
			mailInfo.setTo(new String[] { email });
			mailInfo.setSubject(subject);
			Map model = new HashMap();
			model.put("username", member.getLoginId());
			model.put("password", tempPassword);
			mailEngine.sendVelocityMessage(mailInfo,
					"conf/love/mail/templates/getPassword.vm", model);
			logger.info("The new password for " + member.getMemberId()
					+ " has been sent to " + member.getEmail());

			UpdateMemberInfoResult result = memberService.changePassowrd(member.getMemberId(), member
					.getLoginPassword(), tempPassword,false);
			
			logger.info("reset user's password successful");
		} catch (MailException e) {
			logger.error("sending email failed due to: " + e.getMessage());

			throw e;
		}

		mv = new ModelAndView("/home/home/getPasswordSucceed");
		return mv;
	}
	
	/**
	 * 链接丫丫空间
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView uchome(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		ModelAndView mv = new ModelAndView("/home/home/ucenter");
		mv.addObject("homeUrl", homeUrl);
		// 判断用户是否已经在ucenter中注册过
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		//登录ucenter
		UCenterWebUtil.getInstance(ucenterService).loginUCenter(mv, username);
		return mv;
	}
	 
	/**
	 * 链接丫丫论坛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView bbs(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		ModelAndView mv = new ModelAndView("/home/home/ucenter");
		mv.addObject("bbsUrl", bbsUrl);
		// 判断用户是否已经在ucenter中注册过
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		//登录ucenter
		UCenterWebUtil.getInstance(ucenterService).loginUCenter(mv, username);
		return mv;
	}
	

	/**
	 * 校验个人资料
	 * 
	 * @param form
	 * @return
	 */
	private boolean checkPassInfo(Form form) {
		boolean result = true;
		// 真实姓名必须填写
		String name = form.getField("name").getValue();
		if (StringUtils.isEmpty(name)) {
			form.getField("name").setMessage("请填写真实姓名");
			result = false;
		}
		// 居住地址必须填写
		String address = form.getField("address").getValue();
		if (StringUtils.isEmpty(address)) {
			form.getField("address").setMessage("请填写居住地址");
			result = false;
		}
		// 邮政编码必须填写
		String postcode = form.getField("postCode").getValue();
		if (StringUtils.isEmpty(postcode)) {
			form.getField("postCode").setMessage("请填写邮政编码");
			result = false;
		}
		return result;
	}

    private void sendWelcomeMail(String userName, String mail){
		String subject = "欢迎您加入丫丫大家庭";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[]{mail});
		mailInfo.setSubject(subject);
		
		Map model = new HashMap();
		model.put("username", userName);
		
		mailEngine.sendVelocityMessage(mailInfo,
				"conf/love/mail/templates/registerReply.vm", model);
    }
	
	public void setMailEngine(IMailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	private String generateTempPassword() {
		StringBuffer sb = new StringBuffer();
		char[] c = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a',
				's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v',
				'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
				'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X',
				'C', 'V', 'B', 'N', 'M' };

		sb.append(RandomStringUtils.random(3, c));

		char[] punctuation = {  '_', '-'};

		sb.append(RandomStringUtils.random(1, punctuation));

		sb.append(RandomStringUtils.randomNumeric(2));

		if (logger.isDebugEnabled()) {
			logger.debug("generated Temporary Password: " + sb.toString());
		}

		return sb.toString();
	}
	
	
	public UCenterService getUcenterService() {
		return ucenterService;
	}

	public void setUcenterService(UCenterService ucenterService) {
		this.ucenterService = ucenterService;
	}


	//===========================ucenter config
	private String homeUrl;
	private String bbsUrl;

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getBbsUrl() {
		return bbsUrl;
	}

	public void setBbsUrl(String bbsUrl) {
		this.bbsUrl = bbsUrl;
	}

	
}