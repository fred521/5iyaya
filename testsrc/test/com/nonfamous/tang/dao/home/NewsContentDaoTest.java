package test.com.nonfamous.tang.dao.home;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.domain.NewsContent;

public class NewsContentDaoTest extends DAOTestBase {
	private NewsContentDAO newsContentDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		newsContentDAO = (NewsContentDAO) this.daoBeanFactory
				.getBean("newsContentDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		newsContentDAO = null;
		super.tearDown();
	}

	public void testInsertNewsContent() {
		NewsContent newsContent = new NewsContent();
		newsContent.setContent("hello");
		newsContent.setNewsId("10");
		newsContentDAO.insertNewsContent(newsContent);
	}
	public void testGetNewsContentById() {

		assertNotNull(newsContentDAO.getNewsContentById("10"));
	}
	public void testDeleteContentById()
	{
		newsContentDAO.deleteNewsContentById("10");
	}
}
