package com.nonfamous.tang.dao.sms;

import java.util.Date;
import java.util.List;

import com.nonfamous.tang.domain.Sms;

/**
 * @author: alan
 * 
 * <pre>
 * 短信发送表处理接口
 * </pre>
 * 
 * @version $Id: SmsDAO.java,v 1.1 2008/07/11 00:47:15 fred Exp $
 */
public interface SmsDAO {

	/**
	 * 创建一条短信
	 * 
	 * @param sms
	 */
	public void createSms(Sms sms);

	/**
	 * 根据短信id设置短信状态
	 * 
	 * @param sms
	 */
	public void updateSms(Sms sms);

	/**
	 * 批量更新发送状态
	 * 
	 * @param isSuccess
	 * @param smsList
	 */
	public void updateSmsSendStatus(List<Sms> smsList,boolean isSuccess);

	/**
	 * 
	 * 根据断行id获取短信
	 * 
	 * @param id
	 * @return
	 */
	public Sms getSmsById(Long id);

	/**
	 * 删除短信
	 * 
	 * @param id
	 */
	public void deleteSmsById(Long id);

	/**
	 * 获取待发送短信
	 * 
	 * @param searchDate
	 * @return
	 */
	public List<Sms> getWaitSendSms(Date searchDate);
}
