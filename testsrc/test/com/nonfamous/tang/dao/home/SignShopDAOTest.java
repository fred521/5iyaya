//===================================================================
// Created on 2007-9-17
//===================================================================
package test.com.nonfamous.tang.dao.home;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.SignShopDAO;
import com.nonfamous.tang.dao.query.SignShopQuery;
import com.nonfamous.tang.domain.trade.SignShop;

/**
 * <p>
 *  TradeCarDAOTest
 * </p>
 * @author HGS-gonglei
 * @version $Id: SignShopDAOTest.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */

public class SignShopDAOTest extends DAOTestBase {
    private SignShopDAO signShopDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signShopDAO = (SignShopDAO) this.daoBeanFactory.getBean("signShopDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        signShopDAO = null;
        super.tearDown();
    }

    public void testInsert() {
        SignShop ss = new SignShop();
        ss.setMemberId("admin");
        ss.setShopId("testshop2");
        signShopDAO.insert(ss);
        assertNotNull(ss.getId());
    }

    public void testDelete() {
        signShopDAO.delete("40289290149babe201149bac674c0001", "admin");
    }

    public void testGetShop() {
        SignShop ss = signShopDAO.getByShopAndMember("testshop2", "admin");
        assertNotNull(ss);
    }

    public void testPaging() {
        SignShopQuery ssq = new SignShopQuery();
        //tcq.setPageSize(2);
        ssq.setMemberId("admin");
        ssq = this.signShopDAO.getMySignShopPaging(ssq);
        assertEquals(ssq.getItems().size(), 2);
    }
}
