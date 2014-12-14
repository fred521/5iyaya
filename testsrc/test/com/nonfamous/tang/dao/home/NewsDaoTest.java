package test.com.nonfamous.tang.dao.home;

import java.util.Collection;
import java.util.Map;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

public class NewsDaoTest extends DAOTestBase {
    private NewsDAO newsDAO;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        newsDAO = (NewsDAO) this.daoBeanFactory.getBean("newsDAO");
    }

    @Override
    protected void tearDown() throws Exception {
        newsDAO = null;
        super.tearDown();
    }

    public void testGetAllNewsList() {
        NewsQuery q = new NewsQuery();
        Collection collection = newsDAO.getAllNewsList(q);
        assertNotNull(collection);
    }

    public void testQueryNewsList() {
        NewsQuery q = new NewsQuery();
        q.setNewsTitle("hello");
        q.setMemberId("007");
        q.setNewsStatus("N");
        q.setNewsType("10");
        Collection collection = newsDAO.queryNewsList(q);
        assertNotNull(collection);
    }

    public void testInsertNews() {
        NewsBaseInfo newsBaseInfo = new NewsBaseInfo();
        NewsContent newsContent = new NewsContent();
        newsBaseInfo.setNewsId("100");
        newsBaseInfo.setNewsTitle("hello");
        newsBaseInfo.setMemberId("007");
        newsBaseInfo.setNewsStatus("P");
        newsBaseInfo.setNewsType("10");
        newsBaseInfo.setNick("fred");
        newsDAO.insertNews(newsBaseInfo);
        newsContent.setContent("hello world");
        newsContent.setNewsId(newsBaseInfo.getNewsId());
        newsDAO.insertNewsContent(newsContent);
    }

    public void testUpdateNews() {
        NewsBaseInfo newsBaseInfo = new NewsBaseInfo();
        NewsContent newsContent = new NewsContent();
        newsBaseInfo.setNewsId("100");
        newsBaseInfo.setNewsTitle("hello hello");
        newsBaseInfo.setMemberId("007007");
        newsBaseInfo.setNewsStatus("P");
        newsBaseInfo.setNewsType("20");
        newsBaseInfo.setNick("superfred");
        newsContent.setContent("bababababab");
        newsContent.setNewsId("100");
        newsDAO.updateNews(newsBaseInfo);
        newsDAO.updateNewsContent(newsContent);
    }

    public void testGetTotalNews()

    {
        String memberId = "007007";
        newsDAO.getNewsTotalByMemberId(memberId);
    }

    public void testGetNewsInfoByNewsType() {
        NewsTypeAndInfoDTO dto = newsDAO.getNewsInfoByNewsType("10");
        assertNotNull(dto);
    }

    public void testGetNewsInfo() {
        Map dtoM = newsDAO.getAllNewsInfo();
        assertNotNull(dtoM);
    }
}