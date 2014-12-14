package com.nonfamous.tang.dao.home.ibatis;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.YYNewsDAO;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;

public class IbatisYYNewsDAO extends SqlMapClientDaoSupport implements
		YYNewsDAO {
	public static final String TYPE_SPACE = "NEWSTYPE_SPACE.";

	public static final String CONTENT_SPACE = "NEWSCONTENT_SPACE.";

	public static final String NEWS_SPACE = "NEWSBASEINFO_SPACE.";

	@SuppressWarnings("unchecked")
	public List<NewsBaseInfo> getYYNewsListForIndex(SearchNewsQuery query)
			throws DataAccessException {
		Integer totalItem = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(NEWS_SPACE + "QUERY_YY_NEWS_COUNT_FOR_INDEX",
						query);
		query.setTotalItem(totalItem);
		if (totalItem.intValue() == 0) {
			return java.util.Collections.EMPTY_LIST;
		}
		return this.getSqlMapClientTemplate().queryForList(
				NEWS_SPACE + "QUERY_YY_NEWS_LIST_FOR_INDEX", query);
	}

	public Date getSysDate() throws DataAccessException {
		return (Date) this.getSqlMapClientTemplate().queryForObject(
				NEWS_SPACE + "getSysDate");
	}
}