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
	 * ���¶�ʱ��¼
	 * 
	 * @param log
	 * @return
	 * @throws DataAccessException
	 */
	public void updateQuartzLog(QuartzLog log);
	/**
	 * �õ���ʱ��¼
	 * 
	 * @param quartzType ��ʱ�����磺QUARTZ_INDEX_GOODS����Ʒ��������ʱ��
	 * @return
	 * @throws DataAccessException
	 */
	public QuartzLog getQuartzLogByType(String quartzType);
}
