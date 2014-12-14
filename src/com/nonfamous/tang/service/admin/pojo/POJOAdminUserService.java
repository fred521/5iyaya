package com.nonfamous.tang.service.admin.pojo;

import java.util.List;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.admin.AdminUserDAO;
import com.nonfamous.tang.domain.AdminUser;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.admin.AdminUserService;

/**
 * @author eyeieye
 * @version $Id: POJOAdminUserService.java,v 1.1 2008/07/11 00:47:16 fred Exp $
 */
public class POJOAdminUserService extends POJOServiceBase implements
		AdminUserService {
	private AdminUserDAO adminUserDAO;

	public AdminUserDAO getAdminUserDAO() {
		return adminUserDAO;
	}

	public void setAdminUserDAO(AdminUserDAO adminUserDAO) {
		this.adminUserDAO = adminUserDAO;
	}

	public AdminUser login(String name, String password) {
		if (name == null) {
			throw new NullPointerException("admin user name can't be null");
		}
		if (password == null) {
			throw new NullPointerException("admin user password can't be null");
		}
		AdminUser user = adminUserDAO.getUserByName(name);
		if(user == null){
			throw new ServiceException("用户名不存在");
		}
		if (!user.isUserNormalStatus()) {
			throw new ServiceException("用户已被禁用");
		}
		user.setUnencryptPassword(password);
		if (!user.isPasswordCorrect()) {
			throw new ServiceException("密码不正确");
		}
		return user;
	}

	public AdminUser getAdminUserById(Long adminUserId) {
		if (adminUserId == null) {
			throw new NullPointerException("adminUserId can't be null");
		}
		return this.adminUserDAO.getUserById(adminUserId);
	}

	public void updatePassword(AdminUser adminUser) {
		if (adminUser == null) {
			throw new NullPointerException("adminUser can't be null");
		}
		this.adminUserDAO.updateAdminUserPassword(adminUser);
	}

	public List<AdminUser> findAll() {
		return this.adminUserDAO.findAll();
	}

}
