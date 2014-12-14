package com.nonfamous.tang.web.admin;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


import com.nonfamous.tang.service.admin.HomePageGenerateService;
import com.nonfamous.tang.service.admin.pojo.POJOHomePageGenerateService;



public class AdminTaskAction extends MultiActionController {
	//private static final Log logger = LogFactory.getLog(AdminTaskAction.class);
	private HomePageGenerateService homePageGenerateService;

	public ModelAndView generateHomePageInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("admin/adminTasks/generateHomePage");
		setHomePageProperties(view);
		return view;
	}
	
	private void setHomePageProperties(ModelAndView view){
		view.addObject("charSet",homePageGenerateService.getAttribute("fileCharSet"));
		view.addObject("homePageUrl",homePageGenerateService.getAttribute("homePageUrl"));
		view.addObject("filePath",homePageGenerateService.getAttribute("pageFilePath"));
		File file = new File(homePageGenerateService.getAttribute("pageFilePath"));
		if(file.exists()){
			Date date = new Date(file.lastModified());
			view.addObject("lastModified",date);
		}
	}
	
	public ModelAndView generateHomePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		homePageGenerateService.generateHomePage();
		ModelAndView view = new ModelAndView("admin/adminTasks/generateHomePage");
		setHomePageProperties(view);
		return view;
	}

	public HomePageGenerateService getHomePageGenerateService() {
		return homePageGenerateService;
	}

	public void setHomePageGenerateService(
			HomePageGenerateService homePageGenerateService) {
		this.homePageGenerateService = homePageGenerateService;
	}
}
