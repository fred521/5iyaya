package com.nonfamous.tang.dao.home.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.domain.QuartzLog;
/**
 * 
 * @author victor
 * 
 */
public class IbatisQuartzLogDAO extends SqlMapClientDaoSupport implements QuartzLogDAO{
	public static final String SPACE = "QUARTZLOG_SPACE.";
	public QuartzLog getQuartzLogByType(String quartzType) {
		if (quartzType == null) {
			throw new NullPointerException("quartzType can't be null");
		}
		return (QuartzLog) getSqlMapClientTemplate().queryForObject(
				SPACE + "get_QuartzLogByType", quartzType);
	}

	public void updateQuartzLog(QuartzLog log) {
		this.getSqlMapClientTemplate().update(SPACE+"update_QuartzLog", log);
	}
}
