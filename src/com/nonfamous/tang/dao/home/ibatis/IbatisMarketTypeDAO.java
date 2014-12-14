package com.nonfamous.tang.dao.home.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisMarketTypeDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisMarketTypeDAO extends SqlMapClientDaoSupport implements
		MarketTypeDAO {
	public static final String SPACE = "MARKETTYPE_SPACE.";

	@SuppressWarnings("unchecked")
	public List<MarketType> getAllMarketType() {
		return getSqlMapClientTemplate().queryForList(
				SPACE + "get_markettype_list", null);
	}

	public MarketType getMarketTypeById(String typeId) {
		return (MarketType) getSqlMapClientTemplate().queryForObject(
				SPACE + "get_markettype", typeId);
	}

//	public List<Shop> getShopByMarketId(String MarketId) {
//		return getSqlMapClientTemplate().queryForList(SPACE + "get_shops_marketid", MarketId);
//	}
	
	@SuppressWarnings("unchecked")
	public List<Shop> getAllShopByMarketId() {
		return getSqlMapClientTemplate().queryForList(SPACE + "get_all_shops_marketid",null);
	}

}
