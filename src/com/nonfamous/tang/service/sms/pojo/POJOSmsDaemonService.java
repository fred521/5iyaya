package com.nonfamous.tang.service.sms.pojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.eastelsoft.emesms.EmeSms;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.sms.SmsDAO;
import com.nonfamous.tang.domain.Sms;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.sms.SmsDaemonService;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: POJOSmsDaemonService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public class POJOSmsDaemonService extends POJOServiceBase implements
		SmsDaemonService {
	private SmsDAO smsDAO;

	private boolean is_run = false;

	// ���ŷ�����
	private String smsServer;

	// ���ŷ���˿�
	private int smsServerPort;

	// ���ŷ�����֤�û�
	private String smsAccessUser;

	// ���ŷ�����֤�û�����
	private String smsAccessPwd;

	private int diffDate = -1 ;

	/**
	 * <p>
	 * ���ʹ������̣� 1����ֹdaemon����ʱ��Ƚ϶̣��ظ�����Ϊ���͵����ݣ��������ķ�ʽ���Ƶ��ú����Ƿ���Խ���
	 * 2����ȡ�������õ����ڲ�����ȡ�����͵ļ�¼����ֹ���ڷ������������daemon����ǰ�Ķ���δ�����ͣ��������е���������Ϊ�˱���ȫ��ɨ��
	 * 3�����ݷ���ʧ����񣬸��¶��ż�¼��������ʹ�����Ҫ���·��ʹ���
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void daemonSendSms() throws ServiceException {
		//logger.debug("��ʼ���Ͷ�����");
		if (is_run) {
			if (logger.isDebugEnabled()) {
				logger.debug("sms is sending now.......");
			}
			return;
		}
		try {
			is_run = true;
			
			List<Sms> list = (List) smsDAO.getWaitSendSms(DateUtils
					.getDiffCurrentDate(diffDate));
			if (list.size() == 0) {
				return;
			}
			// һ��daemon����ֻ��Ҫ����һ��sms���Ͷ��󼴿�
			EmeSms emeSms = new EmeSms(smsServer, smsServerPort, smsAccessUser,
					smsAccessPwd);
			
			// ��ǰһ������ʱ�̻�ȡ����������

			List<Sms> successList = new ArrayList<Sms>();
			List<Sms> errorList = new ArrayList<Sms>();
			for (Sms sms : list) {
				if (isSend(sms, emeSms)) {
					// ���Խ�����������
					sms.setStatus(Sms.SMS_SEND_SUCCESS);
					successList.add(sms);
				} else {
					// ����ʧ����Ҫ���·��ʹ���������޸�ʱ��
					sms.setStatus(Sms.SMS_WAIT_SEND);
					errorList.add(sms);
				}
			}
			smsDAO.updateSmsSendStatus(successList, true);
			smsDAO.updateSmsSendStatus(errorList, false);

		} catch (DataAccessException e) {
			logger.error("daemon send sms error ", e);
		} finally {
			is_run = false;
		}
	}

	/**
	 * ����ִ�з��Ͳ���
	 * 
	 * @param sms
	 * @param emeSms
	 * @return
	 */
	private boolean isSend(Sms sms, EmeSms emeSms) {
		String return_serial = "000";
		try {
			// �ӿ���������ͳɹ�����0
			/*
			 * return emeSms.sendMsg(0, sms.getPhone(), sms.getContext(),
			 * return_serial) == 0 ? true : false;
			 */
			int i = emeSms.sendMsg(0, sms.getPhone(), sms.getContext(),
					return_serial);
			if (logger.isDebugEnabled()) {
				logger.debug("�����Ƿ�ɹ�:" + sms.getPhone() + "-����״̬=" + i);
			}
			return i == 0 ? true : false;
		} catch (IOException e) {
			logger.error("sms send error ", e);
			return false;
		}
	}

	public void setSmsDAO(SmsDAO smsDAO) {
		this.smsDAO = smsDAO;
	}

	public void setSmsAccessPwd(String smsAccessPwd) {
		this.smsAccessPwd = smsAccessPwd;
	}

	public void setSmsAccessUser(String smsAccessUser) {
		this.smsAccessUser = smsAccessUser;
	}

	public void setSmsServer(String smsServer) {
		this.smsServer = smsServer;
	}

	public void setSmsServerPort(int smsServerPort) {
		this.smsServerPort = smsServerPort;
	}

	public void setDiffDate(int diffDate) {
		this.diffDate = diffDate;
	}

}
