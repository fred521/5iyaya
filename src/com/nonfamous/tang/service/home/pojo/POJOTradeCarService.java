//===================================================================
// Created on Sep 17, 2007
//===================================================================
package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.home.TradeCarDAO;
import com.nonfamous.tang.dao.home.TradeCarShopDAO;
import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.result.TradeResult;
import com.nonfamous.tang.domain.trade.TradeCar;
import com.nonfamous.tang.domain.trade.TradeCarItem;
import com.nonfamous.tang.domain.trade.TradeCarShop;
import com.nonfamous.tang.domain.trade.TradeCarShopExt;
import com.nonfamous.tang.service.home.TradeCarService;

/**
 * <p>
 * POJOTradeCarService
 * </p>
 * 
 * @author jacky
 * @version $Id: POJOTradeCarService.java,v 1.1 2008/07/11 00:46:46 fred Exp $
 */

public class POJOTradeCarService implements TradeCarService {

    private GoodsDAO            goodsDAO;

    private ShopDAO          shopDAO;
    
    private TradeCarDAO         tradeCarDAO;

    private TradeCarShopDAO     tradeCarShopDAO;

    private TransactionTemplate transactionTemplate;

    public TradeResult addGoodsToCar(final String goodsId, final String owner) {
        TradeResult result = (TradeResult) transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {

                TradeResult result = new TradeResult();
                // check goods
                GoodsBaseInfo goods = goodsDAO.getGoodsBaseInfoById(goodsId);
                
                if (goods == null) {
                    result.setErrorCode(TradeResult.GOODS_NOT_EXIST);
                    return result;
                }
                
                Shop shop = shopDAO.shopSelectByMemberId(owner);
                if ( shop != null && shop.getShopId().equalsIgnoreCase(goods.getShopId())){
                	result.setErrorCode(TradeResult.THE_SAME_SELLER_BUYER);
                    return result;
                }
                
                TradeCar carItem = tradeCarDAO.getTradeCarByGoodsIdWithOwner(goodsId, owner);
                if (carItem != null) {
                    result.setErrorCode(TradeResult.GOODS_HAS_IN_TRADE_CAR);
                    return result;
                }
                // new trade car
                TradeCar tc = new TradeCar();
                tc.setGoodsId(goodsId);
                tc.setOwner(owner);
                tc.setShopId(goods.getShopId());
                // insert trade car
                tradeCarDAO.insert(tc);
                // insert car shop
                TradeCarShop tcs = tradeCarShopDAO.getByOwnerAndShopId(tc.getOwner(),
                                                                       tc.getShopId());
                if (tcs == null) {
                    tcs = new TradeCarShop();
                    tcs.setOwner(tc.getOwner());
                    tcs.setShopId(tc.getShopId());
                    tradeCarShopDAO.insert(tcs);
                }
                result.setSuccess(true);
                return result;
            }
        });
        return result;
    }

    public void deleteGoodsFromCar(final long id, final String owner) {
        transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                TradeCar tc = tradeCarDAO.getTradeCarItemById(id);
                int num = 0;
                if (tc != null) {
                    // check owner
                    if (StringUtils.equals(tc.getOwner(), owner)) {
                        // delete trade car
                        num = tradeCarDAO.delete(id);
                        // delete car shop
                        if (tradeCarDAO.countByShopIdAndOwner(tc.getShopId(), tc.getOwner()) == 0) {
                            TradeCarShop tcs = new TradeCarShop();
                            tcs.setOwner(tc.getOwner());
                            tcs.setShopId(tc.getShopId());
                            tradeCarShopDAO.delete(tcs);
                        }
                    }
                }
                return num;
            }
        });

    }

    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId, String owner) {
        return this.tradeCarDAO.getGoodsByShopIdAndOwner(shopId, owner);
    }

    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query) {
        return this.tradeCarDAO.getMyTradeCarPaging(query);
    }

    public List<TradeCarShopExt> getTradecarshopList(String owner) {
        return this.tradeCarShopDAO.getTradecarshopListByOwner(owner);
    }

    public void setTradeCarDAO(TradeCarDAO tradeCarDAO) {
        this.tradeCarDAO = tradeCarDAO;
    }

    public void setTradeCarShopDAO(TradeCarShopDAO tradeCarShopDAO) {
        this.tradeCarShopDAO = tradeCarShopDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}
    
    
}
