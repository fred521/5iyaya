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
 * ��Ա�������ʵ����
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
	
	private String getPasswordSubject="��Ѿ������������ȷ�����������޸�";
	
	private String getLoginIdSubject = "��Ѿװ�������������μ����ĵ�¼ID";

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
		// 1���ж�����Ƿ�Ϊ��
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		NewMemberResult result = new NewMemberResult();
		//20070910ȡ���ֻ��ű�����ж�
		/*
		if (StringUtils.isEmpty(member.getMobile())) {
			result.setErrorCode(NewMemberResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("�ֻ����벻��Ϊ��");
			return result;
		}
		*/
		// 2���жϸû�Ա�Ƿ��Ѿ���ע��
		int memberCount = memberDAO.countSameMember(member.getLoginId(), null);
		if (memberCount > 0) {
			result.setErrorCode(NewMemberResult.ERROR_MEMBER_EXIST);
			return result;
		}
		// 3���ж��ֻ��Ƿ��Ѿ���ע���
		// ���ҵ��ֻ�����Ϊ�գ�faint
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
		// ��ע����û��û�״̬Ϊ����
		member.setStatus(Member.STATUS_NORMAL);
		if (StringUtils.equals(member.getMemberType(), Member.FLAG_BUYER)) {
			//TODO currently we do not need this. maybe in future 
			//change the old requirement: whatever the member is buyer or seller, the member should be validate pass  
			member.setPhoneValidate(Member.MOBILE_VALIDATE_PASS);
			creator = memberId;
		}
		// 4�������û���Ϣ
		member.setCreator(creator);
		member.setModifier(creator);
		member.setMemberId(memberId);
		memberDAO.insert(member);
		// 5��������������У���룬���ҷ��Ͷ���Ϣ
		if (StringUtils.equals(member.getMemberType(), Member.FLAG_BUYER)) {
			//TODO currently we do not need this. maybe in future 
			//sendActiveCode(member.getMemberId(), member.getMobile());
		}
		// 6�������û�ID
		result.setMemberId(memberId);
		result.setSuccess(true);
		return result;
	}

	/**
	 * �������ң������Ƿ���Ҫͬʱ����������Ϣ
	 * 
	 * @param seller
	 * @param isNeedAddShop
	 * @return
	 */
	private NewMemberResult addSeller(Seller seller, boolean isNeedAddShop,
			String creator) {
		// 1���ж�����Ƿ�Ϊ��
		if (seller == null) {
			throw new NullPointerException("seller info can't be null");
		}
		// 2������Ҫͬ������������Ϣʱ���жϵ�����Ϣ�Ƿ�Ϊ��
		if (isNeedAddShop && seller.getShop() == null) {
			throw new NullPointerException("shop info can't be null");
		}
		// 3������addMember����
		NewMemberResult result = addMember(seller, creator);
		if (result.isSuccess()) {
			// TODO 4������Ҫͬʱ����������Ϣʱ�����õ��̷�������������Ϣ
			if (isNeedAddShop) {

			}
		}
		return result;
	}

	public LoginResult login(String loginAccount, String password,
			String loginIp) {
		LoginResult result = new LoginResult();
		// 1���ж�����Ƿ�Ϊ��
		if (StringUtils.isEmpty(loginAccount) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(loginIp)) {
			result.setErrorCode(LoginResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("��¼id,���룬��¼ip����Ϊ��");
			return result;
		}
		// 2���ж��Ƿ��и��û�����
		Member loginMember = memberDAO.getMemberByLoginId(loginAccount);
		if (loginMember == null) {
			result.setErrorCode(LoginResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3���ж������Ƿ���ȷ
		if (!StringUtils.equals(loginMember.getLoginPassword(), MD5Encrypt
				.encode(password))) {
			result.setErrorCode(LoginResult.ERROR_PASS_ERROR);
			return result;
		}
		// 4���ж��û�״̬�Ƿ�Ϊ����״̬(N)
		if (!StringUtils.equals(loginMember.getStatus(), Member.STATUS_NORMAL)) {
			result.setErrorCode(LoginResult.ERROR_MEMBER_STATUS);
			return result;
		}
		// 5��һ�����������������޸�ʱ�䣬����¼ʱ��,����¼ip,���ӵ�¼����
		memberDAO.updateLoginInfo(loginMember.getMemberId(), loginIp);
		// 6�������û���Ϣ����
		result.setMember(loginMember);
		result.setSuccess(true);
		return result;
	}

	public List queryMember(MemberQuery query) {
		// 1���ж�����Ƿ�Ϊ��
		if (query == null) {
			throw new NullPointerException("query info can't be null");
		}
		// 2����ѯ�û��б�����
		return memberDAO.queryMemberList(query);
	}

	public Member getMemberById(String memberId) {
		// 1���ж�����Ƿ�Ϊ��
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		// 2����ѯ�û���ϸ��Ϣ
		return memberDAO.getMemberById(memberId);
	}

	public Seller getSellerByShopId(String shopId) {
		// TODO 1���ж�����Ƿ�Ϊ��
		// TODO 2�����õ��̽ӿڣ���ȡ���������Ϣ���õ�memberId
		// TODO 3������getMemberById��ȡ��Ա��Ϣ
		// TODO 4�����û���Ϣ�м����Ѿ���ȡ�ĵ�����Ϣ
		// TODO 5�������û���Ϣ
		return null;
	}

	public UpdateMemberInfoResult updateMemberInfo(Member member,
			String modifier) {
		// 1���ж�����Ƿ�Ϊ��
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		UpdateMemberInfoResult result = new UpdateMemberInfoResult();
		member.setModifier(modifier);
		Member oldMember = memberDAO.getMemberById(member.getMemberId());
		// 2���ж��û��Ƿ����
		if (oldMember == null) {
			result.setErrorCode(LoginResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3���ж��޸ĵ��ֻ������Ƿ�Ķ���
		// ����ֻ�Ϊ�գ������κ�����
		if (StringUtils.isNotBlank(member.getMobile())) {
			if (!StringUtils.equals(member.getMobile(), oldMember.getMobile())) {
				int mobileCount = memberDAO.countSameMobile(member.getMobile(),
						member.getMemberId());
				// 3.1�Ƿ���ֻ��Ѿ���������ʹ��
				if (mobileCount > 0) {
					result
							.setErrorCode(UpdateMemberInfoResult.ERROR_MOBILE_EXIST);
					return result;
				}
				//Jason ��1/8/2009ע�͵���
				// 3.2���������û�����Ҫ����֤״̬��Ϊ�ȴ���֤���������·�����֤�룬���һ�Ա����Ҫ
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
		// 4�������û���Ϣ
		memberDAO.updateMemberInfo(member);
		
		// 5. ����ucenter�û�����
		/* �û����ܸ���email
		try{
			String username = member.getLoginId();
			// �ж��û��Ƿ��Ѿ���ucenter��ע���
			Map<String, String> user = ucenterService.userExists(username);
			if (user.size() > 0) { // �û��Ѿ���ucenter��ע�����
				int res = ucenterService.userUpdate(username, member.getLoginPassword(), null, 
					member.getEmail(), 
					false);
				if(logger.isInfoEnabled()){
					logger.info("����ucenter�û����ϳɹ�.");
				}
			}
		}
		catch(UCenterException e){
			logger.error("����ucenter�û�����ʧ��, error code [" + e.getCode() + "].");
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
		// 1���ж�����Ƿ�Ϊ��
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(oldPassword)
				|| StringUtils.isEmpty(newPassword)) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("�û�id,�����룬��Ҫ�޸ĵ����벻��Ϊ��");
			return result;
		}
		// 2���ж��Ƿ��и��û�����
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3���ж��û�״̬��״̬�������������޸�
		if (!StringUtils.equals(member.getStatus(), Member.STATUS_NORMAL)) {
			result
					.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("�û�״̬Ϊ������״̬���������޸�����");
			return result;
		}
		if(isNeedCheck){
			// 4���жϾ������Ƿ���ȷ
			if (!StringUtils.equals(MD5Encrypt.encode(oldPassword), member
					.getLoginPassword())) {
				result.setErrorCode(UpdateMemberInfoResult.ERROR_OLD_PASSWORD);
				return result;
			}
		}
		// 5���޸�����
		
		String md5Password = MD5Encrypt.encode(newPassword);
		memberDAO.updatePassword(memberId, md5Password, memberId);
		
		// 6. ����ucenter���û�����
		try{
			String username = member.getLoginId();
			// �ж��û��Ƿ��Ѿ���ucenter��ע���
			Map<String, String> user = ucenterService.userExists(username);
			if (user.size() > 0) { // �û��Ѿ���ucenter��ע�����
				int res = ucenterService.userUpdate(username, oldPassword, newPassword, 
						member.getEmail()/*������email*/, 
					false/*�����û�������Ҫ��֤����*/);
				if(logger.isInfoEnabled()){
					logger.info("�����û�����ɹ�.");
				}
			}
		}
		catch(UCenterException e){
			logger.error("�����û�����ʧ��, error code [" + e.getCode() + "].");
			logger.error("������������̳���û�����ʧ�ܣ��������޷����������¼��������̳.");
			result.setErrorMessage("������������̳���û�����ʧ�ܣ��������޷����������¼��������̳.");
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
		// TODO 1���ж�����Ƿ�Ϊ��
		// TODO 2���ж��Ƿ��и��û�����
		// TODO 3���ж��û�״̬��״̬�������������޸�
		// TODO 4���жϾ��ֻ��Ƿ���ȷ
		// TODO 5�������ֻ��ӿ����·���У����
		// TODO 6���Ƿ���Ҫ�޸��ֻ���֤״̬Ϊδͨ����ͬʱǰ̨�������κβ�����ֱ������Ϊֹ�������޸�Ϊ��Ҫ������֤
		return null;
	}

	public ActiveResult activeMember(String memberId, String activeCode) {
		// 1���ж�����Ƿ�Ϊ��
		ActiveResult result = new ActiveResult();
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(activeCode)) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("�û�id,�����벻��Ϊ��");
			return result;
		}
		// 2���жϸ��û��Ƿ����
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(ActiveResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3���ж��û�״̬��״̬�������������޸�
		if (!StringUtils.equals(member.getStatus(), Member.STATUS_NORMAL)) {
			result.setErrorCode(ActiveResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("�û�״̬Ϊ������״̬���������޸�����");
			return result;
		}
		// 4���жϸ��û��ֻ���֤״̬�ǲ���δ��֤״̬
		if (StringUtils.equals(member.getPhoneValidate(),
				Member.MOBILE_VALIDATE_PASS)) {
			result.setErrorCode(ActiveResult.ERROR_HAS_ACTIVE);
			return result;
		}
		// 5���ж�У�����Ƿ���ȷ
		MobileValidate mobileValidate = mobileValidateDAO
				.getValidateInfo(memberId);
		if (mobileValidate == null) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("���û�����Ӧ������Ϣ��ȷ���Ƿ������Ҫ����");
			return result;
		}
		if (!StringUtils.equals(activeCode, mobileValidate.getValidateCode())) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_CHECK_CODE);
			return result;
		}
		// 6���޸��ֻ�У��״̬Ϊͨ��
		memberDAO.updateMobileValidateStatus(memberId,
				Member.MOBILE_VALIDATE_PASS, memberId);
		result.setSuccess(true);
		return result;
	}

	public ActiveResult resendActiveCode(String memberId) {
		// 1���ж�����Ƿ�Ϊ��
		if (memberId == null) {
			throw new NullPointerException("memberId info can't be null");
		}
		ActiveResult result = new ActiveResult();

		// 2���жϸ��û��Ƿ����
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(ActiveResult.ERROR_NO_MEMBER);
			return result;
		}
		// 3���жϸ��û��ֻ���֤״̬�ǲ���δ��֤״̬
		if (StringUtils.equals(member.getPhoneValidate(),
				Member.MOBILE_VALIDATE_PASS)) {
			result.setErrorCode(ActiveResult.ERROR_HAS_ACTIVE);
			return result;
		}
		// 4���ж�У�����Ƿ���ȷ
		MobileValidate mobileValidate = mobileValidateDAO
				.getValidateInfo(memberId);

		if (mobileValidate == null) {
			result.setErrorCode(ActiveResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("���û�����Ӧ������Ϣ��ȷ���Ƿ������Ҫ����");
			return result;
		}

		// 5�����Ͷ���Ϣ
		smsService.sendSms(mobileValidate.getPhone(),
				getSmsMessage(mobileValidate.getValidateCode()));
		result.setSuccess(true);
		return result;
	}

	public UpdateMemberInfoResult updateMemberStatus(String memberId,
			String status, String modifier) {
		// 1���ж�����Ƿ�Ϊ��
		UpdateMemberInfoResult result = new UpdateMemberInfoResult();
		if (StringUtils.isEmpty(memberId) || StringUtils.isEmpty(status)) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("�û�id,Ҫ�޸ĵ�״̬����Ϊ��");
		}
		// 2���жϸ��û��Ƿ���ڣ����Ҵ��ڷ�ɾ��״̬
		Member member = memberDAO.getMemberById(memberId);
		if (member == null) {
			result.setErrorCode(UpdateMemberInfoResult.ERROR_NO_MEMBER);
			return result;
		}
		if (StringUtils.equals(member.getStatus(), Member.STATUS_DELETED)) {
			result
					.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
			result.setErrorMessage("���û���ɾ�������������κ��޸�");
			return result;
		}
		// 4��ɾ�������ж��û��Ƿ��Ѽ���,����Ѿ�����������޸�
		if (StringUtils.equals(status, Member.STATUS_DELETED)) {
			if (!StringUtils.equals(member.getPhoneValidate(),
					Member.MOBILE_VALIDATE_WAIT)) {
				result
						.setErrorCode(UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
				result.setErrorMessage("���û��Ѿ�����������������������������ɾ������");
				return result;
			}
		}
		// 5���޸Ļ�Ա״̬(�����Ǻ�̨��Աʹ�ã����Բ���Ҫ�Ӻ�ǿ��������)
		memberDAO.updateStatus(memberId, status, modifier);
		result.setSuccess(true);
		return result;
	}

	public List<MemberList> quickQueryShop(MemberQuery query) {
		// 1����μ��
		if (query == null) {
			throw new NullPointerException("keyType or keyWord can't be null");
		}
		List<MemberList> memberShopList = null;
		// 2��1����Ա��Ϣ����
		if (query.hasMemberQueryInfo()) {
			List memberList = memberDAO.quickQueryMember(query);
			if (memberList != null && memberList.size() > 0) {
				Map shopMap = shopDAO.queryShopByMember(memberList);
				memberShopList = mergeMemberInfo(memberList, shopMap);
			}
		}
		// 2��2��������Ϣ����
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
			throw new NullPointerException("��Աid����Ϊ��");
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
	 * ƴ���б���ʾ
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
	 * ����У���룬���ҷ��Ͷ���Ϣ
	 * 
	 * @param member
	 */
	private void sendActiveCode(String memberId, String mobile) {
		//�޸�����У���롡2008/01/17 by qiujy
		String activeCode = null;
		// 1������У����
		
				
		   activeCode = CheckCodeGenerator.generate();
			
		
		// 2�������ֻ���֤������
		MobileValidate mobileValidate = new MobileValidate();
		mobileValidate.setMemberId(memberId);
		mobileValidate.setPhone(mobile);
		mobileValidate.setValidateCode(activeCode);

		mobileValidateDAO.delete(memberId);
		mobileValidateDAO.insert(mobileValidate);
		// 3�����Ͷ���Ϣ
		smsService.sendSms(mobile, getSmsMessage(activeCode));
	}

	/**
	 * ���͵Ķ������ݣ�������дд���Ժ���ܻ�Ҫ�ĵģ�
	 * 
	 * @param activeCode
	 * @return
	 */
	private String getSmsMessage(String activeCode) {
		return "�𾴵Ļ�Ա���ã���ϲ����ΪYY����Ա�������ֻ���֤��Ϊ" + activeCode
				+ "���¼��վ��������֤�뼤�������ֻ���";
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
