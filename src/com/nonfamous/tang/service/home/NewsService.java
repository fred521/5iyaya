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
	 * ���News
	 * 
	 * @param nbi
	 * @param content
	 * @return
	 */
	public NewsBaseInfoResult addNews(NewsBaseInfo nbi);

	/**
	 * ����News��Ϣ
	 * 
	 * @param nbi
	 * @param content
	 */
	public NewsBaseInfoResult updateNews(NewsBaseInfo nbi);

	/**
	 * �õ�News�б�(Normal)
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
	 * ���ݻ�ԱId ����ʾ������Ϣ
	 * 
	 * @param memberId
	 * @return
	 */
	public List getNewsListByMemberId(String memberId);

	/**
	 * �������������һ
	 * 
	 * @param newsId
	 */
	public void addViewCount(String newsId);
}
