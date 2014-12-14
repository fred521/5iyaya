package com.nonfamous.tang.service.home.pojo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.nonfamous.commom.cache.count.HitsCountService;
import com.nonfamous.commom.cache.count.impl.AbstractHitsCountService;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.result.NewsBaseInfoResult;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.NewsService;

/**
 * 
 * @author fred
 * 
 */
public class POJONewsService extends POJOServiceBase implements NewsService {

	private NewsDAO newsDAO;

	private NewsTypeDAO newsTypeDAO;

	private NewsContentDAO newsContentDAO;

	private HitsCountService<String> viewCountService = new AbstractHitsCountService<String>() {

		@Override
		public void writerHits(Map<String, Integer> id2HitsMap) {
			newsDAO.updateNewsViewCount(id2HitsMap);
		}

	};

	public NewsBaseInfoResult addNews(NewsBaseInfo nbi) {
		if (nbi == null) {
			throw new ServiceException("News info can't be null");
		}
		NewsBaseInfoResult result = new NewsBaseInfoResult();
		if (StringUtils.isEmpty(nbi.getPhone())) {
			result.setErrorCode(NewsBaseInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("手机号码不能为空");
			return result;
		}
		if (StringUtils.isEmpty(nbi.getNewsTitle())) {
			result.setErrorCode(NewsBaseInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("信息标题不能为空");
			return result;
		}
		if (StringUtils.isEmpty(nbi.getContent().getContent())) {
			result.setErrorCode(NewsBaseInfoResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("信息描述不能为空");
			return result;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, nbi.getAbandonDays().intValue());
		nbi.setGmtAbandon(cal.getTime());
		// 3.insert into the database
		String newsId = UUIDGenerator.generate();
		nbi.setNewsId(newsId);
		// include the inserter News base info and news content function
		newsDAO.insertNews(nbi);
		result.setNewsId(newsId);
		result.setSuccess(true);
		return result;

	}

	public NewsBaseInfoResult updateNews(NewsBaseInfo nbi) {
		// 1、判断入参是否为空

		if (nbi == null) {
			throw new NullPointerException("news base info can't be null");
		}
		NewsBaseInfoResult result = new NewsBaseInfoResult();

		NewsBaseInfo tmp = getNewsById(nbi.getNewsId());
		if (tmp == null) {
			result.setErrorCode(NewsBaseInfoResult.ERROR_NO_NEWS);
			return result;
		}
		//如果过期时间不相等，则需要更新过期时间
		if ( tmp.getAbandonDays().intValue() != nbi.getAbandonDays().intValue() ){
			nbi.setGmtAbandon(DateUtils.getDiffDateFromEnterDate(tmp.getGmtCreate(), nbi.getAbandonDays().intValue()));
		}
		// 2、更新新闻
		int count = newsDAO.updateNews(nbi);
		// 3、判断该新闻是否存在
		if (count == 0) {
			result.setErrorCode(NewsBaseInfoResult.ERROR_NO_NEWS);
			return result;
		}
		result.setSuccess(true);
		return result;

	}

	public NewsBaseInfo getNewsById(String newsId) {
		if (StringUtils.isBlank(newsId)) {
			throw new ServiceException("newsId is null");
		}
		return newsDAO.getNewsBaseInfoById(newsId);
	}

	public List getQueryNewsList(NewsQuery query) {
		return this.newsDAO.queryNewsList(query);
	}

	public List getNewsListByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addViewCount(String newsId) {
		if (newsId == null) {
			throw new NullPointerException("news id can't be null");
		}
		this.viewCountService.addHitOnce(newsId);
	}

	public void destroy() {
		this.viewCountService.flush();
	}

	public HitsCountService<String> getViewCountService() {
		return viewCountService;
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

	public NewsTypeDAO getNewsTypeDAO() {
		return newsTypeDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}
}
