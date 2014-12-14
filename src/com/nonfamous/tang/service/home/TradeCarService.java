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
 *  ���ﳵservice
 * </p>
 * @author jacky
 * @version $Id: TradeCarService.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */

public interface TradeCarService {
    /**
     * ����һ����Ʒ�����ﳵ��
     * �������Ʒ���ڵĵ��̲��ڹ��ﳵ�ĵ����б��У���Ҫ�����̼��뵽������
     * @param goodsId
     * @param owner
     */
    public TradeResult addGoodsToCar(String goodsId, String owner);

    /**
     * �ӹ��ﳵ��ɾ��һ����Ʒ
     * �������Ʒ�ǹ��ﳵ�е����һ����Ʒ����Ҫ������Ʒ�ĵ��̵Ĵӹ�������ɾ��
     * @param id
     * @param owner
     */
    public void deleteGoodsFromCar(long id, String owner);

    /**
     * ��ȡ���ﳵ��ĳ��������������Ʒ
     * @param shopId
     * @param owner
     * @return List
     */
    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId, String owner);

    /**
     * ��ȡ���ﳵ�е����б�
     * @param owner
     * @return List
     */
    public List<TradeCarShopExt> getTradecarshopList(String owner);

    /**
     * �ҵĲɹ�������ҳ��
     * @param query
     * @return TradeCarQuery
     */
    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query);
}
