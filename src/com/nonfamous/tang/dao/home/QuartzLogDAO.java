package com.nonfamous.tang.dao.home;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.QuartzLog;
/**
 * 
 * @author victor
 * 
 */
public interface QuartzLogDAO {
	/**
	 * 更新定时记录
	 * 
	 * @param log
	 * @return
	 * @throws DataAccessException
	 */
	public void updateQuartzLog(QuartzLog log);
	/**
	 * 得到定时记录
	 * 
	 * @param quartzType 定时类型如：QUARTZ_INDEX_GOODS（商品的索引定时）
	 * @return
	 * @throws DataAccessException
	 */
	public QuartzLog getQuartzLogByType(String quartzType);
}
