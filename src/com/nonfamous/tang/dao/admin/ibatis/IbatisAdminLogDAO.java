package com.nonfamous.tang.dao.admin.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.admin.AdminLogDAO;
import com.nonfamous.tang.domain.AdminLog;

public class IbatisAdminLogDAO extends SqlMapClientDaoSupport implements
		AdminLogDAO {

	public Long createAdminLog(AdminLog log) {
		if (log == null) {
			throw new NullPointerException("admin log can't be null");
		}
		return (Long) this.getSqlMapClientTemplate().insert("ADM_LOG_SPACE.admin_log_insert", log);
	}

}
