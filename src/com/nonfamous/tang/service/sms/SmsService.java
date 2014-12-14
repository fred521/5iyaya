package com.nonfamous.tang.service.sms;

import com.nonfamous.tang.exception.ServiceException;

/**
 * @author: alan
 * 
 * <pre>
 *  ��վ�ڲ��ṩ���Ͷ��ŷ���ӿڣ�Ϊ�˷�ֹ���������ŷ��͵��쳣������վ����Ӧ�ٶ�
 *  ���Բ��ñ����ʽ��Ϊ���ŷ��͵��н�
 * </pre>
 * 
 * @version $Id: SmsService.java,v 1.1 2008/07/11 00:47:14 fred Exp $
 */
public interface SmsService {

	/**
	 * ���Ͷ���
	 * 
	 * @param phone
	 *            �����ֻ���
	 * @param smsContext
	 *            ��������
	 * @throws ServiceException
	 */
	public void sendSms(String phone, String smsContext)
			throws ServiceException;

}
