//===================================================================
// Created on 2007-9-18
//===================================================================
package com.nonfamous.tang.domain.trade;

import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * <p>
 *  购物车里的商品
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarItem.java,v 1.1 2008/07/11 00:46:44 fred Exp $
 */

public class TradeCarItem extends DomainBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1332889858620508515L;

    private TradeCar          tradeCar;

    private GoodsBaseInfo     goodsBaseInfo;

    private Shop              shop;

    public GoodsBaseInfo getGoodsBaseInfo() {
        return goodsBaseInfo;
    }

    public void setGoodsBaseInfo(GoodsBaseInfo goodsBaseInfo) {
        this.goodsBaseInfo = goodsBaseInfo;
    }

    public TradeCar getTradeCar() {
        return tradeCar;
    }

    public void setTradeCar(TradeCar tradeCar) {
        this.tradeCar = tradeCar;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
