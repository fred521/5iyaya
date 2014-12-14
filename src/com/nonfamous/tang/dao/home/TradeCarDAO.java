//===================================================================
// Created on 2007-9-17
//===================================================================
package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.trade.TradeCar;
import com.nonfamous.tang.domain.trade.TradeCarItem;

/**
 * <p>
 *  购物车
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */

public interface TradeCarDAO {

    /**
     * 新增到购物车
     * @param tc
     * @throws DataAccessException
     */
    public void insert(TradeCar tc) throws DataAccessException;

    /**
     * 从购物车中删除
     * @param id
     * @throws DataAccessException
     */
    public int delete(Long id) throws DataAccessException;

    /**
     * 获取购物车里的一件商品
     * @param id
     * @throws DataAccessException
     */
    public TradeCar getTradeCarItemById(Long id) throws DataAccessException;
    
    /**
     * 获取购物车里的一件商品
     * @param goodsId
     * @throws DataAccessException
     */
    public TradeCar getTradeCarByGoodsIdWithOwner(String goodsId,String owner) throws DataAccessException;

    /**
     * 统计购物车中商品的个数
     * @param shopId
     * @param owner
     * @throws DataAccessException
     */
    public Integer countByShopIdAndOwner(String shopId,String owner) throws DataAccessException;

    /**
     * 获取购物车中某个店铺下所有商品
     * @param shopId
     * @param owner
     * @return
     * @throws DataAccessException
     */
    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId,String owner) throws DataAccessException;

    /**
     * 我的采购单（分页）
     * @param query
     * @return
     * @throws DataAccessException
     */
    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query) throws DataAccessException;

}
