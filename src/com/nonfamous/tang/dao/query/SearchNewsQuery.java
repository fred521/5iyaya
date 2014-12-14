package com.nonfamous.tang.dao.query;

import java.util.Date;
import java.util.List;

import com.nonfamous.tang.domain.NewsBaseInfo;

/**
 * 
 * @author fred
 * 
 */
public class SearchNewsQuery extends SearchQueryBase {

	private static final long serialVersionUID = 2254818574753999780L;

	private String newsType;// 要搜索的资讯分类

	private List<NewsBaseInfo> searchNewsList;// 返回搜索结果

	private Date begin;

	private Date end;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public List<NewsBaseInfo> getSearchNewsList() {
		return searchNewsList;
	}

	public void setSearchNewsList(List<NewsBaseInfo> searchNewsList) {
		this.searchNewsList = searchNewsList;
	}

}
