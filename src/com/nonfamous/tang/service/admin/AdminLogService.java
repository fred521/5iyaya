package com.nonfamous.tang.service.admin;

import com.nonfamous.tang.domain.AdminLog;

/**
 * @author: fish
 * @version $Id: AdminLogService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public interface AdminLogService {
	public Long createLog(AdminLog log);
}
