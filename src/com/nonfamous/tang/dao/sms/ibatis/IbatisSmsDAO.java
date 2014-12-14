package com.nonfamous.tang.dao.sms.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.sms.SmsDAO;
import com.nonfamous.tang.domain.Sms;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisSmsDAO.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */
public class IbatisSmsDAO extends SqlMapClientDaoSupport implements SmsDAO {

	public static final String SPACE = "SMS_SPACE.";

	public void createSms(Sms sms) throws DataAccessException {
		if (sms == null) {
			throw new NullPointerException("sms can't be null");
		}
		getSqlMapClientTemplate().insert(SPACE + "sms_insert", sms);
	}

	public void updateSms(Sms sms) throws DataAccessException {
		if (sms == null || sms.getSmsId() == null) {
			throw new NullPointerException("id or object can't be null");
		}
		getSqlMapClientTemplate().update(SPACE + "sms_update", sms);

	}

	@SuppressWarnings("unchecked")
	public void updateSmsSendStatus(List<Sms> smsList,boolean isSuccess) {
		if (smsList != null && smsList.size() > 0) {
			Map map = new HashMap();
			if ( isSuccess ){
				map.put("success" , "success");
			}
			map.put("list",smsList);
			getSqlMapClientTemplate().update(SPACE + "sms_send_status_update",
					map);
		}
	}

	public Sms getSmsById(Long id) throws DataAccessException {
		if (id == null) {
			throw new NullPointerException("id can't be null");
		}
		return (Sms) getSqlMapClientTemplate().queryForObject(
				SPACE + "get_sms", id);
	}

	public void deleteSmsById(Long id) throws DataAccessException {
		if (id == null) {
			throw new NullPointerException("id can't be null");
		}
		getSqlMapClientTemplate().delete(SPACE + "delete_sms", id);

	}

	@SuppressWarnings("unchecked")
	public List<Sms> getWaitSendSms(Date searchDate) throws DataAccessException {
		if (searchDate == null) {
			throw new NullPointerException("searchDate can't be null");
		}
		return getSqlMapClientTemplate().queryForList(
				SPACE + "get_wait_sms_list", searchDate);
	}

}
