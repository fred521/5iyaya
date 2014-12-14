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

	// 短信服务器
	private String smsServer;

	// 短信服务端口
	private int smsServerPort;

	// 短信发送验证用户
	private String smsAccessUser;

	// 短信发送验证用户密码
	private String smsAccessPwd;

	private int diffDate = -1 ;

	/**
	 * <p>
	 * 发送处理流程： 1、防止daemon调用时间比较短，重复处理为发送的数据，采用锁的方式控制调用函数是否可以进入
	 * 2、获取根据配置的日期差来获取待发送的记录，防止由于服务器问题造成daemon触发前的短信未被发送，这样作有点土，但是为了避免全表扫描
	 * 3、根据发送失败与否，更新短信记录，如果发送错误需要根新发送次数
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public void daemonSendSms() throws ServiceException {
		//logger.debug("开始发送短信啦");
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
			// 一次daemon发送只需要创建一次sms发送对象即可
			EmeSms emeSms = new EmeSms(smsServer, smsServerPort, smsAccessUser,
					smsAccessPwd);
			
			// 从前一天的这个时刻获取待发送数据

			List<Sms> successList = new ArrayList<Sms>();
			List<Sms> errorList = new ArrayList<Sms>();
			for (Sms sms : list) {
				if (isSend(sms, emeSms)) {
					// 可以进行批量更新
					sms.setStatus(Sms.SMS_SEND_SUCCESS);
					successList.add(sms);
				} else {
					// 发送失败需要更新发送次数和最后修改时间
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
	 * 具体执行发送操作
	 * 
	 * @param sms
	 * @param emeSms
	 * @return
	 */
	private boolean isSend(Sms sms, EmeSms emeSms) {
		String return_serial = "000";
		try {
			// 接口中如果发送成功返回0
			/*
			 * return emeSms.sendMsg(0, sms.getPhone(), sms.getContext(),
			 * return_serial) == 0 ? true : false;
			 */
			int i = emeSms.sendMsg(0, sms.getPhone(), sms.getContext(),
					return_serial);
			if (logger.isDebugEnabled()) {
				logger.debug("发送是否成功:" + sms.getPhone() + "-发送状态=" + i);
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
