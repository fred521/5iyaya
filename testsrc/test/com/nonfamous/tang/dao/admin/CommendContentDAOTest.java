//===================================================================
// Created on Jun 11, 2007
//===================================================================
package test.com.nonfamous.tang.dao.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.domain.CommendContent;

/**
 * <p>
 * CommendContentDAOTest
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendContentDAOTest.java,v 1.1 2008/07/11 00:47:13 fred Exp $
 */

public class CommendContentDAOTest extends DAOTestBase {
    private CommendContentDAO commendContentDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        commendContentDAO = (CommendContentDAO) this.daoBeanFactory.getBean("commendContentDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        commendContentDAO = null;
        super.tearDown();
    }

    public void testInsert() {
        CommendContent cc = new CommendContent();
        cc.setBatchNum(Long.valueOf(1));
        cc.setCommendPositionId(Long.valueOf(63));
        cc.setCommendType(Long.valueOf(1));
        cc.setCommendText("testhaha");
        cc.setGmtCreate(new Date());
        cc.setGmtEnd(new Date());
        cc.setGmtStart(new Date());
        cc.setGmtModify(new Date());
        cc.setCreator("1");
        cc.setModifier("1");
        cc.setCommendStatus("N");
        this.commendContentDAO.insertCommendContent(cc);
        assertNotNull(cc.getContentId());
    }

    public void testSelect() {
        CommendContent cc = this.commendContentDAO.getCommendContentsByContetnId("61");
        assertNotNull(cc.getContentId());
    }
    
    public void testGetCodes(){
        List<String> list = this.commendContentDAO.getCommendCodesByPage("0");
        assertNotNull(list);
    }
    
    public void testGetCommendContentsByPage(){
        Map map = this.commendContentDAO.getCommendContentsByPage("0");
        assertNotNull(map);
    }
}
