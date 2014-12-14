package test.com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.domain.result.NewsBaseInfoResult;
import com.nonfamous.tang.service.home.NewsService;

import test.com.nonfamous.tang.service.ServiceTestBase;

/**
 * <p>
 * 会员服务测试类
 * </p>
 * 
 * @author:fred
 */
public class NewsServiceTest extends ServiceTestBase {
	private NewsService newsService;

	private NewsDAO newsDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		newsService = (NewsService) this.serviceBeanFactory
				.getBean("newsService");
		newsDAO = (NewsDAO) this.serviceBeanFactory.getBean("newsDAO");
	}

	public void testAddNews() {

		NewsContent newsContent = new NewsContent();
		newsContent.setContent("hello my child");
		newsContent.setNewsId("111111");
		NewsBaseInfo newsBaseInfo = new NewsBaseInfo();
		newsBaseInfo.setAbandonDays(new Long(90));
		newsBaseInfo.setCreator("fred");
		newsBaseInfo.setMemberId("111");
		newsBaseInfo.setModifier("fred");
		newsBaseInfo.setNewsId("111111");
		newsBaseInfo.setNewsStatus("N");
		newsBaseInfo.setNewsTitle("hello world");
		newsBaseInfo.setNewsType("10");
		newsBaseInfo.setPhone("13868022823");
		newsBaseInfo.setContent(newsContent);

		NewsBaseInfoResult result = newsService.addNews(newsBaseInfo);
		assertTrue(result.isSuccess());
		assertNotNull(result.getNewsId());
		String newsId = result.getNewsId();
		assertFalse(result.isSuccess());
		result = newsService.addNews(newsBaseInfo);
		assertEquals(result.getErrorCode(), NewsBaseInfoResult.ERROR_NEWS_EXIST);
		// 清理数据，不然一堆垃圾数据了
		newsDAO.deleteNewsById(newsId);
	}

	public void testGetNewsById() {
		NewsBaseInfo newsBaseInfo = (NewsBaseInfo) newsService
				.getNewsById("29788689fd38435383777d767b42d5f9");
		assertNotNull(newsBaseInfo);
	}

	public void testUpdateNewsStatus() {

	}

	public void testQuery() {

	}
}
