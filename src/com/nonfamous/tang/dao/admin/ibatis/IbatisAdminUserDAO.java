package com.nonfamous.tang.dao.admin.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.admin.AdminUserDAO;
import com.nonfamous.tang.domain.AdminUser;

/**
 * @author eyeieye
 * @version $Id: IbatisAdminUserDAO.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */
public class IbatisAdminUserDAO extends SqlMapClientDaoSupport implements
		AdminUserDAO {
	public AdminUser getUserByName(String name) throws DataAccessException {
		if (name == null) {
			throw new NullPointerException("name can't be null");
		}
		return (AdminUser) getSqlMapClientTemplate().queryForObject(
				"ADMUSER_SPACE.get_admin_user_by_name", name);
	}

	public Long createAdminUser(AdminUser u) throws DataAccessException {
		if (u == null) {
			throw new NullPointerException("AdminUser can't be null");
		}
		return (Long) getSqlMapClientTemplate().insert(
				"ADMUSER_SPACE.admin_user_insert", u);
	}

	public boolean updateAdminUserPassword(AdminUser au)
			throws DataAccessException {
		if (au == null) {
			throw new NullPointerException("AdminUser can't be null");
		}
		int rows = this.getSqlMapClientTemplate().update(
				"ADMUSER_SPACE.update_admin_user_password", au);
		return rows == 1;
	}

	public boolean setAdminUserStatus(AdminUser au) throws DataAccessException {
		if (au == null) {
			throw new NullPointerException("AdminUser can't be null");
		}
		int rows = this.getSqlMapClientTemplate().update(
				"ADMUSER_SPACE.update_admin_user_status", au);
		return rows == 1;
	}

	public AdminUser getUserById(Long adminUserId) {
		if (adminUserId == null) {
			throw new NullPointerException("adminUserId can't be null");
		}
		return (AdminUser) getSqlMapClientTemplate().queryForObject(
				"ADMUSER_SPACE.get_admin_user_by_id", adminUserId);
	}

	@SuppressWarnings("unchecked")
	public List<AdminUser> findAll() {
		return getSqlMapClientTemplate().queryForList(
				"ADMUSER_SPACE.get_all_admin_users");
	}

}
