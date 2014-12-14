package com.nonfamous.tang.service.search.pojo;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.SearchServiceBase;
import com.nonfamous.tang.dao.home.SearchGoodsDAO;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.service.search.BabyIndexService;

public class POJOBabyIndexService extends SearchServiceBase implements
		BabyIndexService {
	private static final int KEYWORDHITNUM = 0;

	private SearchGoodsDAO searchGoodsDAO;

	public void init() {
		searchGoodsDAO.updateGoodsIndex();
	}

	public void createBabyIndex() {
		searchGoodsDAO.createGoodsIndex();
	}

	public void updateBabyIndex() {
		searchGoodsDAO.updateGoodsIndex();
	}

	public void rebuildBabyIndex() {
		searchGoodsDAO.rebuildGoodsIndex();
	}

	public SearchGoodsQuery findBaby(SearchGoodsQuery query) {
		query = searchGoodsDAO.findAllGoods(query);
		if (StringUtils.isNotBlank(query.getKeyWords())
				&& query.getTotalItem() > KEYWORDHITNUM) {
			query.setKeyWordList(this.getHitKeyWords(query.getKeyWords()));
		}
		return query;
	}
	
	public SearchGoodsQuery findEffectBaby(SearchGoodsQuery query)
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