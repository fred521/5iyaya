package com.nonfamous.tang.service.search.pojo;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.SearchServiceBase;
import com.nonfamous.tang.dao.home.SearchGoodsDAO;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.service.search.GoodsIndexService;

/**
 * 
 * @author victor
 * 
 */
public class POJOGoodsIndexService extends SearchServiceBase implements
		GoodsIndexService {
	private static final int KEYWORDHITNUM = 0;

	private SearchGoodsDAO searchGoodsDAO;

	public void init() {
		searchGoodsDAO.updateGoodsIndex();
	}

	public void createGoodsIndex() {
		searchGoodsDAO.createGoodsIndex();
	}

	public void updateGoodsIndex() {
		searchGoodsDAO.updateGoodsIndex();
	}

	public void rebuildGoodsIndex() {
		searchGoodsDAO.rebuildGoodsIndex();
	}

	public SearchGoodsQuery findGoods(SearchGoodsQuery query) {
		query = searchGoodsDAO.findAllGoods(query);
		if (StringUtils.isNotBlank(query.getKeyWords())
				&& query.getTotalItem() > KEYWORDHITNUM) {
			query.setKeyWordList(this.getHitKeyWords(query.getKeyWords()));
		}
		return query;
	}
	
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query)
	{
		query = searchGoodsDAO.findEffectGoods(query);
		if (StringUtils.isNotBlank(query.getKeyWords())
				&& query.getTotalItem() > KEYWORDHITNUM) {
			query.setKeyWordList(this.getHitKeyWords(query.getKeyWords()));
		}
		return query;	
	}

	public void setSearchGoodsDAO(SearchGoodsDAO searchGoodsDAO) {
		this.searchGoodsDAO = searchGoodsDAO;
	}
}
