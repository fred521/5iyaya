package com.nonfamous.tang.dao.home.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.MobileValidateDAO;
import com.nonfamous.tang.domain.MobileValidate;

/**
 * <p>
 * 手机验证DAO的实现类
 * </p>
 * 
 * @author:daodao
 * @version $Id: IbatisMobileValidateDAO.java,v 1.1 2007/06/03 12:36:40 sunzy
 *          Exp $
 */
public class IbatisMobileValidateDAO extends SqlMapClientDaoSupport implements
		MobileValidateDAO {

	public static final String SPACE = "MOBILEVALIDATE_SPACE.";

	public MobileValidate getValidateInfo(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return (MobileValidate) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_MOBILEVALIDATE_BY_MEMBERID", memberId);
	}

	public void delete(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		this.getSqlMapClientTemplate().delete(
				SPACE + "DELETE_MOBILEVALIDATE_BY_MEMBERID", memberId);
	}

	public void insert(MobileValidate mobile) {
		if (mobile == null) {
			throw new NullPointerException("mobile info can't be null");
		}
		this.getSqlMapClientTemplate().insert(SPACE + "MOBILE_VALIDATE_INSER",
				mobile);
	}
}
