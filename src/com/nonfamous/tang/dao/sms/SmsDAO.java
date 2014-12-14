package com.nonfamous.tang.dao.sms;

import java.util.Date;
import java.util.List;

import com.nonfamous.tang.domain.Sms;

/**
 * @author: alan
 * 
 * <pre>
 * ���ŷ��ͱ���ӿ�
 * </pre>
 * 
 * @version $Id: SmsDAO.java,v 1.1 2008/07/11 00:47:15 fred Exp $
 */
public interface SmsDAO {

	/**
	 * ����һ������
	 * 
	 * @param sms
	 */
	public void createSms(Sms sms);

	/**
	 * ���ݶ���id���ö���״̬
	 * 
	 * @param sms
	 */
	public void updateSms(Sms sms);

	/**
	 * �������·���״̬
	 * 
	 * @param isSuccess
	 * @param smsList
	 */
	public void updateSmsSendStatus(List<Sms> smsList,boolean isSuccess);

	/**
	 * 
	 * ���ݶ���id��ȡ����
	 * 
	 * @param id
	 * @return
	 */
	public Sms getSmsById(Long id);

	/**
	 * ɾ������
	 * 
	 * @param id
	 */
	public void deleteSmsById(Long id);

	/**
	 * ��ȡ�����Ͷ���
	 * 
	 * @param searchDate
	 * @return
	 */
	public List<Sms> getWaitSendSms(Date searchDate);
}
