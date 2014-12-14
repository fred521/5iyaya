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
 *  ���ﳵ
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */

public interface TradeCarDAO {

    /**
     * ���������ﳵ
     * @param tc
     * @throws DataAccessException
     */
    public void insert(TradeCar tc) throws DataAccessException;

    /**
     * �ӹ��ﳵ��ɾ��
     * @param id
     * @throws DataAccessException
     */
    public int delete(Long id) throws DataAccessException;

    /**
     * ��ȡ���ﳵ���һ����Ʒ
     * @param id
     * @throws DataAccessException
     */
    public TradeCar getTradeCarItemById(Long id) throws DataAccessException;
    
    /**
     * ��ȡ���ﳵ���һ����Ʒ
     * @param goodsId
     * @throws DataAccessException
     */
    public TradeCar getTradeCarByGoodsIdWithOwner(String goodsId,String owner) throws DataAccessException;

    /**
     * ͳ�ƹ��ﳵ����Ʒ�ĸ���
     * @param shopId
     * @param owner
     * @throws DataAccessException
     */
    public Integer countByShopIdAndOwner(String shopId,String owner) throws DataAccessException;

    /**
     * ��ȡ���ﳵ��ĳ��������������Ʒ
     * @param shopId
     * @param owner
     * @return
     * @throws DataAccessException
     */
    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId,String owner) throws DataAccessException;

    /**
     * �ҵĲɹ�������ҳ��
     * @param query
     * @return
     * @throws DataAccessException
     */
    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query) throws DataAccessException;

}
