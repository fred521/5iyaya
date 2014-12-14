package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.home.HelperTypeDAO;
import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.domain.HelperType;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.HelperService;
import com.nonfamous.tang.web.common.Constants;
public class HelperAction extends MultiActionController{
	private static final Log logger = LogFactory.getLog(HelperAction.class);

	private FormFactory formFactory;
	private HelperService helperService;
	private HelperTypeDAO helperTypeDAO;

	public HelperService getHelperService() {
		return this.helperService;
	}

	public void setHelperService(HelperService helperService) {
		this.helperService = helperService;
	}

	public FormFactory getFormFactory() {
		return this.formFactory;
	}

	public HelperTypeDAO getHelperTypeDAO() {
		return this.helperTypeDAO;
	}

	public void setHelperTypeDAO(HelperTypeDAO helperTypeDAO) {
		this.helperTypeDAO = helperTypeDAO;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mav = new ModelAndView("home/helper/index");
		List<HelperType> helperTypeList=helperTypeDAO.getAllHelperType();
		mav.addObject("helperTypeList", helperTypeList);
		
		Map<Integer,List<Helper>> helperMap = new HashMap<Integer,List<Helper>>();
		List helperList = helperService.getAllHelperList();
		if( helperList != null ) {
			for( int i=0 ; i<helperList.size() ; i ++ ) {
				Helper helper = (Helper)helperList.get(i) ;
				if( !helperMap.containsKey( helper.getHelperType() ) ) {
					helperMap.put( helper.getHelperType() , new ArrayList<Helper>() ) ;
				}
				helperMap.get( helper.getHelperType() ).add( helper ) ;
			}
		}
		mav.addObject( "helperMap" , helperMap );
		
		return mav;
		
		
	}
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String helperId = rvp.getParameterOrAttribute("id").getString();
		if (StringUtils.isBlank(helperId)) {
			throw new Exception("helperId is null");
		}
		Helper helper = helperService.getHelperById(helperId);
		if (helper == null
				|| !helper.getHelperStatus().equalsIgnoreCase(
						Helper.NORMAL_STATUS)) {
			throw new ServiceException("您要找的分类信息不存在，或已经删除");
		}
		ModelAndView mv = new ModelAndView("home/helper/detail");
		mv.addObject("helper", helper);
		return mv;
	}
	/**
	 * menu.vm->link->screen
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView helperList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HelperQuery query = new HelperQuery();
		query.setPageSize(20);
		String keyType = request.getParameter("keyType");
		// 关键字
		String keyWord = request.getParameter("keyWord");

		String helperTypeId = request.getParameter("helperType");
		query.setHelperType(helperTypeId);
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("helperType", helperTypeId);
		 if (StringUtils.equals(keyType, "helperTitle")) {
			query.setHelperTitle(keyWord);
		 }
		RequestValueParse rvp = new RequestValueParse(request);
		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));
		}
		List helperList = helperService.getQueryHelperList(query);
		if (helperList != null) {
			for (int i = 0; i < helperList.size(); i++) {
				Helper helper = (Helper) helperList.get(i);
				Integer helperType = helper.getHelperType();
				HelperType object = helperTypeDAO.getHelperTypeById(String.valueOf(helperType));
				String typeName = object.getTypeName();
				helper.getHelperTypeDO().setTypeName(typeName);
			}
		}
		ModelAndView mav = new ModelAndView("admin/helper/helperList");
		List helperTypeList = helperTypeDAO.getAllHelperType();
		mav.addObject("helperTypeList", helperTypeList);
		mav.addObject("helperList", helperList);
		mav.addObject("search", query);
		return mav;
	}
	/**
	 * menu.vm->link->screen
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addHelperTypeInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/helper/helperTypeAdd");
		return mav;
	}
	/**
	 * page->submit->action
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addHelperType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Form form = formFactory.getForm("adminAddHelperType", request);
		ModelAndView mav = new ModelAndView("admin/helper/helperTypeAdd");
		if (!form.isValide()) {
			request.setAttribute("form", form);
			return mav;
		}
		HelperType helperType= new HelperType();
		String typeName= request.getParameter("helperTitle").toString();
		if(StringUtils.isNotEmpty(typeName)){
			if(StringUtils.isNotEmpty(helperTypeDAO.getHelperTypeByTypeName(typeName))){
				mav.addObject("errorMessage", "存在重复的标题名字");
				mav.addObject("form", form);
				return mav;
			}
			helperType.setTypeName(typeName);
		}
		Integer showOrder= new Integer(1);
		if(StringUtils.isNotEmpty(request.getParameter("showOrder").toString()))
		{
			 showOrder=Integer.valueOf(request.getParameter("showOrder").toString());
		}
		helperType.setShowOrder(showOrder);
		helperType = helperTypeDAO.addHelperType(helperType);
		mav.addObject("form", form);
		mav.addObject("succMessage", "新增成功,您可以继续新增");
		return mav;
	}
	/**
	 * menu.vm->link->screen
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView helperAddInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/helper/helperAdd");
		List helperType = helperTypeDAO.getAllHelperType();
		mav.addObject("helperType", helperType);
		return mav;
	}
	public ModelAndView helperUpdateInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/helper/helperAdd");
		String helperId = request.getParameter("id");
		Helper helper = (Helper) helperService.getHelperById(helperId);
		Form form = formFactory.getForm("adminAddHelper", request);
		form.getField("helperTitle").setValue(helper.getHelperTitle());
		form.getField("helperType").setValue(String.valueOf(helper.getHelperType()));
		form.getField("content").setValue(helper.getContent());
		List helperType = helperTypeDAO.getAllHelperType();
		mv.addObject("helperType", helperType);
		mv.addObject("id", helperId);
		mv.addObject("form", form);
		return mv;
	}
	/**
	 * page->submit->action
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView helperAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Form form = formFactory.getForm("adminAddHelper", request);

		String helperId = request.getParameter("id");
		if (!form.isValide()) {
			if (StringUtils.isBlank(helperId)) {
				request.setAttribute("form", form);
				return helperAddInit(request, response);
			} else {
				request.setAttribute("form", form);
				return helperUpdateInit(request, response);
			}
		}
		ModelAndView mv = new ModelAndView("admin/helper/helperAdd");
		RequestValueParse parse = new RequestValueParse(request);
		Helper helper = new Helper();
		helper.setHelperTitle(request.getParameter("helperTitle"));
		helper.setHelperType(Integer.valueOf(request.getParameter("helperType")));
		helper.setContent(request.getParameter("content"));
		String adminUserId = parse.getCookyjar().get(
				Constants.AdminUserId_Cookie);

		helper.setModifier(adminUserId);

		if (StringUtils.isBlank(helperId)) {
			helper.setHelperStatus(Helper.NORMAL_STATUS);
			helper.setCreator(adminUserId);
			helperService.addHelper(helper);
			mv.addObject("succMessage", "帮助信息新增成功,您可以继续新增");
		} else {
			helper.setHelperId(helperId);
			helperService.updateHelper(helper);
			mv.addObject("succMessage", "帮助信息修改成功");
			mv.addObject("id", helperId);
			request.setAttribute("form", form);
		}
		List helperType = helperTypeDAO.getAllHelperType();
		mv.addObject("helperType", helperType);
		return mv;
	}
	public ModelAndView helperDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String adminUserId = parse.getCookyjar().get(
				Constants.AdminUserId_Cookie);
		String helperId = request.getParameter("id");
		Helper helper = (Helper) helperService.getHelperById(helperId);
		helper.setHelperStatus(Helper.DELETE_STATUS);
		// news.setNick(loginId);
		// news.setMemberId("helelelel");
		// news.setCreator(adminUserId);
		helper.setModifier(adminUserId);
		try {
			helperService.updateHelper(helper);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return helperList(request, response);
	}




}
