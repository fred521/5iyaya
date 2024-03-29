package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchNewsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchNewsDAO {
	/**
	 * 搜索资讯，返回资讯集合
	 * */
	public SearchNewsQuery findAllNews(SearchNewsQuery query);
	/**
	 * 重新建立资讯索引，取数据的开始时间从定时记录数据表中读取
	 * */
	public void createNewsIndex();
	/**
	 * 重新建立资讯索引，取数据的开始时间为2000年
	 * */
	public void rebuildNewsIndex();
	/**
	 * 更新资讯索引
	 * */
	public void updateNewsIndex();
}
