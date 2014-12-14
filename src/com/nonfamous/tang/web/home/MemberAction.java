package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.UserCenter;
import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.domain.message.Message;
import com.nonfamous.tang.domain.result.ActiveResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.service.MemberAuthToken;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.service.mail.MailException;
import com.nonfamous.tang.service.usercenter.UserCenterService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 * 会员前台的action类
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberAction.java,v 1.5 2009/08/15 10:05:25 andy Exp $
 */
public class MemberAction extends MultiActionController {

	private MemberService memberService;

	private ShopService shopService;

	private NewsDAO newsDAO;
	
	private TradeOrderService tradeOrderService;

	private DefaultFormFactory formFactory;
	
	private IMailEngine mailEngine;

	private UserCenterService userCenterService;
	
	public ResourceBundle resource = ResourceBundle.getBundle("resources.message");
	
	public IMailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(IMailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	/**
	 * 会员激活
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_active(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String activeCode = rvp.getParameter("activeCode").getString();
		ModelAndView mv = new ModelAndView("/home/member/activeUser");
		request.setAttribute("activeCode", activeCode);
		// 校验合法性,否则出错
		if (StringUtils.isEmpty(activeCode)) {
			mv.addObject("errorMessage", "请输入检验码");
			return mv;
		}
		if (activeCode.length() != 6) {
			mv.addObject("errorMessage", "请输入6位检验码");
			return mv;
		}
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ActiveResult result = memberService.activeMember(memberId, activeCode);
		if (result.isSuccess()) {
			// 除去激活的权限,加上发布分类信息的权限
			MemberAuthToken authToken = (MemberAuthToken) request
					.getAttribute(MemberAuthToken.MemberAuthTokenInRequest);
			authToken.removePermission(Constants.Permission.MemberActive);
			authToken.addPermission(Constants.Permission.NewsEdit,
					Constants.Permission.TradePay);
			mv = new ModelAndView("forward:/member/index.htm");
		} else {
			mv.addObject("errorMessage", result.getErrorMessage());
		}
        return mv;
	}
	
	public ModelAndView apply2s(HttpServletRequest request, HttpServletResponse response)throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("redirect:/member/index.htm");
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Member member =memberService.getMemberById(memberId);
		String subject = "我爱YY网用户申请成为卖家";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[] { "5iyya.uk@gmail.com","daizhixia@gmail.com","haizhishu428@163.com" });
		mailInfo.setSubject(subject);
		Map model = new HashMap();
		model.put("username", member.getLoginId());
		model.put("email", member.getEmail());
		mailEngine.sendVelocityMessage(mailInfo, "conf/love/mail/templates/apply2s.vm", model);
		return  mv;
	}

	/**
	 * 重新发送校验码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_resend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("redirect:/member/active.htm");

		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ActiveResult result = memberService.resendActiveCode(memberId);
		if (result.isSuccess()) {
			mv.addObject("succMessage", "重发校验码成功！");
		} else {
			mv.addObject("errorMessage", result.getErrorMessage());
		}

		return mv;
	}

	/**
	 * 会员修改信息显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ModelAndView mv = new ModelAndView("/home/member/modifyInfo");
		Form form = formFactory.getForm("membermodify", request);
		request.setAttribute("form", form);

		Member member = memberService.getMemberById(memberId);
		
    	if(!checkIfHasFullMemberInfo(member)){
    		ModelAndView goTv = new ModelAndView("/home/home/regStep2");
    		goTv.addObject("gotoUrl", "/member/modify.htm");
    		return goTv;
    	}
    	
		if (member == null) {
			mv.addObject("errorMessage", "无相应用户信息");
			return mv;
		}
		// 将会员信息copy到form里面
		formFactory.formCopy(member, form);
		return mv;
	}

	/**
	 * 修改会员信息
	 * 
	 * @param request
	 * @param response
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_modify(HttpServletRequest request,
			HttpServletResponse response, Member member) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Form form = formFactory.getForm("membermodify", request);
		request.setAttribute("form", form);
		ModelAndView mv = new ModelAndView("/home/member/modifyInfo");

		if (form.isValide()) {
			member.setMemberId(memberId);
			// 修改会员信息
			UpdateMemberInfoResult result = memberService.updateMemberInfo(
					member, memberId);
			if (result.isSuccess()) {
				mv.addObject("succMessage", "信息修改成功");
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		return mv;
	}

	/**
	 * 激活页面初始化显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView active(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/member/activeUser");
		return mv;
	}

	/**
	 * 修改密码页面显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView modifypwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/member/modifyPwd");
		
		return mv;
	}

	/**
	 * 修改密码操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView do_modify_pwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Form form = formFactory.getForm("modifyPwd", request);
		request.setAttribute("form", form);
		ModelAndView mv = new ModelAndView("/home/member/modifyPwd");

		if (form.isValide()) {
			String oldPassword = form.getField("oldPassword").getValue();
			String newPassword = form.getField("newPassword").getValue();
			String conPassword = form.getField("conPassword").getValue();

			// 密码与密码确认是否相同
			if (!StringUtils.equals(newPassword, conPassword)) {
				form.getField("conPassword").setMessage("新密码与密码确认不相同，请重新输入");
				return mv;
			}

			// 修改密码
			UpdateMemberInfoResult result = memberService.changePassowrd(
					memberId, oldPassword, newPassword);
			if (result.isSuccess()) {
				mv.addObject("succMessage", "信息修改成功");
			} else {
				mv.addObject("errorMessage", result.getErrorMessage());
			}
		}
		return mv;
	}

	/**
	 * 首页显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ModelAndView mv = new ModelAndView("/home/member/index");
		// 会员信息
		Member member = memberService.getMemberById(memberId);
		if (member == null) {
			mv.addObject("errorMessage", "无相应用户信息");
			return mv;
		}
		request.setAttribute("member", member);
		if (StringUtils.equals(member.getMemberType(), Member.FLAG_SELLER)) {
			// 店铺信息
			Shop shop = shopService.shopSelectByMemberId(memberId);
			request.setAttribute("shop", shop);
		}
		// 增加分类信息的显示
		int totalNews = newsDAO.getNewsTotalByMemberId(memberId);
		request.setAttribute("totalNews", totalNews);
		
		//未处理订单
		request.setAttribute("total_unsettle_order",tradeOrderService.findUnsettledOrderCount(memberId));
		//未支付订单
		request.setAttribute("total_unpay_order",tradeOrderService.getUnpayOrderCount(memberId));
		
		//单点登录同步
		String synLoginCode = rvp.getParameter("synLoginCode").getString();
		mv.addObject("synLoginCode", synLoginCode);
		return mv;
	}

    private boolean checkIfHasFullMemberInfo(Member member){
    	if(Constants.Default_Member_Info.equals(member.getName())){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public ModelAndView forgotPasswd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/member/passwordBackOne");
		RequestValueParse rvp = new RequestValueParse(request);
		String type = rvp.getParameterOrAttribute("type").getString();
		mav.addObject("type", type);
		return mav;
	}
	
	public ModelAndView forgotUserName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/member/userNameBack");
		String type = request.getParameter("type");
		mav.addObject("type", type);
		return mav;
	}
	
	public ModelAndView isUserExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView stepOne = new ModelAndView("home/member/passwordBackOne");
		RequestValueParse rvp = new RequestValueParse(request);
		String type = rvp.getParameterOrAttribute("type").getString();
		String loginName = rvp.getParameterOrAttribute("loginName").getString();
		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		String email = null;
		int id = 0;

		stepOne.addObject("loginName", loginName);
		stepOne.addObject("type", type);
		
		// check user exist or not
		Member member = memberService.getMemberByLoginId(loginName);
		if(member==null){
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("errors.userNotExist"));
			message.setType(Message.ERROR);
			messageList.add(message);
			stepOne.addObject("messageList", messageList);
			return stepOne;
		}
		email = member.getEmail();
		// check email exist or not
		if (StringUtils.isBlank(email)) {
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("errors.emailNotExist"));
			message.setType(Message.ERROR);
			messageList.add(message);
			stepOne.addObject("messageList", messageList);
			return stepOne;
		}

		// user is valid
		ModelAndView stepTwo = new ModelAndView("home/member/passwordBackTwo");
		stepTwo.addObject("id", member.getMemberId());
		stepTwo.addObject("emailSuffix", getEmailSuffix(email));
		stepTwo.addObject("messageList", messageList);
		return stepTwo;
	}
	
	public ModelAndView checkEmailExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/member/userNameBack");
		String type = request.getParameter("type");
		String email = request.getParameter("email");
		mav.addObject("type", type);
		mav.addObject("email", email);
		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		List supplierList = null;
		List buyerList = null;
		Member member = this.memberService.getMemberByEmail(email);
		
		if(member!=null){
				//发送邮件
			HashMap model = new HashMap();
			model.put("supplierList", supplierList);
			message = memberService.findLoginId(email, model);
			messageList.add(message);
			mav.addObject("messageList", messageList);
			return mav;
		}
		//提示错误	
		message.setTitle(resource.getString("common.error"));	
		Object[] arg = {email}; 
		message.setDetail(resource.getString("MemberAction.checkEmailExist.error")); 
		message.setType(Message.ERROR);
		messageList.add(message);	
		mav.addObject("messageList", messageList);	
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView forgotPswEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/member/passwordBackTwo");
		RequestValueParse rvp = new RequestValueParse(request);
		String type = rvp.getParameterOrAttribute("type").getString();
		String idString = rvp.getParameterOrAttribute("id").getString();
		String email = rvp.getParameterOrAttribute("email").getString();
		String emailSuffix = rvp.getParameterOrAttribute("emailSuffix").getString();
		boolean isMatchedEmail = false;
		String name = null;
		String dbEmail = null;
		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		Member member = memberService.getMemberById(idString);
		dbEmail = member.getEmail();
		if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(dbEmail) && email.equals(dbEmail)) {
			isMatchedEmail = true;
		}
		// Email doesn't match,go to the form page,and show errors.
		if (!isMatchedEmail) {
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("errors.emailNotMatch"));
			message.setType(Message.ERROR);
			messageList.add(message);
			mav.addObject("type", type);
			mav.addObject("id", idString);
			mav.addObject("email", email);
			mav.addObject("emailSuffix", emailSuffix);
			mav.addObject("messageList", messageList);
			return mav;
		}
		// email match
		// send email,and go to success page.
		HashMap model = new HashMap();
		model.put("name", member.getNick());
		model.put("token", memberService.generateToken()); 
		try {
			memberService.findPassword(idString, email, request.getRemoteAddr(), model);
		} catch (MailException exception) {
			message.setDetail(resource.getString("errors.sendEmail"));
			messageList.add(message);
			mav.addObject("type", type);
			mav.addObject("id", idString);
			mav.addObject("email", email);
			mav.addObject("emailSuffix", emailSuffix);
			mav.addObject("messageList", messageList);
			return mav;
		}
		mav = new ModelAndView("home/member/passwordBackThree");
		return mav;
	}

	private String getEmailSuffix(String email) {
		int index = email.indexOf("@");
		return email.substring(index);
	}
	
	public ModelAndView updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/member/passwordUpdate");
		RequestValueParse rvp = new RequestValueParse(request);
		String code = rvp.getParameterOrAttribute("code").getString();
		String token = rvp.getParameterOrAttribute("token").getString();
		// check token
		boolean isValid = memberService.checkToken(token);
		// check code
		ArrayList<Message> messageList = new ArrayList<Message>();
		Message message = new Message();
		UserCenter userCenter = memberService.checkMemberCode(code, messageList);
		if (userCenter == null) {
			isValid = false;
		}
		if (!isValid || request.getMethod().equals("GET")) {
			mav.addObject("isValid", isValid);
			return mav;
		}

		String newPassword = rvp.getParameterOrAttribute("newPassword").getString();
		
		Member member = memberService.getMemberById(userCenter.getFkMember());
		memberService.changePassowrd(member.getMemberId(), member
				.getLoginPassword(), newPassword);
		userCenterService.deleteByMember(userCenter);
		mav.addObject("messageList", messageList);
		mav.addObject("isValid", isValid);
		return mav;
	}
    
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public UserCenterService getUserCenterService() {
		return userCenterService;
	}

	public void setUserCenterService(UserCenterService userCenterService) {
		this.userCenterService = userCenterService;
	}
	
}
