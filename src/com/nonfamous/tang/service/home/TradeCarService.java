//===================================================================
// Created on Sep 17, 2007
//===================================================================
package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.result.TradeResult;
import com.nonfamous.tang.domain.trade.TradeCarItem;
import com.nonfamous.tang.domain.trade.TradeCarShopExt;

/**
 * <p>
 *  购物车service
 * </p>
 * @author jacky
 * @version $Id: TradeCarService.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */

public interface TradeCarService {
    /**
     * 增加一个商品到购物车中
     * 如果该商品所在的店铺不在购物车的店铺列表中，需要将店铺加入到关联表
     * @param goodsId
     * @param owner
     */
    public TradeResult addGoodsToCar(String goodsId, String owner);

    /**
     * 从购物车中删除一件商品
     * 如果该商品是购物车中的最后一件商品，需要将该商品的店铺的从关联表中删除
     * @param id
     * @param owner
     */
    public void deleteGoodsFromCar(long id, String owner);

    /**
     * 获取购物车中某个店铺下所有商品
     * @param shopId
     * @param owner
     * @return List
     */
    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId, String owner);

    /**
     * 获取购物车中店铺列表
     * @param owner
     * @return List
     */
    public List<TradeCarShopExt> getTradecarshopList(String owner);

    /**
     * 我的采购单（分页）
     * @param query
     * @return TradeCarQuery
     */
    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query);
}
