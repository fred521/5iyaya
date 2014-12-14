//===================================================================
// Created on 2007-9-17
//===================================================================
package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.trade.TradeCarShop;
import com.nonfamous.tang.domain.trade.TradeCarShopExt;

/**
 * <p>
 *  购物车中所有的店铺表
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarShopDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */

public interface TradeCarShopDAO {
    /**
     * 新增
     * @param tc
     * @throws DataAccessException
     */
    public void insert(TradeCarShop tcs) throws DataAccessException;

    /**
     * 删除
     * @param id
     * @throws DataAccessException
     */
    public void delete(TradeCarShop tcs) throws DataAccessException;

    /**
     * 获取店铺
     * @param tcs
     * @return
     * @throws DataAccessException
     */
    public TradeCarShop getByOwnerAndShopId(String owner, String shopId) throws DataAccessException;

    /**
     * 获取购物车中的店铺列表
     * @param owner
     * @return
     * @throws DataAccessException
     */
    public List<TradeCarShopExt> getTradecarshopListByOwner(String owner) throws DataAccessException;

}
