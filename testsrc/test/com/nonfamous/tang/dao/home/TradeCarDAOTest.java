//===================================================================
// Created on 2007-9-17
//===================================================================
package test.com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.TradeCarDAO;
import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.trade.TradeCar;

/**
 * <p>
 *  TradeCarDAOTest
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarDAOTest.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */

public class TradeCarDAOTest extends DAOTestBase {
    private TradeCarDAO tradeCarDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tradeCarDAO = (TradeCarDAO) this.daoBeanFactory.getBean("tradeCarDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        tradeCarDAO = null;
        super.tearDown();
    }

    public void testInsert() {
        TradeCar tc = new TradeCar();
        tc.setGmtCreate(new Date());
        tc.setGoodsId("test1");
        tc.setOwner("admin");
        tc.setShopId("4028928b139bc87101139bcee1750001");
        tradeCarDAO.insert(tc);
        assertNotNull(tc.getId());
    }

    public void testDelete() {
        tradeCarDAO.delete(new Long(21));
    }

    public void testCount() {
        Integer count = this.tradeCarDAO.countByShopIdAndOwner("4028928b139bc87101139bcee1750001","40289291149bb12201149bb1220d0000");
        assertEquals(2, count.intValue());
    }

    public void testGetGoods() {
        List list = this.tradeCarDAO.getGoodsByShopIdAndOwner("4028928b139bc87101139bcee1750001","40289291149bb12201149bb1220d0000");
        assertEquals(list.size(), 3);
    }

    public void testGetGoodsByGoodsIdWithOwner() {
        TradeCar tc = this.tradeCarDAO.getTradeCarByGoodsIdWithOwner("297e932914b730fc0114b730fcb00000","4028928c150859d801150859d8aa0000");
        assertNotNull(tc);
    }

    public void testGetGoodsPaging() {
        TradeCarQuery tcq = new TradeCarQuery();
        //tcq.setPageSize(2);
        tcq.setOwner("admin");
        tcq = this.tradeCarDAO.getMyTradeCarPaging(tcq);
        assertEquals(tcq.getItems().size(), 5);
    }
}
