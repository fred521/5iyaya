//===================================================================
// Created on 2007-9-17
//===================================================================
package com.nonfamous.tang.dao.home.ibatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.TradeCarDAO;
import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.trade.TradeCar;
import com.nonfamous.tang.domain.trade.TradeCarItem;

/**
 * <p>
 * TradeCarDAO µœ÷
 * </p>
 * 
 * @author HGS-gonglei
 * @version $Id: IbatisTradeCarDAO.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */

public class IbatisTradeCarDAO extends SqlMapClientDaoSupport implements TradeCarDAO {

    public static final String SPACE = "TRADECAR_SPACE.";

    public int delete(Long id) throws DataAccessException {
        if (id == null) {
            throw new NullPointerException("tradecar id can not be null");
        }
        return this.getSqlMapClientTemplate().delete(SPACE + "delete_tradecar", id);

    }

    public void insert(TradeCar tc) throws DataAccessException {
        if (tc == null) {
            throw new NullPointerException("tradecar can not be null");
        }
        this.getSqlMapClientTemplate().insert(SPACE + "tradecar_insert", tc);
    }

    public TradeCar getTradeCarByGoodsIdWithOwner(String goodsId, String owner)
                                                                               throws DataAccessException {
        if (StringUtils.isBlank(goodsId) || StringUtils.isBlank(owner)) {
            throw new NullPointerException("goods id or owner can not be null");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("goodsId", goodsId);
        map.put("owner", owner);
        return (TradeCar) this.getSqlMapClientTemplate()
                              .queryForObject(SPACE + "get_tradecar_by_goods_id_with_owner", map);
    }

    public TradeCar getTradeCarItemById(Long id) throws DataAccessException {
        if (id == null) {
            throw new NullPointerException("tradecar id can not be null");
        }
        return (TradeCar) this.getSqlMapClientTemplate().queryForObject(SPACE + "get_tradecar", id);
    }

    public Integer countByShopIdAndOwner(String shopId, String owner) throws DataAccessException {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(owner)) {
            throw new NullPointerException("shopId or owner can not be null");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("shopId", shopId);
        map.put("owner", owner);
        return (Integer) this.getSqlMapClientTemplate()
                             .queryForObject(SPACE + "count_by_shopid_owner", map);
    }

    @SuppressWarnings("unchecked")
    public List<TradeCarItem> getGoodsByShopIdAndOwner(String shopId, String owner)
                                                                                   throws DataAccessException {
        if (StringUtils.isBlank(shopId)) {
            throw new NullPointerException("shopId can not be null");
        }
        Map<String, String> map = new HashMap();
        map.put("shopId", shopId);
        map.put("owner", owner);
        return (List) this.getSqlMapClientTemplate()
                          .queryForList(SPACE + "get_goods_by_shopid_and_owner", map);
    }

    @SuppressWarnings("unchecked")
    public TradeCarQuery getMyTradeCarPaging(TradeCarQuery query) throws DataAccessException {
        if (query == null) {
            throw new NullPointerException("TradeCarQuery can't be null");
        }
        Integer total = (Integer) this.getSqlMapClientTemplate()
                                      .queryForObject(SPACE + "count_my_tradecar_items", query);
        query.setTotalItem(total);
        if (total == 0) {
            query.setItems(Collections.EMPTY_LIST);
            return query;
        }
        List<TradeCarItem> items = this.getSqlMapClientTemplate()
                                       .queryForList(SPACE + "get_my_tradecar_paging", query);
        query.setItems(items);
        return query;
    }

}
