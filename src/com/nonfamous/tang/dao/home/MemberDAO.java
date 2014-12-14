package com.nonfamous.tang.dao.home;

import java.util.List;
import java.util.Map;

import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Member;

/**
 * <p>
 * ��Ա��Ϣ����DAO
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MemberDAO {

	/**
	 * ͳ��һ����Ա���Ѿ�ע����û���
	 * 
	 * @param loginId
	 * @return
	 */
	public int countSameMember(String loginId, String memberId);

	/**
	 * ͳ��һ���ֻ���ע��Ļ�Ա��
	 * 
	 * @param mobile
	 * @return
	 */
	public int countSameMobile(String mobile, String memberId);

	/**
	 * �²���һ����Ա����
	 * 
	 * @param member
	 */
	public void insert(Member member);

	/**
	 * ���ݵ�¼��Ա����ȡ��Ա��Ϣ
	 * 
	 * @param loginAccount
	 * @return
	 */
	public Member getMemberByLoginId(String loginAccount);

	/**
	 * �޸��û���¼��Ϣ����������¼ip��
	 * 
	 * @param loginIp
	 */
	public void updateLoginInfo(String memberId, String loginIp);

	/**
	 * ��ȡ�����������û��б�
	 * 
	 * @param query
	 * @return
	 */
	public List queryMemberList(MemberQuery query);

	/**
	 * ���ݻ�Աid��ȡ��Ա��Ϣ
	 * 
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId);

	/**
	 * ���»�Ա��Ϣ
	 * 
	 * @param member
	 * @return
	 */
	public int updateMemberInfo(Member member);

	/**
	 * ���»�Ա�ֻ���֤״̬
	 * 
	 * @param memberId
	 * @param mobile_validate_pass
	 * @param modifier
	 */
	public void updateMobileValidateStatus(String memberId,
			String mobile_validate_pass, String modifier);

	/**
	 * ���»�Ա״̬
	 * 
	 * @param memberId
	 * @param status
	 * @param modifier
	 */
	public void updateStatus(String memberId, String status, String modifier);

	/**
	 * ����ɾ����Ա����ʵҵ����Ӧ���ǲ����õ�
	 * 
	 * @param memberId
	 */
	public void deleteMember(String memberId);
	
	/**
	 * ����query����Ϣ��ȡmember���б�����ҳ
	 * @param query
	 * @return
	 */
	public List quickQueryMember(MemberQuery query);
	
	/**
	 * ���ݵ�����Ϣ�е�memberId��ȡ��Ա��Ϣ
	 * @param shopMap
	 * @return
	 */
	public List queryMemberByIds(Map shopMap);
	
	/**
	 * ��ȡ��memberIdΪkey��member map
	 * @param memberIds
	 * @return
	 */
	public Map<String,Member> queryMemberMapByIds(String[] memberIds);
	
	/**
	 * �޸��������
	 * @param memberId
	 * @param md5Password
	 * @param modifier
	 */
	public void updatePassword(String memberId, String md5Password, String modifier);

}
