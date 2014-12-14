//===================================================================
// Created on 2007-9-17
//===================================================================
package test.com.nonfamous.tang.dao.home;

import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.TradeCarShopDAO;
import com.nonfamous.tang.domain.trade.TradeCarShop;

/**
 * <p>
 *  TradeCarDAOTest
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarShopDAOTest.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */

public class TradeCarShopDAOTest extends DAOTestBase {
    private TradeCarShopDAO tradeCarShopDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tradeCarShopDAO = (TradeCarShopDAO) this.daoBeanFactory.getBean("tradeCarShopDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        tradeCarShopDAO = null;
        super.tearDown();
    }

    public void testInsert() {
        TradeCarShop tcs = new TradeCarShop();
        tcs.setOwner("admin");
        tcs.setShopId("testshop4");
        tradeCarShopDAO.insert(tcs);
        assertNotNull(tcs.getShopId());
    }

    public void testDelete() {
        TradeCarShop tcs = new TradeCarShop();
        tcs.setOwner("admin");
        tcs.setShopId("testshop1");
        tradeCarShopDAO.delete(tcs);
    }

    public void testFind() {
        List list = tradeCarShopDAO.getTradecarshopListByOwner("40289291149bb12201149bb1220d0000");       
        //TradeCarShop tradeCarShop = tradeCarShopDAO.getByOwnerAndShopId("admin", "testshop1");
        assertNotNull(list);
    }
}
