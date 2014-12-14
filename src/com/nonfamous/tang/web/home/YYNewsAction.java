package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.NewsService;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.SearchTypeConstants;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

public class YYNewsAction extends MultiActionController 
{
	private FormFactory formFactory;
	
	private NewsService newsService;

	private NewsTypeDAO newsTypeDAO;

	private NewsContentDAO newsContentDAO;

	private MemberDAO memberDAO;

	private NewsDAO newsDAO;
	
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
		ModelAndView mv = new ModelAndView("home/yynews/detail");
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

	public MemberDAO getMemberDAO() {
		return memberDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public NewsContentDAO getNewsContentDAO() {
		return newsContentDAO;
	}

	public void setNewsContentDAO(NewsContentDAO newsContentDAO) {
		this.newsContentDAO = newsContentDAO;
	}

	public NewsDAO getNewsDAO() {
		return newsDAO;
	}

	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public NewsTypeDAO getNewsTypeDAO() {
		return newsTypeDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}

	public FormFactory getFormFactory() {
		return formFactory;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}
}