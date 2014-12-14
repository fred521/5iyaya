package com.nonfamous.tang.service.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Buyer;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Seller;
import com.nonfamous.tang.domain.UserCenter;
import com.nonfamous.tang.domain.message.Message;
import com.nonfamous.tang.domain.result.ActiveResult;
import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.exception.ServiceException;

/**
 * <p>
 * ��Ա����ӿ��࣬�����˴󲿷ֵĻ�Ա����
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberService.java,v 1.3 2009/08/15 10:05:25 andy Exp $
 */
public interface MemberService {
	/**
	 * ����һ����һ�Ա
	 * 
	 * @param buyer
	 * @return
	 */
	public NewMemberResult addBuyer(Buyer buyer);

	/**
	 * ����һ�����һ�Ա
	 * 
	 * @param seller
	 * @param creator
	 * @return
	 */
	public NewMemberResult addSeller(Seller seller, String creator);

	/**
	 * �������һ�Ա����Ϊ�䴴�����̣�Ŀǰ�����ò�����
	 * 
	 * @param seller
	 * @param creator
	 * @return
	 */
	public NewMemberResult addSellerAndShop(Seller seller, String creator);

	/**
	 * ����һ����Ա��������һ�������
	 * 
	 * @param member
	 * @param creator
	 * @return
	 */
	public NewMemberResult addMember(Member member, String creator);

	/**
	 * ��Ա��¼
	 * 
	 * @param loginAccount
	 * @param password
	 * @param loginIp
	 * @return
	 */
	public LoginResult login(String loginAccount, String password,
			String loginIp);

	/**
	 * ��ѯ��Ա�б������߼�������
	 * 
	 * @param query
	 * @return
	 */
	public List queryMember(MemberQuery query);
	
	/**
	 * ��ȡ��memberIdΪkey��member map
	 * @param memberIds
	 * @return
	 */
	public Map queryMemberMapByIds(String[] memberIds);

	/**
	 * ���ݻ�Աid��ȡ��Ա��Ϣ
	 * 
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId);

	/**
	 * ���ݵ��̵�id��ȡ������Ϣ(��Ϊһ������ֻ��������ֻ��һ�����̣����Կ����������㣬Ŀǰ����Ҳ�ò���)
	 * 
	 * @param shopId
	 * @return
	 */
	public Seller getSellerByShopId(String shopId);

	/**
	 * �޸Ļ�Ա��Ϣ
	 * 
	 * @param member
	 * @return
	 */
	public UpdateMemberInfoResult updateMemberInfo(Member member,
			String modifier);

	/**
	 * ������¼����
	 * 
	 * @param memberId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword);
	/**
	 * �趨��־λ�Ǹ�����¼���뻹����������
	 * @param memberId
	 * @param oldPassword
	 * @param newPassword
	 * @param isNeedCheck
	 * @return
	 */
	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword,boolean isNeedCheck);
	/**
	 * ������Ա�ֻ����ݲ�ʵ�֣��������ܻ�ŵ�ר�ŵ��ֻ��������У�
	 * 
	 * @param memberId
	 * @param oldMobile
	 * @param newMobile
	 * @return
	 */
	public UpdateMemberInfoResult changeMobile(String memberId,
			String oldMobile, String newMobile);

	/**
	 * �����Ա
	 * 
	 * @param memberId
	 * @param activeCode
	 * @return
	 */
	public ActiveResult activeMember(String memberId, String activeCode);

	/**
	 * �ط�У����
	 * 
	 * @param memberId
	 * @return
	 */
	public ActiveResult resendActiveCode(String memberId);

	/**
	 * �޸Ļ�Ա״̬�������Ǻ�̨ʹ�ã�����Ҫ��̫�������
	 * 
	 * @param memberId
	 * @param status
	 */
	public UpdateMemberInfoResult updateMemberStatus(String memberId,
			String status, String modifier);
	
	
	/**
	 * ��Ա��Ϣ��������
	 * @param query
	 * @return
	 */
	public List quickQueryShop(MemberQuery query);
	
	public boolean emailIsUsed(String email);

	public Member getMemberByEmail(String email);
	
	/**
	 * 
	 * @param memberId ��һ����̼� ID
	 * @param memberType ��ң��̼ұ�־
	 * @param mail ����ʼ���ַ
	 * @param requestIp �������õ�IP
	 * @param model �ʼ�ģ������
	 * @return �������Ϣ
	 */
	public Message findPassword(String memberId,String mail,String requestIp,HashMap model)throws ServiceException;
	
	/**
	 * 
	 * @param memberId ���ܹ��ķ��͵������memberId
	 * @param memberType ��ң��̼����ͱ�־
	 * @param code ���ܹ����͵������code
	 * @param messageList �洢��ҳ�����Ϣ�б�
	 * @return �޸�����������Ч�򷵻�null,���򷵻ؾ����UserCenter����
	 */
	public UserCenter checkMemberCode(String verifyCode, ArrayList<Message> messageList)throws ServiceException;
	
	/**
	 * 
	 * @param loginId �û���¼��ID
	 * @return 
	 */
	public Message findLoginId(String mail,HashMap model)throws ServiceException;
	
	/**
	 * ͨ��loginId�����û�
	 * @param loginId
	 * @return
	 * @throws ServiceException
	 */
	public Member getMemberByLoginId(String loginId)throws ServiceException;
	
	/**
	 * �����û��޸���������
	 * @return
	 */
	public String generateToken();
	
	/**
	 * ��������Ƿ���Ч
	 * @param token
	 * @return
	 */
	public Boolean checkToken(String token);
	
}
