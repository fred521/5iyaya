package com.nonfamous.tang.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.NewsService;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.SearchTypeConstants;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

/**
 * 
 * @author fred
 * 
 */
public class NewsMemmberAction extends MultiActionController {

	private FormFactory formFactory;

	private NewsService newsService;

	private NewsTypeDAO newsTypeDAO;

	private NewsContentDAO newsContentDAO;

	private MemberDAO memberDAO;

	private NewsDAO newsDAO;

	public ModelAndView newsAddInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/news/newsAdd");
		List newsType = newsTypeDAO.getAllNewsType();
		mav.addObject("newsType", newsType);
		return mav;
	}

	public ModelAndView newsAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/news/newsAdd");
		Form form = formFactory.getForm("addNews", request);
		request.setAttribute("form", form);
		/**
		 * 如果不设置，那么重复点击修改会变成新增
		 */
		request.setAttribute("id",request.getParameter("id"));
		if (!form.isValide()) {
			return newsAddInit(request, response);
		}
		RequestValueParse parse = new RequestValueParse(request);
		NewsBaseInfo news = new NewsBaseInfo();
		news.setNewsTitle(HtmlUtils
				.parseHtml(request.getParameter("newsTitle")));
		form.getField("newsTitle").setValue(news.getNewsTitle());
		if ( StringUtils.isBlank(request.getParameter("abandonDays"))){
			news.setAbandonDays(new Long(10));
		}else{
			news.setAbandonDays(new Long(request.getParameter("abandonDays")));
		}
		news.setNewsType(request.getParameter("newsType"));
		news.setPhone(request.getParameter("phone"));
		String content = request.getParameter("content");
		news.getContent().setContent(HtmlUtils.escapeScriptTag(content));
		form.getField("content").setValue(news.getContent().getContent());
		// need modification here
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		String loginId = parse.getCookyjar().get(
				Constants.MemberLoinName_Cookie);
		news.setNick(loginId);
		news.setMemberId(memberId);
		news.setCreator(memberId);
		news.setViewCount(Long.valueOf(0));
		news.setModifier(memberId);
		// entry from member the news status need to be verified with the value
		// of "W"
		news.setNewsStatus(NewsBaseInfo.WAITING_STATUS);
		String infoShow = null;
		String newsId = request.getParameter("id");
		if (StringUtils.isBlank(newsId)) {
			newsService.addNews(news);
			infoShow = "新增信息成功";
		} else {
			news.setNewsId(newsId);
			NewsBaseInfo tempNews = newsDAO.getNewsBaseInfoById(newsId);
			if (!memberId.equals(tempNews.getMemberId())) {
				throw new ServiceException("不能修改他人的信息");
			}
			newsService.updateNews(news);
			infoShow = "修改信息成功";
		}

		List newsType = newsTypeDAO.getAllNewsType();
		mv.addObject("newsType", newsType);
		mv.addObject("infoShow", infoShow);
		return mv;

	}

	public ModelAndView newsUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return newsAddInit(request, response);

	}

	public ModelAndView newsUpdateInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/news/newsAdd");
		String newsId = request.getParameter("id");
		NewsBaseInfo news = (NewsBaseInfo) newsService.getNewsById(newsId);
		Form form = formFactory.getForm("addNews", request);
		form.getField("newsTitle").setValue(news.getNewsTitle());
		form.getField("phone").setValue(news.getPhone());
		form.getField("newsType").setValue(news.getNewsType());
		form.getField("abandonDays").setValue(
				Long.toString(news.getAbandonDays()));
		form.getField("content").setValue(news.getContent().getContent());
		List newsType = newsTypeDAO.getAllNewsType();
		mv.addObject("newsType", newsType);
		mv.addObject("id", newsId);
		mv.addObject("form", form);
		return mv;
	}

	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String newsId = rvp.getParameterOrAttribute("id").getString();
		if (StringUtils.isBlank(newsId)) {
			throw new Exception("newsId is null");
		}
		NewsBaseInfo news = newsService.getNewsById(newsId);
		if (news == null
				|| !news.getNewsStatus().equalsIgnoreCase(
						NewsBaseInfo.NORMAL_STATUS)) {
			throw new ServiceException("您要找的分类信息不存在，或已经删除");
		}
		NewsTypeAndInfoDTO dto = newsDAO.getNewsInfoByNewsType(news
				.getNewsType());
		String memberId = news.getMemberId();

		Member member = memberDAO.getMemberById(memberId);
		ModelAndView mv = new ModelAndView("home/news/detail");
		mv.addObject("news", news);
		mv.addObject("member", member);
		mv.addObject("dto", dto);
		// 浏览量加一
		this.newsService.addViewCount(newsId);
		request.setAttribute("searchType", SearchTypeConstants.Search_News);
		// 页面标题
		request.setAttribute(Constants.PageTitle_Page, news.getNewsTitle());
		return mv;
	}

	public ModelAndView preview(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String newsId = rvp.getParameterOrAttribute("id").getString();
		if (StringUtils.isBlank(newsId)) {
			throw new Exception("newsId is null");
		}
		NewsBaseInfo news = newsService.getNewsById(newsId);
		if (news == null) {
			throw new Exception("您要找的信息不存在");
		}

		ModelAndView mv = new ModelAndView("home/news/preview");
		mv.addObject("news", news);
		return mv;
	}

	public ModelAndView newsMemberList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		NewsQuery query = new NewsQuery();
		query.setMemberId(rvp.getCookyjar().get(Constants.MemberId_Cookie));
		String currentPage = rvp.getParameter("current").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPageString(currentPage);
		}
		String newsStatus = request.getParameter("newsStatus");
		if (StringUtils.isNotBlank(newsStatus)) {
			query.setNewsStatus(newsStatus);
		}
		List newsList = newsService.getQueryNewsList(query);
		request.setAttribute("query", query);
		ModelAndView mav = new ModelAndView("home/news/newsMemberList");
		mav.addObject("newsList", newsList);
		// mav.addObject("search", query);
		return mav;
	}

	public ModelAndView newsDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		String loginId = parse.getCookyjar().get(
				Constants.MemberLoinName_Cookie);
		String newsId = request.getParameter("id");
		NewsBaseInfo tempNews = newsDAO.getNewsBaseInfoById(newsId);
		if (!memberId.equals(tempNews.getMemberId())) {
			throw new ServiceException("不能删除他人的信息");
		}
		ModelAndView mv = new ModelAndView("home/news/newsMemberList");
		NewsBaseInfo news = newsService.getNewsById(newsId);
		news.setNewsStatus(NewsBaseInfo.DELETE_STATUS);
		news.setNick(loginId);
		news.setMemberId(memberId);
		news.setCreator(memberId);
		news.setModifier(memberId);
		newsService.updateNews(news);
		NewsQuery query = new NewsQuery();
		// query.setCurrentPageString(parse.getParameter("current").getString());
		query.setMemberId(memberId);
		List newsList = newsService.getQueryNewsList(query);
		request.setAttribute("query", query);
		mv.addObject("newsList", newsList);
		return mv;
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

	public NewsTypeDAO getNewsTypeDAO() {
		return newsTypeDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}

	public NewsContentDAO getNewsContentDAO() {
		return newsContentDAO;
	}

	public void setNewsContentDAO(NewsContentDAO newsContentDAO) {
		this.newsContentDAO = newsContentDAO;
	}

	public MemberDAO getMemberDAO() {
		return memberDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public NewsDAO getNewsDAO() {
		return newsDAO;
	}

	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

}
