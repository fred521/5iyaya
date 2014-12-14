package com.nonfamous.tang.service.sms.pojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.sms.SmsDAO;
import com.nonfamous.tang.domain.Sms;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.sms.SmsService;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: POJOSmsService.java,v 1.1 2008/07/11 00:47:13 fred Exp $
 */
public class POJOSmsService extends POJOServiceBase implements SmsService {

	private SmsDAO smsDAO;

	private Pattern p = Pattern.compile("^[1][3|5][0-9]{9}");

	public void sendSms(String phone, String smsContext)
			throws ServiceException {
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(smsContext)
				|| !isValidePhone(phone)) {
			throw new ServiceException("短信内容或手机号不能为空");
		}
		Sms sms = new Sms();
		sms.setPhone(phone);
		sms.setContext(smsContext);
		sms.setStatus(Sms.SMS_WAIT_SEND);
		smsDAO.createSms(sms);
	}

	public void setSmsDAO(SmsDAO smsDAO) {
		this.smsDAO = smsDAO;
	}

	private boolean isValidePhone(String phone) {
		Matcher m = p.matcher(phone);
		return m.matches();
	}
}
