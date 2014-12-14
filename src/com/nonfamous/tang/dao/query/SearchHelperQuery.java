package com.nonfamous.tang.dao.query;

import java.util.Date;
import java.util.List;

import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.domain.NewsBaseInfo;

/**
 * 
 * @author fred
 * 
 */
public class SearchHelperQuery extends SearchQueryBase {

	private static final long serialVersionUID = 2254818574753999780L;

	private String helperType;// 要搜索的帮助分类

	private List<Helper> searchHelperList;// 返回搜索结果

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

	public String getHelperType() {
		return helperType;
	}

	public void setHelperType(String helperType) {
		this.helperType = helperType;
	}

	public List<Helper> getSearchHelperList() {
		return searchHelperList;
	}

	public void setSearchHelperList(List<Helper> searchHelperList) {
		this.searchHelperList = searchHelperList;
	}

}
