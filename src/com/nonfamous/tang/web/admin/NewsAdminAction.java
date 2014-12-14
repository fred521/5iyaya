package com.nonfamous.tang.web.admin;

import java.util.List;

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
import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.domain.NewsType;
import com.nonfamous.tang.service.home.NewsService;
import com.nonfamous.tang.web.common.Constants;

/**
 * 
 * @author fred
 * 
 */
public class NewsAdminAction extends MultiActionController {

	private static final Log logger = LogFactory.getLog(NewsAdminAction.class);

	private FormFactory formFactory;

	private NewsService newsService;

	private NewsTypeDAO newsTypeDAO;

	private NewsContentDAO newsContentDAO;

	public ModelAndView newsList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewsQuery query = new NewsQuery();
		query.setPageSize(20);
		String keyType = request.getParameter("keyType");
		// 关键字
		String keyWord = request.getParameter("keyWord");

		String newsTypeId = request.getParameter("newsType");
		query.setNewsType(newsTypeId);
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("newsType", newsTypeId);
		if (StringUtils.equals(keyType, "nick")) {
			query.setNick(keyWord);
		} else if (StringUtils.equals(keyType, "newsTitle")) {
			query.setNewsTitle(keyWord);
		} else if (StringUtils.equals(keyType, "phone")) {
			query.setPhone(keyWord);
		}
		RequestValueParse rvp = new RequestValueParse(request);
		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));
		}
		List newsList = newsService.getQueryNewsList(query);
		if (newsList != null) {
			for (int i = 0; i < newsList.size(); i++) {
				NewsBaseInfo news = (NewsBaseInfo) newsList.get(i);
				String newsType = news.getNewsType();
				NewsType object = newsTypeDAO.getNewsTypeById(newsType);
				String typeName = object.getTypeName();
				news.getNewsTypeDO().setTypeName(typeName);
			}
		}
		ModelAndView mav = new ModelAndView("admin/news/newsList");
		List newsTypeList = newsTypeDAO.getAllNewsType();
		mav.addObject("newsTypeList", newsTypeList);
		mav.addObject("newsList", newsList);
		mav.addObject("search", query);
		return mav;
	}

	public ModelAndView newsAddInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/news/newsAdd");
		List newsType = newsTypeDAO.getAllNewsType();
		mav.addObject("newsType", newsType);
		return mav;
	}

	public ModelAndView newsUpdateInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/news/newsAdd");
		String newsId = request.getParameter("id");
		NewsBaseInfo news = (NewsBaseInfo) newsService.getNewsById(newsId);
		NewsContent content = newsContentDAO.getNewsContentById(newsId);
		Form form = formFactory.getForm("addNews", request);
		form.getField("newsTitle").setValue(news.getNewsTitle());
		form.getField("phone").setValue(news.getPhone());
		form.getField("newsType").setValue(news.getNewsType());
		form.getField("abandonDays").setValue(
				Long.toString(news.getAbandonDays()));
		form.getField("content").setValue(content.getContent());
		List newsType = newsTypeDAO.getAllNewsType();
		mv.addObject("newsType", newsType);
		mv.addObject("id", newsId);
		mv.addObject("form", form);
		return mv;
	}

	public ModelAndView newsAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Form form = formFactory.getForm("adminAddNews", request);

		String newsId = request.getParameter("id");
		if (!form.isValide()) {
			if (StringUtils.isBlank(newsId)) {
				request.setAttribute("form", form);
				request.setAttribute("memberId", request.getParameter("memberId"));
				request.setAttribute("nick", request.getParameter("nick"));
				return newsAddInit(request, response);
			} else {
				request.setAttribute("form", form);
				return newsUpdateInit(request, response);
			}
		}
		ModelAndView mv = new ModelAndView("admin/news/newsAdd");
		RequestValueParse parse = new RequestValueParse(request);
		NewsBaseInfo news = new NewsBaseInfo();
		news.setNewsTitle(request.getParameter("newsTitle"));
		if ( StringUtils.isBlank(request.getParameter("abandonDays"))){
			news.setAbandonDays(new Long(10));
		}else{
			news.setAbandonDays(new Long(request.getParameter("abandonDays")));
		}
		news.setNewsType(request.getParameter("newsType"));
		news.setPhone(request.getParameter("phone"));
		news.getContent().setContent(request.getParameter("content"));
		String adminUserId = parse.getCookyjar().get(
				Constants.AdminUserId_Cookie);

		news.setModifier(adminUserId);

		if (StringUtils.isBlank(newsId)) {
			news.setNewsStatus(NewsBaseInfo.NORMAL_STATUS);
			news.setNick(request.getParameter("nick"));
			news.setMemberId(request.getParameter("memberId"));
			news.setCreator(adminUserId);
			news.setViewCount(Long.valueOf(0));
			newsService.addNews(news);
			mv.addObject("succMessage", "信息新增成功,您可以继续新增");
			request.setAttribute("memberId", request.getParameter("memberId"));
			request.setAttribute("nick", request.getParameter("nick"));
		} else {
			news.setNewsId(newsId);
			newsService.updateNews(news);
			mv.addObject("succMessage", "信息修改成功");
			mv.addObject("id", newsId);
			request.setAttribute("form", form);
		}

		List newsType = newsTypeDAO.getAllNewsType();
		mv.addObject("newsType", newsType);
		
		return mv;

	}

	public ModelAndView newsDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String adminUserId = parse.getCookyjar().get(
				Constants.AdminUserId_Cookie);
		String newsId = request.getParameter("id");
		NewsBaseInfo news = (NewsBaseInfo) newsService.getNewsById(newsId);
		news.setNewsStatus(NewsBaseInfo.DELETE_STATUS);
		// news.setNick(loginId);
		// news.setMemberId("helelelel");
		// news.setCreator(adminUserId);
		news.setModifier(adminUserId);
		try {
			newsService.updateNews(news);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return newsList(request, response);
	}

	public ModelAndView newsApprove(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String adminUserId = parse.getCookyjar().get(
				Constants.AdminUserId_Cookie);
		// String loginId =
		// parse.getCookyjar().get(Constants.MemberLoinName_Cookie);
		String newsId = request.getParameter("id");
		NewsBaseInfo news = (NewsBaseInfo) newsService.getNewsById(newsId);
		news.setNewsStatus(NewsBaseInfo.NORMAL_STATUS);
		news.setModifier(adminUserId);
		try {
			newsService.updateNews(news);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// TODO Auto-generated method stub
		}
		return newsList(request, response);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public FormFactory getFormFactory() {
		return formFactory;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public static void main(String[] args) {
		String batchPrice = "32.0";
		long bp = (long) ((Float.valueOf(batchPrice)) * 100);
		System.out.println(bp);
	}

	public NewsContentDAO getNewsContentDAO() {
		return newsContentDAO;
	}

	public void setNewsContentDAO(NewsContentDAO newsContentDAO) {
		this.newsContentDAO = newsContentDAO;
	}

	public NewsTypeDAO getNewsTypeDAO() {
		return newsTypeDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}

}
