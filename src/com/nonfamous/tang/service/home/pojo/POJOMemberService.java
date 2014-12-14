package com.nonfamous.tang.service.home.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.nonfamous.commom.util.CheckCodeGenerator;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.TokenUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.home.MobileValidateDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Buyer;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.MemberList;
import com.nonfamous.tang.domain.MobileValidate;
import com.nonfamous.tang.domain.Seller;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.UserCenter;
import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.domain.message.Message;
import com.nonfamous.tang.domain.result.ActiveResult;
import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.service.sms.SmsService;
import com.nonfamous.tang.service.ucenter.UCenterException;
import com.nonfamous.tang.service.ucenter.UCenterService;
import com.nonfamous.tang.service.usercenter.UserCenterService;

/**
 * <p>
 * 会员服务类的实现类
 * </p>
 * 
 * @author:daodao
 * @version $Id: POJOMemberService.java,v 1.8 2009/08/15 10:05:25 andy Exp $
 */
public class POJOMemberService extends POJOServiceBase implements MemberService {

	private UCenterService ucenterService;
	
	private SmsService smsService;

	private MemberDAO memberDAO;

	private MobileValidateDAO mobileValidateDAO;

	
	private ShopDAO shopDAO;
	
	
	public ResourceBundle resource = ResourceBundle.getBundle("resources.message");
	
	
	private IMailEngine mailEngine;
	
	
	private UserCenterService userCenterService;
	
	private String forgetPasswordTemplate;
	
	private String forgetLoginIdTemplate;
	
	private String signUpTemplate;
	
	private String getPasswordSubject="爱丫网提醒您：请确认您的密码修改";
	
	private String getLoginIdSubject = "爱丫装网提醒您：请牢记您的登录ID";

	private static final int TOKEN_VALID_DAY = 1;
	
	public NewMemberResult addBuyer(Buyer buyer) {
		return addMember(buyer, null);
	}

	public NewMemberResult addSeller(Seller seller, String creator) {
		return addSeller(seller, false, creator);
	}

	public NewMemberResult addSellerAndShop(Seller seller, String creator) {
		return addSeller(seller, true, creator);
	}

	public NewMemberResult addMember(Member member, String creator) {
		// 1、判断入参是否为空
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		NewMemberResult result = new NewMemberResult();
		//20070910取消手机号必填的判断
		/*
		if (StringUtils.isEmpty(member.getMobile())) {
			result.setErrorCode(NewMemberResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("手机号码不能为空");
			return result;
		}
		*/
		// 2、判断该会员是否已经被注册
		int memberCount = memberDAO.countSameMember(member.getLoginId(), null);
		if (memberCount > 0) {
			result.setErrorCode(NewMemberResult.ERROR_MEMBER_EXIST);
			return result;
		}
		// 3、判断手机是否已经被注册过
		// 卖家的手机可以为空，faint
		if (StringUtils.isNotBlank(member.getMobile())) {
			int mobileCount = memberDAO.countSameMobile(member.getMobile(),
					null);
			if (mobileCount > 0) {
				result.setErrorCode(NewMemberResult.ERROR_MOBILE_EXIST);
				return result;
			}
		}
		String memberId = UUIDGenerator.generate();
		member.setPhoneValidate(Member.MOBILE_VALIDATE_PASS);
		// 刚注册的用户用户状态为正常
		member.setStatus(Member.STATUS_NORMAL);
		if (StringUtils.equals(member.getMemberType(), Member.FLAG_BUYER)) {
			//TODO currently we do not need this. maybe in future 
			//change the old requirement: whatever the member is buyer or seller, the member should be validate pass  
			member.setPhoneValidate(Member.MOBILE_VALIDATE_PASS);
			creator = memberId;
		}
		// 4、新增用户信息
		member.setCreator(creator);
		member.setModifier(creator);
		member.setMemberId(memberId);
		memberDAO.insert(member);
		// 5、如果是买家生成校验码，并且发送短消息
		if (StringUtils.equals(member.getMemberType(), Member.FLAG_BUYER)) {
			//TODO currently we do not need this. maybe in future 
			//sendActiveCode(member.getMemberId(), member.getMobile());
		}
		// 6、返回用户ID
		result.setMemberId(memberId);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 新增卖家，区分是否需要同时新增店铺信息
	 * 
	 * @param seller
	 * @param isNeedAddShop
	 * @return
	 */
	private NewMemberResult addSeller(Seller seller, boolean isNeedAddShop,
			String creator) {
		// 1、判断入参是否为空
		if (seller == null) {
			throw new NullPointerException("seller info can't be null");
		}
		// 2、当需要同事新增店铺信息时，判断店铺信息是否为空
		if (isNeedAddShop && seller.getShop() == null) {
			throw new NullPointerException("shop info can't be null");
		}
		// 3、调用addMember函数
		NewMemberResult result = addMember(seller, creator);
		if (result.isSuccess()) {
			// TODO 4、当需要同时新增店铺信息时，调用店铺服务，新增店铺信息
			if (isNeedAddShop) {

			}
		}
		return result;
	}

	public LoginResult login(String loginAccount, String password,
			String loginIp) {
		LoginResult result = new LoginResult();
		// 1、判断入参是否为空
		if (StringUtils.isEmpty(loginAccount) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(loginIp)) {
			result.setErrorCode(LoginResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("登录id,密码，登录ip不能为空");
			return result;
		}
		// 2、判断是否有该用户存在
		Member loginMember = memberDAO.getMemberByLoginId(loginAccount);
		if (loginMember == null) {
			result.setErrorCode(LoginResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3、判断密码是否正确
		if (!StringUtils.equals(loginMember.getLoginPassword(), MD5Encrypt
				.encode(password))) {
			result.setErrorCode(LoginResult.ERROR_PASS_ERROR);
			return result;
		}
		// 4、判断用户状态是否为正常状态(N)
		if (!StringUtils.equals(loginMember.getStatus(), Member.STATUS_NORMAL)) {
			result.setErrorCode(LoginResult.ERROR_MEMBER_STATUS);
			return result;
		}
		// 5、一切正常，则更新最后修改时间，最后登录时间,最后登录ip,增加登录次数
		memberDAO.updateLoginInfo(loginMember.getMemberId(), loginIp);
		// 6、返回用户信息数据
		result.setMember(loginMember);
		result.setSuccess(true);
		return result;
	}

	public List queryMember(MemberQuery query) {
		// 1、判断入参是否为空
		if (query == null) {
			throw new NullPointerException("query info can't be null");
		}
		// 2、查询用户列表数据
		return memberDAO.queryMemberList(query);
	}

	public Member getMemberById(String memberId) {
		// 1、判断入参是否为空
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		// 2、查询用户详细信息
		return memberDAO.getMemberById(memberId);
	}

	public Seller getSellerByShopId(String shopId) {
		// TODO 1、判断入参是否为空
		// TODO 2、调用店铺接口，获取店铺相关信息，得到memberId
		// TODO 3、调用getMemberById获取会员信息
		// TODO 4、在用户信息中加入已经获取的店铺信息
		// TODO 5、返回用户信息
		return null;
	}

	public UpdateMemberInfoResult updateMemberInfo(Member member,
			String modifier) {
		// 1、判断入参是否为空
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		UpdateMemberInfoResult result = new UpdateMemberInfoResult();
		member.setModifier(modifier);
		Member oldMember = memberDAO.getMemberById(member.getMemberId());
		// 2、判断用户是否存在
		if (oldMember == null) {
			result.setErrorCode(LoginResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3、判断修改的手机号码是否改动过
		// 如果手机为空，则不做任何事情
		if (StringUtils.isNotBlank(member.getMobile())) {
			if (!StringUtils.equals(member.getMobile(), oldMember.getMobile())) {
				int mobileCount = memberDAO.countSameMobile(member.getMobile(),
						member.getMemberId());
				// 3.1是否该手机已经被其他人使用
				if (mobileCount > 0) {
					result
							.setErrorCode(UpdateMemberInfoResult.ERROR_MOBILE_EXIST);
					return result;
				}
				//Jason 于1/8/2009注释掉。
				// 3.2如果是买家用户，需要将验证状态改为等待验证，并且重新发送验证码，卖家会员不需要
/*				if (StringUtils.equals(oldMember.getMemberType(),
						Member.FLAG_BUYER)) {
					sendActiveCode(member.getMemberId(), member.getMobile());
					if (StringUtils.equals(oldMember.getPhoneValidate(),
							Member.MOBILE_VALIDATE_PASS)) {
						member.setPhoneValidate(Member.MOBILE_VALIDATE_AGAIN);
					}
				}*/
			}
		}
		// 4、更新用户信息
		memberDAO.updateMemberInfo(member);
		
		// 5. 更新ucenter用户资料
		/* 用户不能更新email
		try{
			String username = member.getLoginId();
			// 判断用户是否已经在ucenter中注册过
			Map<String, String> user = ucenterService.userExists(username);
			if (user.size() > 0) { // 用户已经在ucenter中注册过了
				int res = ucenterService.userUpdate(username, member.getLoginPassword(), null, 
					member.getEmail(), 
					false);
				if(logger.isInfoEnabled()){
					logger.info("更新ucenter用户资料成功.");
				}
			}
		}
		catch(UCenterException e){
			logger.error("更新ucenter用户资料失败, error code [" + e.getCode() + "].");
			logger.error(e.getMessage());
			result.setErrorMessage(e.getMessage());
			return result;
		}
		*/
		result.setSuccess(true);
		return result;

	}

	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword,boolean isNeedCheck) {
		UpdateMemberInfoResult result = new UpdateMemberInfoResult();
		// 1、判断入参是否为空
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(oldPassword)
				|| StringUtils.isEmpty(newPassword)) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("用户id,旧密码，需要修改的密码不能为空");
			return result;
		}
		// 2、判断是否有该用户存在
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3、判断用户状态，状态不正常不允许修改
		if (!StringUtils.equals(member.getStatus(), Member.STATUS_NORMAL)) {
			result
					.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("用户状态为非正常状态，不允许修改密码");
			return result;
		}
		if(isNeedCheck){
			// 4、判断旧密码是否正确
			if (!StringUtils.equals(MD5Encrypt.encode(oldPassword), member
					.getLoginPassword())) {
				result.setErrorCode(UpdateMemberInfoResult.ERROR_OLD_PASSWORD);
				return result;
			}
		}
		// 5、修改密码
		
		String md5Password = MD5Encrypt.encode(newPassword);
		memberDAO.updatePassword(memberId, md5Password, memberId);
		
		// 6. 更新ucenter的用户密码
		try{
			String username = member.getLoginId();
			// 判断用户是否已经在ucenter中注册过
			Map<String, String> user = ucenterService.userExists(username);
			if (user.size() > 0) { // 用户已经在ucenter中注册过了
				int res = ucenterService.userUpdate(username, oldPassword, newPassword, 
						member.getEmail()/*不更改email*/, 
					false/*更改用户资料需要验证密码*/);
				if(logger.isInfoEnabled()){
					logger.info("更新用户密码成功.");
				}
			}
		}
		catch(UCenterException e){
			logger.error("更新用户密码失败, error code [" + e.getCode() + "].");
			logger.error("更新社区及论坛的用户密码失败，您可能无法用新密码登录社区及论坛.");
			result.setErrorMessage("更新社区及论坛的用户密码失败，您可能无法用新密码登录社区及论坛.");
			return result;
		}
		result.setSuccess(true);
		return result;
	}
	
	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword) {
		return changePassowrd(memberId,oldPassword,newPassword,true);
	}

	public UpdateMemberInfoResult changeMobile(String memberId,
			String oldMobile, String newMobile) {
		// TODO 1、判断入参是否为空
		// TODO 2、判断是否有该用户存在
		// TODO 3、判断用户状态，状态不正常不允许修改
		// TODO 4、判断旧手机是否正确
		// TODO 5、调用手机接口重新发送校验码
		// TODO 6、是否需要修改手机验证状态为未通过，同时前台不允许任何操作，直到激活为止，还是修改为需要重新验证
		return null;
	}

	public ActiveResult activeMember(String memberId, String activeCode) {
		// 1、判断入参是否为空
		ActiveResult result = new ActiveResult();
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(activeCode)) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("用户id,激活码不能为空");
			return result;
		}
		// 2、判断该用户是否存在
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(ActiveResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3、判断用户状态，状态不正常不允许修改
		if (!StringUtils.equals(member.getStatus(), Member.STATUS_NORMAL)) {
			result.setErrorCode(ActiveResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("用户状态为非正常状态，不允许修改密码");
			return result;
		}
		// 4、判断该用户手机验证状态是不是未验证状态
		if (StringUtils.equals(member.getPhoneValidate(),
				Member.MOBILE_VALIDATE_PASS)) {
			result.setErrorCode(ActiveResult.ERROR_HAS_ACTIVE);
			return result;
		}
		// 5、判断校验码是否正确
		MobileValidate mobileValidate = mobileValidateDAO
				.getValidateInfo(memberId);
		if (mobileValidate == null) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("该用户无相应激活信息，确认是否真的需要激活");
			return result;
		}
		if (!StringUtils.equals(activeCode, mobileValidate.getValidateCode())) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_CHECK_CODE);
			return result;
		}
		// 6、修改手机校验状态为通过
		memberDAO.updateMobileValidateStatus(memberId,
				Member.MOBILE_VALIDATE_PASS, memberId);
		result.setSuccess(true);
		return result;
	}

	public ActiveResult resendActiveCode(String memberId) {
		// 1、判断入参是否为空
		if (memberId == null) {
			throw new NullPointerException("memberId info can't be null");
		}
		ActiveResult result = new ActiveResult();

		// 2、判断该用户是否存在
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(ActiveResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3、判断该用户手机验证状态是不是未验证状态
		if (StringUtils.equals(member.getPhoneValidate(),
				Member.MOBILE_VALIDATE_PASS)) {
			result.setErrorCode(ActiveResult.ERROR_HAS_ACTIVE);
			return result;
		}
		// 4、判断校验码是否正确
		MobileValidate mobileValidate = mobileValidateDAO
				.getValidateInfo(memberId);

		if (mobileValidate == null) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("该用户无相应激活信息，确认是否真的需要激活");
			return result;
		}

		// 5、发送短消息
		smsService.sendSms(mobileValidate.getPhone(),
				getSmsMessage(mobileValidate.getValidateCode()));
		result.setSuccess(true);
		return result;
	}

	public UpdateMemberInfoResult updateMemberStatus(String memberId,
			String status, String modifier) {
		// 1、判断入参是否为空
		UpdateMemberInfoResult result = new UpdateMemberInfoResult();
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(status)) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("用户id,要修改的状态不能为空");
		}
		// 2、判断该用户是否存在，并且处于非删除状态
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_NO_MEMBER);
			return result;
		}
		if (StringUtils.equals(member.getStatus(), Member.STATUS_DELETED)) {
			result
					.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("该用户已删除，不能再做任何修改");
			return result;
		}
		// 4、删除操作判断用户是否已激活,如果已经激活，则不允许修改
		if (StringUtils.equals(status, Member.STATUS_DELETED)) {
			if (!StringUtils.equals(member.getPhoneValidate(),
					Member.MOBILE_VALIDATE_WAIT)) {
				result
						.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
				result.setErrorMessage("该用户已经激活，或者曾经被激活过，不能再做删除操作");
				return result;
			}
		}
		// 5、修改会员状态(由于是后台会员使用，所以不需要加很强的限制了)
		memberDAO.updateStatus(memberId, status, modifier);
		result.setSuccess(true);
		return result;
	}

	public List<MemberList> quickQueryShop(MemberQuery query) {
		// 1、入参检查
		if (query == null) {
			throw new NullPointerException("keyType or keyWord can't be null");
		}
		List<MemberList> memberShopList = null;
		// 2、1按会员信息搜索
		if (query.hasMemberQueryInfo()) {
			List memberList = memberDAO.quickQueryMember(query);
			if (memberList != null && memberList.size() > 0) {
				Map shopMap = shopDAO.queryShopByMember(memberList);
				memberShopList = mergeMemberInfo(memberList, shopMap);
			}
		}
		// 2、2按店铺信息搜索
		if (query.hasShopQueryInfo()) {
			Map shopMap = shopDAO.quickQueryShop(query);
			if (shopMap != null && shopMap.size() > 0) {
				List memberList = memberDAO.queryMemberByIds(shopMap);
				memberShopList = mergeMemberInfo(memberList, shopMap);
			}
		}
		return memberShopList;
	}

	public Map<String, Member> queryMemberMapByIds(String[] memberIds) {
		if (memberIds == null) {
			throw new NullPointerException("会员id不能为空");
		}
		return memberDAO.queryMemberMapByIds(memberIds);
	}
	
	public boolean emailIsUsed(String email) {
		return getMemberByEmail(email) != null;
	}

	public Member getMemberByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException("email can't be null or empty");
		}

		MemberQuery query = new MemberQuery();
		query.setEmail(email.trim());
		List memberList = memberDAO.quickQueryMember(query);

		return memberList == null ? null : (Member) memberList.get(0);
	}

	/**
	 * 拼凑列表显示
	 * 
	 * @param memberList
	 * @param shopMap
	 * @return
	 */
	private List<MemberList> mergeMemberInfo(List memberList, Map shopMap) {
		List<MemberList> memberMergeList = new ArrayList<MemberList>();
		for (int i = 0; i < memberList.size(); i++) {
			MemberList member = (MemberList) memberList.get(i);
			Shop shop = (Shop) shopMap.get(member.getMemberId());
			member.setShop(shop);
			memberMergeList.add(member);
		}
		return memberMergeList;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	/**
	 * 生成校验码，并且发送短消息
	 * 
	 * @param member
	 */
	private void sendActiveCode(String memberId, String mobile) {
		//修改生成校验码　2008/01/17 by qiujy
		String activeCode = null;
		// 1、生成校验码
		
				
		   activeCode = CheckCodeGenerator.generate();
			
		
		// 2、插入手机验证表数据
		MobileValidate mobileValidate = new MobileValidate();
		mobileValidate.setMemberId(memberId);
		mobileValidate.setPhone(mobile);
		mobileValidate.setValidateCode(activeCode);

		mobileValidateDAO.delete(memberId);
		mobileValidateDAO.insert(mobileValidate);
		// 3、发送短消息
		smsService.sendSms(mobile, getSmsMessage(activeCode));
	}

	/**
	 * 发送的短信内容（现在乱写写，以后可能会要改的）
	 * 
	 * @param activeCode
	 * @return
	 */
	private String getSmsMessage(String activeCode) {
		return "尊敬的会员您好，恭喜您成为YY网会员，您的手机验证码为" + activeCode
				+ "请登录网站后输入验证码激活您的手机。";
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setMobileValidateDAO(MobileValidateDAO mobileValidateDAO) {
		this.mobileValidateDAO = mobileValidateDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public UCenterService getUcenterService() {
		return ucenterService;
	}

	public void setUcenterService(UCenterService ucenterService) {
		this.ucenterService = ucenterService;
	}


	public String getForgetPasswordTemplate() {
		return forgetPasswordTemplate;
	}

	public void setForgetPasswordTemplate(String forgetPasswordTemplate) {
		this.forgetPasswordTemplate = forgetPasswordTemplate;
	}

	public UserCenterService getUserCenterService() {
		return userCenterService;
	}

	public void setUserCenterService(UserCenterService userCenterService) {
		this.userCenterService = userCenterService;
	}

	public IMailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(IMailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public UserCenter checkMemberCode(String verifyCode, ArrayList<Message> messageList) throws ServiceException{
		if(StringUtils.isBlank(verifyCode)||messageList==null){
			if(messageList==null){
				throw new ServiceException(resource.getString("common.param.invalid"));
			}
			Message message = new Message();
			message.setType(Message.ERROR);
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("common.param.invalid"));
			messageList.add(message);
			return null;
		}
		UserCenter record = this.userCenterService.getByVerifyCode(verifyCode);
		if(record==null){
			Message message = new Message();
			message.setType(Message.ERROR);
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("common.param.invalid"));
			messageList.add(message);
			return null;
		}
		UserCenter lastRecord = this.userCenterService.getLastRequest(record);
		if(!record.getVerifyCode().equals(lastRecord.getVerifyCode())){
			Message message = new Message();
			message.setType(Message.ERROR);
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("common.param.invalid"));
			messageList.add(message);
			return null;
		}
		return record;
	}

	public Message findLoginId(String mail,HashMap model)throws ServiceException {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[] { mail });
		mailInfo.setSubject(this.getLoginIdSubject);
	    mailEngine.sendNorml(mailInfo, this.forgetLoginIdTemplate, model);
	    
	    Message message = new Message();
	    message.setType(Message.INFO);
	    message.setTitle(resource.getString("common.info"));
	    message.setDetail(resource.getString("messageservice.getLoginId.success"));
	    
		return message;
	}
	

	public Message findPassword(String memberId,  String mail,
			String requestIp,HashMap model) throws ServiceException {
		Message message = null;
		if(memberId==null||StringUtils.isBlank(mail)||StringUtils.isBlank(requestIp)){
			message = new Message();
			message.setType(Message.ERROR);
			message.setTitle(resource.getString("common.error"));
			message.setDetail(resource.getString("common.param.invalid"));
			return message;
		}
		UserCenter userCenter = new UserCenter();
		userCenter.setFkMember(memberId);
		userCenter.setRequestIp(requestIp);
		userCenterService.addUserCenter(userCenter);
		model.put("code",userCenter.getVerifyCode());
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[] { mail });
		mailInfo.setSubject(getPasswordSubject);
	    mailEngine.sendNorml(mailInfo, this.forgetPasswordTemplate, model);
	    message = new Message();
	    message.setType(Message.INFO);
	    message.setTitle(resource.getString("common.info"));
	    message.setDetail(resource.getString("messageservice.getPassword.success"));
	    return message;
	}

	public String getGetPasswordSubject() {
		return getPasswordSubject;
	}

	public void setGetPasswordSubject(String getPasswordSubject) {
		this.getPasswordSubject = getPasswordSubject;
	}

	public String getGetLoginIdSubject() {
		return getLoginIdSubject;
	}

	public void setGetLoginIdSubject(String getLoginIdSubject) {
		this.getLoginIdSubject = getLoginIdSubject;
	}

	public String getForgetLoginIdTemplate() {
		return forgetLoginIdTemplate;
	}

	public void setForgetLoginIdTemplate(String forgetLoginIdTemplate) {
		this.forgetLoginIdTemplate = forgetLoginIdTemplate;
	}

	public Member getMemberByLoginId(String loginId) throws ServiceException {
		
		return memberDAO.getMemberByLoginId(loginId);
	}
	
	public Boolean checkToken(String token) {
		try{
			String tokenTime = new String(TokenUtils.decode(TokenUtils.hex2byte(token), TokenUtils.key));
			Date validDate = DateUtils.getDiffCurrentDate(0-TOKEN_VALID_DAY);
			long tokenTimeStamp = Long.parseLong(tokenTime);
			if(validDate.getTime()>tokenTimeStamp){
				return false;
			}
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public String generateToken() {
		Date currentTime = new Date();
		try{
			String token = TokenUtils.byte2hex(TokenUtils.encode(String.valueOf(currentTime.getTime()).getBytes(), TokenUtils.key));
			return token;
		}catch(Exception e){
			logger.error("Fail to generate token for member service",e);
		}
		return null;
	}

	public String getSignUpTemplate() {
		return signUpTemplate;
	}

	public void setSignUpTemplate(String signUpTemplate) {
		this.signUpTemplate = signUpTemplate;
	}
	
}
