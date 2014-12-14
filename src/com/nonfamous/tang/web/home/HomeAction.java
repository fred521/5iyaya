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
	 * ��Ա��¼
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
			// ��¼�Ƿ�ɹ�
			if (result.isSuccess()) {
				Member loginMember = result.getMember();
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, loginMember
						.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, loginMember
						.getLoginId());
				cookyjar.set(Constants.User_Role, loginMember.getMemberType());
				// �����Ƿ���Ҫ����loginId
				if (StringUtils.isEmpty(isSave)) {
					cookyjar.remove(Constants.MemberSaveLoginInfo_Cookie);
				}
				// �����Ҫ�����¼��Ϣ�Ļ����򽫱�־λ�ó�loginId
				if (StringUtils.equals(isSave, "Y")) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie,
							loginMember.getLoginId());
				}
				buildPermission(loginMember, cookyjar);
				// �ɹ���ת������gotoUrl����ͨ����ת
				if (StringUtils.isNotEmpty(gotoUrl)) {
					response.sendRedirect(gotoUrl);
					return null;
				} else {
					mv = new ModelAndView("redirect:/member/index.htm");
					// �ж���һ�Ա�Ƿ��Ѿ��ֻ����δ��������ת������ҳ��
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
	 * ��¼-��¼��Ϣ
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
	 * ��¼-��������
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
			//��֪��Ϊʲô��form��ת����mobile�ϴ��ж��š������� �Ժ��ϸ�顣
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
			// ��¼�������ִ�Сд��ȫ�����Сд
			buyer.setLoginId(buyer.getLoginId().toLowerCase());
			buyer.setMD5LoginPassword(buyer.getLoginPassword());
			buyer.setLastLoginIp(ip);
			buyer.setLoginCount(1);
			NewMemberResult result = memberService.addBuyer(buyer);
			if (result.isSuccess()) {
				// ���õ�¼��Ϣ��ע�ἴ���ڵ�¼һ��
				RequestValueParse rvp = new RequestValueParse(request);
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, result.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, buyer
						.getLoginId());
				// �������Ҫ�����ϴε�¼��Ϣ
				if (StringUtils.isNotEmpty(cookyjar
						.get(Constants.MemberSaveLoginInfo_Cookie))) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie, buyer
							.getLoginId());
				}
				// ����Ȩ����Ϣ
				buildPermission(buyer, cookyjar);
				// ��ʾ�ֻ�����
				request.setAttribute("mobile", buyer.getMobile());
				request.setAttribute("loginName", buyer.getLoginId());
				mv = new ModelAndView("/home/home/regSucc");

				// �ж��û��Ƿ��Ѿ���ucenter��ע���
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
	 * ����ע��
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
			// ��¼�������ִ�Сд��ȫ�����Сд
			buyer.setLoginId(buyer.getLoginId().toLowerCase());
			buyer.setMD5LoginPassword(buyer.getLoginPassword());
			buyer.setLastLoginIp(ip);
			buyer.setLoginCount(1);
			//���ñ���d��Ĭ�ϵ��û���Ϣ
			setDefaultBuyInfo(buyer);
			NewMemberResult result = memberService.addBuyer(buyer);
			if (result.isSuccess()) {
				// ���õ�¼��Ϣ��ע�ἴ���ڵ�¼һ��
				RequestValueParse rvp = new RequestValueParse(request);
				Cookyjar cookyjar = rvp.getCookyjar();
				cookyjar.set(Constants.MemberId_Cookie, result.getMemberId());
				cookyjar.set(Constants.MemberLoinName_Cookie, buyer
						.getLoginId());
				// �������Ҫ�����ϴε�¼��Ϣ
				if (StringUtils.isNotEmpty(cookyjar
						.get(Constants.MemberSaveLoginInfo_Cookie))) {
					cookyjar.set(Constants.MemberSaveLoginInfo_Cookie, buyer
							.getLoginId());
				}
				// ����Ȩ����Ϣ
				buildPermission(buyer, cookyjar);
				// ��ʾ�ֻ�����
				request.setAttribute("mobile", buyer.getMobile());
				request.setAttribute("loginName", buyer.getLoginId());
				mv = new ModelAndView("/home/home/regSucc");
				
				// �ж��û��Ƿ��Ѿ���ucenter��ע���
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
	 * ��Ա�ǳ�
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
		
		// ���memberId��Ϣ
		cookyjar.remove(Constants.MemberId_Cookie);
		cookyjar.remove(Constants.MemberPermissions_Cookie);
		// ����Ҫ�����ϴε�¼��Ա���������cookie��Ϣ
		if (StringUtils.isEmpty(cookyjar
				.get(Constants.MemberSaveLoginInfo_Cookie))) {
			cookyjar.remove(Constants.MemberLoinName_Cookie);
		}
		// ��ʾ��¼��
		Form form = formFactory.getForm("memberlogin", request);
		request.setAttribute("form", form);
		// ֱ�����Ƿ���Ҫ�����¼��Ϣ����ʾ�Ϳ����ˣ���Ϊ�Ѿ���¼��loginId��
		form.getField("loginId").setValue(
				cookyjar.get(Constants.MemberSaveLoginInfo_Cookie));
		
		ModelAndView mv = new ModelAndView("/home/home/init");
		// �ж��û��Ƿ��Ѿ���ucenter��ע���
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		UCenterWebUtil.getInstance(ucenterService).logoutUCenter(mv, username);
		
		return mv;
	}

	/**
	 * ��¼ҳ��
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
		// ��ʾ��¼��
		Form form = formFactory.getForm("memberlogin", request);
		request.setAttribute("form", form);
		// ֱ�����Ƿ���Ҫ�����¼��Ϣ����ʾ�Ϳ����ˣ���Ϊ�Ѿ���¼��loginId��
		form.getField("loginId").setValue(
				cookyjar.get(Constants.MemberSaveLoginInfo_Cookie));
		ModelAndView mv = new ModelAndView("/home/home/init");
		return mv;
	}

	/**
	 * ע��
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
	 * �����̳���ҳ
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
	 * �����̳�֮Ǯׯ
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
	 * �����̳�֮����ƽ̨
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
	 * �����̳�֮96����ƽ̨
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
	 * �����̳�֮���̳���¼
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
	 * ����YY֮������־
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
	 * ����YY֮web800�绰
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
	 * ����YY֮��������ͷ
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
	 * ����YY֮��ά���¼�
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
	 * ��Ȩ��ʱ��ת��ҳ��
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
	 * ����Ȩ����Ϣ
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
		// 1����������һ�������
		if (StringUtils.equals(loginMember.getMemberType(), Member.FLAG_SELLER)) {
			permissionList.add(Constants.Permission.ShopEdit);
			permissionList.add(Constants.Permission.NewsEdit);
			permissionList.add(Constants.Permission.MemberActive);
		}

		if (StringUtils.equals(loginMember.getMemberType(), Member.FLAG_BUYER)) {
			// ͨ����֤(ͨ����֤ͬʱ���з���������Ϣ��Ȩ��)

			permissionList.add(Constants.Permission.NewsEdit);
			permissionList.add(Constants.Permission.MemberActive);
			/*
			 * if (StringUtils.equals(loginMember.getPhoneValidate(),
			 * Member.MOBILE_VALIDATE_PASS)) {
			 * permissionList.add(Constants.Permission.NewsEdit); } else {
			 * permissionList.add(Constants.Permission.MemberActive); }
			 */
		}

		// ��һ�������ֻҪ������ж���Ȩ��
		/*
		 * if (StringUtils.equals(loginMember.getPhoneValidate(),
		 * Member.MOBILE_VALIDATE_PASS)) {
		 * permissionList.add(Constants.Permission.TradePay); }
		 */
		permissionList.add(Constants.Permission.TradePay);
		MemberAuthToken.buildPermissions(cookyjar, permissionList);
	} 

	/**
	 * У���¼��Ϣ�Ƿ�����
	 * 
	 * @param form
	 * @return
	 */
	private boolean checkPassLogin(HttpServletRequest request, Form form) {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		// �ж�У�����Ƿ���ȷ
		/*
		 * String checkCode = cookyjar.get(Constants.CheckCode_Cookie); String
		 * inputcheck = form.getField("checkcode").getValue(); if
		 * (!StringUtils.equals(checkCode, inputcheck)) {
		 * form.getField("checkcode").setMessage("У���벻��ȷ"); return false; }
		 */
		// ��cookieȥ��
		cookyjar.remove(Constants.CheckCode_Cookie);
		// �ж�ȷ�������Ƿ���ȷ
		String password = form.getField("loginPassword").getValue();
		String conpassword = form.getField("conPassword").getValue();
		if (!StringUtils.equals(password, conpassword)) {
			form.getField("conPassword").setMessage("�����ȷ�����벻һ��");
			return false;
		}
		// �ж��û����Ƿ����
		String loginId = form.getField("loginId").getValue().toLowerCase();
		int countMember = memberDAO.countSameMember(loginId, null);
		if (countMember > 0) {
			form.getField("loginId").setMessage("���û����Ѿ���������ʹ�ã��뻻һ���û���");
			return false;
		}
		// �ж��ֻ������Ƿ����
		//let's talk about it in the next turn when the user want to buy something.
		/*String mobile = form.getField("mobile").getValue();
		int countMobile = memberDAO.countSameMobile(mobile, null);
		if (countMobile > 0) {
			form.getField("mobile").setMessage("���ֻ��Ѿ���������ʹ�ã���������ֻ���");
			return false;
		}*/
		return true;
	}

	/**
	 * ����Ĭ���û���Ϣ
	 * 
	 * @param buyer ����ע��ʱ���û���Ϣ
	 * @return
	 */
	private void setDefaultBuyInfo(Buyer buyer){
		buyer.setMobile(UUIDGenerator.generate());
		buyer.setName(Constants.Default_Member_Info);
		buyer.setPostCode(Constants.Default_Member_Info);
		buyer.setAddress(Constants.Default_Member_Info);
	}
	
	/**
	 * ����Ĭ���û���Ϣ
	 * 
	 * @param buyer ����ע��ʱ���û���Ϣ
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
	 * ��������
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
			form.getField("email").setMessage("������ĵ����ʼ���ַ����ȷ�����������롣");
			return mv;
		}

		String subject = "��Ѿ����������ȷ�����һصĻ�Ա��";
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
			form.getField("loginId").setMessage("��Ա��������");
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
			form.getField("email").setMessage("������ĵ����ʼ���ַ����ȷ�����������롣");
			return mv;
		}
		member.setEmail(email);
		String tempPassword = generateTempPassword();

		try {
			String subject = "��Ѿ����������ȷ������Ҫ�޸ĵ�����";
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
	 * ����ѾѾ�ռ�
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
		// �ж��û��Ƿ��Ѿ���ucenter��ע���
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		//��¼ucenter
		UCenterWebUtil.getInstance(ucenterService).loginUCenter(mv, username);
		return mv;
	}
	 
	/**
	 * ����ѾѾ��̳
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
		// �ж��û��Ƿ��Ѿ���ucenter��ע���
		String username = cookyjar.get(Constants.MemberSaveLoginInfo_Cookie);
		//��¼ucenter
		UCenterWebUtil.getInstance(ucenterService).loginUCenter(mv, username);
		return mv;
	}
	

	/**
	 * У���������
	 * 
	 * @param form
	 * @return
	 */
	private boolean checkPassInfo(Form form) {
		boolean result = true;
		// ��ʵ����������д
		String name = form.getField("name").getValue();
		if (StringUtils.isEmpty(name)) {
			form.getField("name").setMessage("����д��ʵ����");
			result = false;
		}
		// ��ס��ַ������д
		String address = form.getField("address").getValue();
		if (StringUtils.isEmpty(address)) {
			form.getField("address").setMessage("����д��ס��ַ");
			result = false;
		}
		// �������������д
		String postcode = form.getField("postCode").getValue();
		if (StringUtils.isEmpty(postcode)) {
			form.getField("postCode").setMessage("����д��������");
			result = false;
		}
		return result;
	}

    private void sendWelcomeMail(String userName, String mail){
		String subject = "��ӭ������ѾѾ���ͥ";
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