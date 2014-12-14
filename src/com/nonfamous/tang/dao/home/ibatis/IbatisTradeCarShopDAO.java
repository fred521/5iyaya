//===================================================================
// Created on 2007-9-17
//===================================================================
package com.nonfamous.tang.dao.home.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.TradeCarShopDAO;
import com.nonfamous.tang.domain.trade.TradeCarShop;
import com.nonfamous.tang.domain.trade.TradeCarShopExt;

/**
 * <p>
 *  TradeCarShopDAO µœ÷
 * </p>
 * @author HGS-gonglei
 * @version $Id: IbatisTradeCarShopDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */

public class IbatisTradeCarShopDAO extends SqlMapClientDaoSupport implements TradeCarShopDAO {

    public static final String SPACE            = "TRADECARSHOP_SPACE.";

    private static final long  serialVersionUID = 208580606587774835L;

    public void delete(TradeCarShop tcs) throws DataAccessException {
        if (tcs == null) {
            throw new NullPointerException("tradecarshop  can not be null");
        }
        this.getSqlMapClientTemplate().delete(SPACE + "delete_tradecarshop", tcs);

    }

    public TradeCarShop getByOwnerAndShopId(String owner, String shopId) throws DataAccessException {
        if (StringUtils.isBlank(shopId) || StringUtils.isBlank(owner)) {
            throw new NullPointerException("shopid or owner can not be null");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("shopId", shopId);
        map.put("owner", owner);
        return (TradeCarShop) this.getSqlMapClientTemplate()
                                  .queryForObject(SPACE + "get_by_owner_and_shopid", map);
    }

    public void insert(TradeCarShop tcs) throws DataAccessException {
        if (tcs == null) {
            throw new NullPointerException("tradecarshop can not be null");
        }
        this.getSqlMapClientTemplate().insert(SPACE + "tradecarshop_insert", tcs);
    }

    @SuppressWarnings("unchecked")
    public List<TradeCarShopExt> getTradecarshopListByOwner(String owner) throws DataAccessException {
        if (StringUtils.isBlank(owner)) {
            throw new NullPointerException("owner can not be null or blank");
        }
        return this.getSqlMapClientTemplate()
                   .queryForList(SPACE + "get_tradecarshop_list_by_owner", owner);
    }

}
