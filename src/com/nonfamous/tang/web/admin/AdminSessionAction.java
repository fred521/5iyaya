package com.nonfamous.tang.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.domain.AdminUser;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.admin.AdminUserService;
import com.nonfamous.tang.web.common.Constants;

/**
 * @author eyeieye
 * @version $Id: AdminSessionAction.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class AdminSessionAction extends MultiActionController {
	private static final Log logger = LogFactory
			.getLog(AdminSessionAction.class);

	private boolean checkCodeEnable = true;

	private AdminUserService adminUserService;

	/**
	 * 显示登录页面
	 */
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "管理员登录");
		request.setAttribute("checkCodeEnable", checkCodeEnable);
		return new ModelAndView("admin/adminSession/loginPage");
	}

	/**
	 * 登录
	 */
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String name = rvp.getParameter("admin_name").getString();
		String pwd = rvp.getParameter("admin_pwd").getString();
		Cookyjar cookyjar = rvp.getCookyjar();
		String checkCode = cookyjar.get(Constants.CheckCode_Cookie);
		String commitCheckCode = rvp.getParameter("check_code").getString();
		boolean inputRight = true;
		if (name == null) {
			request.setAttribute("errorMessage", "用户名不能为空");
			inputRight = false;
		}
		if (pwd == null) {
			request.setAttribute("pwdMessage", "密码不能为空");
			inputRight = false;
		}
		if (checkCodeEnable) {
			if (commitCheckCode == null || checkCode == null
					|| !checkCode.equals(commitCheckCode)) {
				request.setAttribute("checkMessage", "校验码错误");
				inputRight = false;
			}
		}
		cookyjar.remove(Constants.CheckCode_Cookie);
		if (!inputRight) {
			ModelAndView mv = new ModelAndView("forward:init.htm");
			mv.addObject("inputName", name);
			return mv;
		}

		AdminUser user = null;
		try {
			user = adminUserService.login(name, pwd);
			if (logger.isDebugEnabled()) {
				logger.debug("user[" + user + "] login success");
			}
		} catch (ServiceException e) {
			logger.error("admin user login error", e);
		}

		if (user != null) {
			cookyjar.set(Constants.AdminUserId_Cookie, user.getUserId());
			return new ModelAndView("redirect:../admin_user/index.htm");
		} else {
			ModelAndView mv = new ModelAndView("forward:init.htm");
			mv.addObject("error", "输入的用户名或密码不对！");
			mv.addObject("inputName", name);
			return mv;
		}

	}

	/**
	 * 登出
	 */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		cookyjar.clean();
		return new ModelAndView("admin/adminSession/logout");
	}

	public boolean isCheckCodeEnable() {
		return checkCodeEnable;
	}

	public void setCheckCodeEnable(boolean checkCodeEnable) {
		this.checkCodeEnable = checkCodeEnable;
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
}
