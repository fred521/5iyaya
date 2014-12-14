package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.result.NewsBaseInfoResult;

/**
 * 
 * @author fred
 * 
 */
public interface NewsService {

	/**
	 * 添加News
	 * 
	 * @param nbi
	 * @param content
	 * @return
	 */
	public NewsBaseInfoResult addNews(NewsBaseInfo nbi);

	/**
	 * 更新News信息
	 * 
	 * @param nbi
	 * @param content
	 */
	public NewsBaseInfoResult updateNews(NewsBaseInfo nbi);

	/**
	 * 得到News列表(Normal)
	 * 
	 * @return
	 */
	public NewsBaseInfo getNewsById(String newsId);

	/**
	 * query the news from admin module
	 * 
	 * @param query
	 * @return
	 */
	public List getQueryNewsList(NewsQuery query);

	/**
	 * 根据会员Id 来显示新闻信息
	 * 
	 * @param memberId
	 * @return
	 */
	public List getNewsListByMemberId(String memberId);

	/**
	 * 增加新闻浏览量一
	 * 
	 * @param newsId
	 */
	public void addViewCount(String newsId);
}
