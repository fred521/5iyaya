package test.com.nonfamous.tang.dao.home;

import java.util.Collection;

import com.nonfamous.tang.dao.home.NewsTypeDAO;

import test.com.nonfamous.tang.dao.DAOTestBase;

public class NewsTypeDaoTest extends DAOTestBase {
    private NewsTypeDAO newsTypeDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        newsTypeDAO = (NewsTypeDAO) this.daoBeanFactory.getBean("newsTypeDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        newsTypeDAO = null;
        super.tearDown();
    }

    public void testGetAllNewsTypeList() {
        Collection collection = newsTypeDAO.getAllNewsType();
        assertNotNull(collection);
    }

    public void testGetNewsTypeById() {

        assertNotNull(newsTypeDAO.getNewsTypeById("10"));
    }
}
