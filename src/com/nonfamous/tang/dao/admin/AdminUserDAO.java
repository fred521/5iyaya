package com.nonfamous.tang.dao.admin;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.AdminUser;

/**
 * @author eyeieye
 * @version $Id: AdminUserDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */
public interface AdminUserDAO {
	/**
	 * 根据用户的登录名得到用户
	 * 
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public AdminUser getUserByName(String name) throws DataAccessException;

	/**
	 * 创建一个用户
	 * 
	 * @param u
	 * @return 新创建的用户id
	 */
	public Long createAdminUser(AdminUser u) throws DataAccessException;

	/**
	 * 更新用户口令,更新口令，修改人
	 * 
	 * @param au
	 * @param operatorId
	 * @return 是否成功
	 * @throws DataAccessException
	 */
	public boolean updateAdminUserPassword(AdminUser au)
			throws DataAccessException;

	/**
	 * 更新用户的状态,包括状态，修改人
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws DataAccessException
	 */
	public boolean setAdminUserStatus(AdminUser au) throws DataAccessException;

	/**
	 * 根据id得到用户
	 * 
	 * @param adminUserId
	 * @return
	 */
	public AdminUser getUserById(Long adminUserId);

	/**
	 * 得到所有的管理员
	 * 
	 * @return
	 */
	public List<AdminUser> findAll();
}
