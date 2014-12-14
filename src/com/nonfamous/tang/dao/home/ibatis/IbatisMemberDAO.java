package com.nonfamous.tang.dao.home.ibatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Member;

/**
 * <p>
 * 会员DAO实现类
 * </p>
 * 
 * @author:daodao
 * @version $Id: IbatisMemberDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisMemberDAO extends SqlMapClientDaoSupport implements
		MemberDAO {

	public static final String SPACE = "MEMBER_SPACE.";

	public int countSameMember(String loginId, String memberId) {
		if (loginId == null) {
			throw new NullPointerException("loginId can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("loginId", loginId);
		param.put("memberId", memberId);
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "COUNT_SAME_MEMBER", param);
		return count.intValue();
	}

	public int countSameMobile(String mobile, String memberId) {
		if (mobile == null) {
			throw new NullPointerException("mobile can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("mobile", mobile);
		param.put("memberId", memberId);
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "COUNT_SAME_MOBILE", param);
		return count.intValue();
	}

	public void insert(Member member) {
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		this.getSqlMapClientTemplate().insert(SPACE + "MEMBER_INSERT", member);
	}

	public Member getMemberByLoginId(String loginAccount) {
		if (loginAccount == null) {
			throw new NullPointerException("loginId can't be null");
		}
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_MEMBER_BY_LOGIN_ID", loginAccount);
	}

	public void updateLoginInfo(String memberId, String loginIp) {
		if (memberId == null || loginIp == null) {
			throw new NullPointerException("memberId or loginIp can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("modifier", memberId);
		param.put("lastLoginIp", loginIp);
		param.put("memberId", memberId);
		this.getSqlMapClientTemplate().update(
				SPACE + "MEMBER_UPDATE_LOGIN_INFO", param);
	}

	public List queryMemberList(MemberQuery query) {
		if (query == null) {
			throw new NullPointerException("query info can't be null");
		}
		List memberList = null;
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "QUERY_MEMBER_COUNT", query);
		query.setTotalItem(total);
		if (total != null && total.intValue() > 0) {
			String queryString = SPACE + "QUERY_MEMBER_LIST_BUYER";
			if (StringUtils.equals(query.getMemberType(), Member.FLAG_SELLER)) {
				queryString = SPACE + "QUERY_MEMBER_LIST_SELLER";
			}
			memberList = this.getSqlMapClientTemplate().queryForList(
					queryString, query);
		}
		return memberList;
	}

	public Member getMemberById(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_MEMBER_BY_ID", memberId);
	}

	public int updateMemberInfo(Member member) {
		if (member == null) {
			throw new NullPointerException("member info can't be null");
		}
		return this.getSqlMapClientTemplate().update(
				SPACE + "MEMBER_UPDATE_INFO", member);
	}

	public void updateMobileValidateStatus(String memberId,
			String mobile_validate_pass, String modifier) {
		if (memberId == null || mobile_validate_pass == null
				|| modifier == null) {
			throw new NullPointerException(
					"memberId or mobile_validate_pass or modifier can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("modifier", modifier);
		param.put("memberId", memberId);
		param.put("phoneValidate", mobile_validate_pass);
		this.getSqlMapClientTemplate().update(
				SPACE + "MEMBER_UPDATE_MOBILE_VALIDATE_STATUS", param);
	}

	public void updateStatus(String memberId, String status, String modifier) {
		if (memberId == null || status == null || modifier == null) {
			throw new NullPointerException(
					"memberId or status or modifier can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("modifier", modifier);
		param.put("memberId", memberId);
		param.put("status", status);
		this.getSqlMapClientTemplate().update(SPACE + "MEMBER_UPDATE_STATUS",
				param);
	}

	public void deleteMember(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		this.getSqlMapClientTemplate().delete(SPACE + "DELETE_MEMBER_BY_ID",
				memberId);
	}

	public List quickQueryMember(MemberQuery query) {
		if (query == null) {
			throw new NullPointerException("query info can't be null");
		}
		List memberList = null;
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "GET_QUERY_MEMBER_COUNT_BY_QUERY_INFO",
						query);
		query.setTotalItem(total);
		if (total != null && total.intValue() > 0) {
			memberList = this.getSqlMapClientTemplate().queryForList(
					SPACE + "GET_QUERY_MEMBER_BY_QUERY_INFO", query);
		}
		return memberList;
	}

	public List queryMemberByIds(Map shopMap) {
		if (shopMap == null) {
			throw new NullPointerException("shopMap can't be null");
		}
		Map<String, Object[]> param = new HashMap<String, Object[]>();
		param.put("memberIds", shopMap.keySet().toArray());
		return this.getSqlMapClientTemplate().queryForList(
				SPACE + "GET_QUERY_MEMBER_BY_SHOP_INFO", param);
	}

	public void updatePassword(String memberId, String md5Password,
			String modifier) {
		if (memberId == null || md5Password == null || modifier == null) {
			throw new NullPointerException("update password info can't be null");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("modifier", modifier);
		param.put("loginPassword", md5Password);
		param.put("memberId", memberId);
		this.getSqlMapClientTemplate().update(SPACE + "MEMBER_UPDATE_PASSWORD",
				param);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Member> queryMemberMapByIds(String[] memberIds) {
		if (memberIds == null) {
			throw new NullPointerException("memberIds info can't be null");
		}
		if (memberIds.length == 0) {
			return Collections.EMPTY_MAP;
		}
		Map<String, String[]> param = new HashMap<String, String[]>();
		param.put("memberIds", memberIds);
		return this.getSqlMapClientTemplate().queryForMap(
				SPACE + "GET_QUERY_MEMBER_BY_MEMBER_IDS", param, "memberId");

	}

}
