package com.nonfamous.tang.service.sms;

import com.nonfamous.tang.exception.ServiceException;

/**
 * @author: alan
 * 
 * <pre>
 *  网站内部提供发送短信服务接口，为了防止第三方短信发送的异常导致网站的响应速度
 *  所以采用表的形式作为短信发送的中介
 * </pre>
 * 
 * @version $Id: SmsService.java,v 1.1 2008/07/11 00:47:14 fred Exp $
 */
public interface SmsService {

	/**
	 * 发送短信
	 * 
	 * @param phone
	 *            发送手机号
	 * @param smsContext
	 *            发送内容
	 * @throws ServiceException
	 */
	public void sendSms(String phone, String smsContext)
			throws ServiceException;

}
