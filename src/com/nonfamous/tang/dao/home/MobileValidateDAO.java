package com.nonfamous.tang.dao.home;

import com.nonfamous.tang.domain.MobileValidate;

/**
 * <p>
 * �ֻ�У��DAO
 * </p>
 * 
 * @author:daodao
 * @version $Id: MobileValidateDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MobileValidateDAO {

	/**
	 * ���ݻ�Աid��ȡ�ֻ���֤����
	 * 
	 * @param memberId
	 * @return
	 */
	public MobileValidate getValidateInfo(String memberId);

	/**
	 * ɾ����Ա�ֻ���֤����
	 * 
	 * @param memberId
	 */
	public void delete(String memberId);

	/**
	 * �����Ա�ֻ���֤����
	 * 
	 * @param mobile
	 */
	public void insert(MobileValidate mobile);

}
