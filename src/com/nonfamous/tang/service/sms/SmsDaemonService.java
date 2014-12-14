package com.nonfamous.tang.service.sms;

import com.nonfamous.tang.exception.ServiceException;

/**
 * @author: alan
 * 
 * <pre>
 * 短信定时发送程序
 * </pre>
 * 
 * @version $Id: SmsDaemonService.java,v 1.1 2008/07/11 00:47:14 fred Exp $
 */
public interface SmsDaemonService {

	public void daemonSendSms() throws ServiceException;
}
