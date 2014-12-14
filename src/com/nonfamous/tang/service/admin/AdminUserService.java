package com.nonfamous.tang.service.admin;

import java.util.List;

import com.nonfamous.tang.domain.AdminUser;

/**
 * @author eyeieye
 * @version $Id: AdminUserService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public interface AdminUserService {
	/**
	 * ����Ա��¼
	 * 
	 * @param name
	 *            ����Ϊ��
	 * @param password
	 *            ����Ϊ��
	 * @return ��¼�ɹ��Ĺ���Ա
	 */
	public AdminUser login(String name, String password);

	/**
	 * ����id�õ�����Ա
	 * 
	 * @param adminUserId
	 *            ����Ϊ��
	 * @return ����Ա
	 */
	public AdminUser getAdminUserById(Long adminUserId);

	/**
	 * �޸��û�����
	 * @param adminUser
	 */
	public void updatePassword(AdminUser adminUser);

	/**
	 * �õ����еĹ���Ա
	 * @return
	 */
	public List<AdminUser> findAll();
}
