//===================================================================
// Created on 2007-9-19
//===================================================================
package com.nonfamous.tang.dao.home.ibatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.SignShopDAO;
import com.nonfamous.tang.dao.query.SignShopQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.trade.SignShop;

/**
 * <p>
 * «©‘ºµÍ∆Ã
 * </p>
 * 
 * @author HGS-gonglei
 * @version $Id: IbatisSignShopDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */

public class IbatisSignShopDAO extends SqlMapClientDaoSupport implements
		SignShopDAO {

	public static final String SPACE = "SIGNSHOP_SPACE.";

	@SuppressWarnings("unchecked")
	public void delete(String shopId, String memberId)
			throws DataAccessException {
		if (shopId == null || StringUtils.isBlank(memberId)) {
			throw new NullPointerException(
					"signshop id or memberId can not be null");
		}
		Map map = new HashMap();
		map.put("shopId", shopId);
		map.put("memberId", memberId);
		this.getSqlMapClientTemplate().delete(SPACE + "delete_signshop", map);

	}

	@SuppressWarnings("unchecked")
	public SignShopQuery getMySignShopPaging(SignShopQuery query)
			throws DataAccessException {
		if (query == null) {
			throw new NullPointerException("SignShopQuery can't be null");
		}
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "count_my_signshop", query);
		query.setTotalItem(total);
		if (total == 0) {
			query.setItems(Collections.EMPTY_LIST);
			return query;
		}
		List<Shop> items = this.getSqlMapClientTemplate().queryForList(
				SPACE + "get_my_signshop_paging", query);
		query.setItems(items);
		return query;
	}

	public void insert(SignShop ss) throws DataAccessException {
		if (ss == null) {
			throw new NullPointerException("SignShop can not be null");
		}
		this.getSqlMapClientTemplate().insert(SPACE + "signshop_insert", ss);

	}

	@SuppressWarnings("unchecked")
	public SignShop getByShopAndMember(String shopId, String memberId)
			throws DataAccessException {
		Map map = new HashMap();
		map.put("shopId", shopId);
		map.put("memberId", memberId);
		return (SignShop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_by_shop_and_member", map);
	}

	@SuppressWarnings("unchecked")
	public List<Shop> getMySignShopList(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can not be null");
		}
		return this.getSqlMapClientTemplate().queryForList(
				SPACE + "get_my_signshop", memberId);
	}

}
