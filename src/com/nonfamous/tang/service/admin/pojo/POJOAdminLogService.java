package com.nonfamous.tang.service.admin.pojo;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.admin.AdminLogDAO;
import com.nonfamous.tang.domain.AdminLog;
import com.nonfamous.tang.service.admin.AdminLogService;

public class POJOAdminLogService extends POJOServiceBase implements
		AdminLogService {
	private AdminLogDAO adminLogDAO;

	public Long createLog(AdminLog log) {
		if (log == null) {
			throw new NullPointerException("admin log can't be null");
		}
		return this.adminLogDAO.createAdminLog(log);
	}

	public AdminLogDAO getAdminLogDAO() {
		return adminLogDAO;
	}

	public void setAdminLogDAO(AdminLogDAO adminLogDAO) {
		this.adminLogDAO = adminLogDAO;
	}

}
