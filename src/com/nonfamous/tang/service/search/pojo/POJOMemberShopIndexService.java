package com.nonfamous.tang.service.search.pojo;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.SearchServiceBase;
import com.nonfamous.tang.dao.home.SearchMemberShopDAO;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.service.search.MemberShopIndexService;

/**
 * 
 * @author victor
 * 
 */
public class POJOMemberShopIndexService extends SearchServiceBase implements
		MemberShopIndexService {
	private static final int KEYWORDHITNUM = 0;

	private SearchMemberShopDAO searchMemberShopDAO;

	public void init() {
		searchMemberShopDAO.updateMemberShopIndex();
	}

	public void createMemberShopIndex() {
		searchMemberShopDAO.createMemberShopIndex();
	}

	public void updateMemberShopIndex() {
		searchMemberShopDAO.updateMemberShopIndex();
	}

	public SearchMemberShopQuery findMemberShop(SearchMemberShopQuery query) {
		query = searchMemberShopDAO.findAllMemberShop(query);
		if (!StringUtils.isEmpty(query.getKeyWords())
				&& query.getTotalItem() > KEYWORDHITNUM) {
			query.setKeyWordList(this.getHitKeyWords(query.getKeyWords()));
		}
		return query;
	}

	public void setSearchMemberShopDAO(SearchMemberShopDAO searchMemberShopDAO) {
		this.searchMemberShopDAO = searchMemberShopDAO;
	}

	public void rebuildMemberShopIndex() {
		searchMemberShopDAO.rebuildMemberShopIndex();
	}

}
