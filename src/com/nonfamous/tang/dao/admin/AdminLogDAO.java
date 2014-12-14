package com.nonfamous.tang.dao.admin;

import com.nonfamous.tang.domain.AdminLog;

/**
 * @author: fish
 * @version $Id: AdminLogDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */
public interface AdminLogDAO {
	/**
	 * 创建一个操作日志记录
	 * 
	 * @param log
	 * @return
	 */
	public Long createAdminLog(AdminLog log);
}
