//===================================================================
// Created on 2007-9-21
//===================================================================
package com.nonfamous.tang.domain.result;

/** 
 * @author HGS-gonglei
 * @version $Id: TradeResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */

public class TradeResult extends ResultBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID       = -9129803217478394533L;

    /** 商品不存在 */
    public static final String GOODS_NOT_EXIST        = "GOODS_NOT_EXIST";

    /** 商品已经在采购单中 */
    public static final String GOODS_HAS_IN_TRADE_CAR = "GOODS_HAS_IN_TRADE_CAR";
    
    /** 自己的商品不能放入自己的购物车 */
    public static final String THE_SAME_SELLER_BUYER        = "THE_SAME_SELLER_BUYER";
}
