package com.nonfamous.tang.web.home;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.nonfamous.tang.service.ucenter.UCenterException;
import com.nonfamous.tang.service.ucenter.UCenterService;

public class UCenterWebUtil {
	
	private UCenterWebUtil(UCenterService ucenterService){
		this.ucenterService = ucenterService;
	}
	
	private UCenterService ucenterService;
	
	private static UCenterWebUtil instance;
	
	public synchronized static UCenterWebUtil getInstance(UCenterService ucenterService){
		if(instance == null)
			instance = new UCenterWebUtil(ucenterService);
		return instance;
	}
	
	protected final Log logger = LogFactory.getLog(UCenterWebUtil.class);
	
	/**
	 * �����¼
	 * 
	 * �ж��û��Ƿ��Ѿ���ucenter��ע���, ���û�У�����ucenterע����ʺ�
	 * 
	 * @param mv
	 * @param loginId
	 * @param password
	 * @param email
	 * @throws UCenterException
	 */
	protected void registerOrLoginUCenter(ModelAndView mv, String loginId,
			String password, String email) {
		try {
			String uid = "";
			// �ж��û��Ƿ��Ѿ���ucenter��ע���
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() <= 0) {// �û�û����ucenter��ע�����
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] is not register in ucenter.");
				}
				int res = ucenterService.userRegister(loginId, password, email);
				if (res <= 0) {
					logger.error("User [" + user.get("username")
							+ "] register in UCenter failed.");
					return;
				}
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] register successfully in ucenter.");
				}
				uid = String.valueOf(res);
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] exists in ucenter.");
				}
				uid = user.get("uid");
			}

			// get syn login code from ucenter and print in page(it is a
			// fragment of Javascript)
			loginUCenter(mv, loginId);
		} catch (UCenterException e) {
			logger.error("Cannot register or login in ucenter due to: \n"
					+ "error code: " + e.getCode() + "\n" + "error message: "
					+ e.getMessage());
		}
	}

	/**
	 * �����¼
	 * 
	 * @param mv
	 * @param loginId
	 */
	protected void loginUCenter(ModelAndView mv, String loginId) {
		try {
			// �ж��û��Ƿ��Ѿ���ucenter��ע���
			String uid = "";
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() <= 0) {// �û�û����ucenter��ע�����
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] not exists in ucenter.");
				}
			} else {
				uid = user.get("uid");
				// get syn login code from ucenter and print in page(it is a
				// fragment of Javascript)
				String synLoginCode = ucenterService.userSynlogin(uid);
				if (logger.isInfoEnabled()) {
					logger.info("sync login code: " + synLoginCode);
				}
				mv.addObject("synLoginCode", synLoginCode);
			}
		} catch (UCenterException e) {
			logger.error("Cannot login in ucenter due to: \n" + "error code: "
					+ e.getCode() + "\n" + "error message: " + e.getMessage());
		}
	}

	/**
	 * ����ǳ�
	 * 
	 * @param mv
	 * @param loginId
	 */
	protected void logoutUCenter(ModelAndView mv, String loginId) {
		try {
			// �ж��û��Ƿ��Ѿ���ucenter��ע���
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() > 0) { // �û��Ѿ���ucenter��ע�����
				String synLogoutCode = ucenterService.userSynlogout(user
						.get("uid"));
				mv.addObject("synLogoutCode", synLogoutCode);
			}
		} catch (UCenterException e) {
			logger.error("Cannot logout in ucenter due to: \n" + "error code: "
					+ e.getCode() + "\n" + "error message: " + e.getMessage());
		}
	}
}
