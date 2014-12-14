package com.nonfamous.tang.dao.home;

import com.nonfamous.tang.domain.MobileValidate;

/**
 * <p>
 * 手机校验DAO
 * </p>
 * 
 * @author:daodao
 * @version $Id: MobileValidateDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MobileValidateDAO {

	/**
	 * 根据会员id获取手机验证数据
	 * 
	 * @param memberId
	 * @return
	 */
	public MobileValidate getValidateInfo(String memberId);

	/**
	 * 删除会员手机验证数据
	 * 
	 * @param memberId
	 */
	public void delete(String memberId);

	/**
	 * 插入会员手机验证数据
	 * 
	 * @param mobile
	 */
	public void insert(MobileValidate mobile);

}
