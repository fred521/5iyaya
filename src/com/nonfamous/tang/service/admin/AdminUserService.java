package com.nonfamous.tang.service.admin;

import java.util.List;

import com.nonfamous.tang.domain.AdminUser;

/**
 * @author eyeieye
 * @version $Id: AdminUserService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public interface AdminUserService {
	/**
	 * 管理员登录
	 * 
	 * @param name
	 *            不能为空
	 * @param password
	 *            不能为空
	 * @return 登录成功的管理员
	 */
	public AdminUser login(String name, String password);

	/**
	 * 根据id得到管理员
	 * 
	 * @param adminUserId
	 *            不能为空
	 * @return 管理员
	 */
	public AdminUser getAdminUserById(Long adminUserId);

	/**
	 * 修改用户口令
	 * @param adminUser
	 */
	public void updatePassword(AdminUser adminUser);

	/**
	 * 得到所有的管理员
	 * @return
	 */
	public List<AdminUser> findAll();
}
