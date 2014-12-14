package com.nonfamous.tang.dao.home;

import java.util.List;
import java.util.Map;

import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Member;

/**
 * <p>
 * 会员信息操作DAO
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MemberDAO {

	/**
	 * 统计一个会员名已经注册的用户数
	 * 
	 * @param loginId
	 * @return
	 */
	public int countSameMember(String loginId, String memberId);

	/**
	 * 统计一个手机号注册的会员数
	 * 
	 * @param mobile
	 * @return
	 */
	public int countSameMobile(String mobile, String memberId);

	/**
	 * 新插入一条会员数据
	 * 
	 * @param member
	 */
	public void insert(Member member);

	/**
	 * 根据登录会员名获取会员信息
	 * 
	 * @param loginAccount
	 * @return
	 */
	public Member getMemberByLoginId(String loginAccount);

	/**
	 * 修改用户登录信息，包括最后登录ip等
	 * 
	 * @param loginIp
	 */
	public void updateLoginInfo(String memberId, String loginIp);

	/**
	 * 获取符合条件的用户列表
	 * 
	 * @param query
	 * @return
	 */
	public List queryMemberList(MemberQuery query);

	/**
	 * 根据会员id获取会员信息
	 * 
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId);

	/**
	 * 更新会员信息
	 * 
	 * @param member
	 * @return
	 */
	public int updateMemberInfo(Member member);

	/**
	 * 更新会员手机验证状态
	 * 
	 * @param memberId
	 * @param mobile_validate_pass
	 * @param modifier
	 */
	public void updateMobileValidateStatus(String memberId,
			String mobile_validate_pass, String modifier);

	/**
	 * 跟新会员状态
	 * 
	 * @param memberId
	 * @param status
	 * @param modifier
	 */
	public void updateStatus(String memberId, String status, String modifier);

	/**
	 * 物理删除会员，真实业务中应该是不会用的
	 * 
	 * @param memberId
	 */
	public void deleteMember(String memberId);
	
	/**
	 * 根据query的信息获取member的列表，不分页
	 * @param query
	 * @return
	 */
	public List quickQueryMember(MemberQuery query);
	
	/**
	 * 根据店铺信息中的memberId获取会员信息
	 * @param shopMap
	 * @return
	 */
	public List queryMemberByIds(Map shopMap);
	
	/**
	 * 获取以memberId为key的member map
	 * @param memberIds
	 * @return
	 */
	public Map<String,Member> queryMemberMapByIds(String[] memberIds);
	
	/**
	 * 修改密码操作
	 * @param memberId
	 * @param md5Password
	 * @param modifier
	 */
	public void updatePassword(String memberId, String md5Password, String modifier);

}
