package com.nonfamous.tang.service.search.pojo;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.SearchServiceBase;
import com.nonfamous.tang.dao.home.SearchNewsDAO;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.service.search.NewsIndexService;

/**
 * 
 * @author victor
 * 
 */
public class POJONewsIndexService extends SearchServiceBase implements
		NewsIndexService {
	private static final int KEYWORDHITNUM = 0;

	private SearchNewsDAO searchNewsDAO;

	public void init() {
		searchNewsDAO.updateNewsIndex();
	}

	public void createNewsIndex() {
		searchNewsDAO.createNewsIndex();
	}

	public void updateNewsIndex() {
		searchNewsDAO.updateNewsIndex();
	}

	public SearchNewsQuery findNews(SearchNewsQuery query) {
		query = searchNewsDAO.findAllNews(query);
		if (!StringUtils.isEmpty(query.getKeyWords())
				&& query.getTotalItem() > KEYWORDHITNUM) {
			query.setKeyWordList(this.getHitKeyWords(query.getKeyWords()));
		}
		return query;
	}

	public void setSearchNewsDAO(SearchNewsDAO searchNewsDAO) {
		this.searchNewsDAO = searchNewsDAO;
	}

	public void rebuildNewsIndex() {
		searchNewsDAO.rebuildNewsIndex();
	}

}
