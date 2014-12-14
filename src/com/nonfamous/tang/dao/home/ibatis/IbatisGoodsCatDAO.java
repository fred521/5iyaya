package com.nonfamous.tang.dao.home.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.domain.GoodsCat;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisGoodsCatDAO.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */
public class IbatisGoodsCatDAO extends SqlMapClientDaoSupport implements
		GoodsCatDAO {
	public static final String SPACE = "GOODSCAT_SPACE.";

	@SuppressWarnings("unchecked")
	public List<GoodsCat> getAllGoodsCat() {
		return getSqlMapClientTemplate().queryForList(
				SPACE + "get_goodscat_list");
	}

	public GoodsCat getGoodsCatById(String catId) throws DataAccessException {
		if (catId == null) {
			throw new NullPointerException("GoodsCat can't be null");
		}
		return (GoodsCat) getSqlMapClientTemplate().queryForObject(
				SPACE + "get_goodscat", catId);
	}

	public List<GoodsCat> getRootGoodsCat() throws DataAccessException {
		List<GoodsCat> goods_cats = getAllGoodsCat();
		List<GoodsCat> rtn = new ArrayList<GoodsCat>();
		for (GoodsCat cat : goods_cats) {
			if (cat.isRoot()) {
				rtn.add(cat);
			}
		}
		return rtn;
	}

	public GoodsCat getGoosCatByName(String catName) {
		throw new UnsupportedOperationException();
	}	
	
}
