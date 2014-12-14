//===================================================================
// Created on 2007-9-19
//===================================================================
package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.SignShopQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.trade.SignShop;

/**
 * <p>
 * 用户签约店铺
 * </p>
 * 
 * @author HGS-gonglei
 * @version $Id: SignShopDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */

public interface SignShopDAO {
	/**
	 * 我的签约店铺（分页）
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public SignShopQuery getMySignShopPaging(SignShopQuery query)
			throws DataAccessException;

	/**
	 * 新增签约店铺
	 * 
	 * @param tc
	 * @throws DataAccessException
	 */
	public void insert(SignShop ss) throws DataAccessException;

	/**
	 * 删除签约店铺
	 * 
	 * @param shopId
	 * @param memberId
	 * @throws DataAccessException
	 */
	public void delete(String shopId, String memberId)
			throws DataAccessException;

	/**
	 * 获取签约店铺
	 * 
	 * @param shopId
	 * @param memberId
	 * @throws DataAccessException
	 */
	public SignShop getByShopAndMember(String shopId, String memberId)
			throws DataAccessException;

	/**
	 * 我的签约店铺（不分页）
	 * 
	 * @param memberId
	 * @return
	 */
	public List<Shop> getMySignShopList(String memberId);

}
