package com.nonfamous.tang.dao.home.ibatis;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.NewsContentDAO;
import com.nonfamous.tang.domain.NewsContent;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisNewsContentDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisNewsContentDAO extends SqlMapClientDaoSupport implements
		NewsContentDAO {
	public static final String SPACE = "NEWSCONTENT_SPACE.";


	public NewsContent getNewsContentById(String newsId) throws DataAccessException {
		return (NewsContent) getSqlMapClientTemplate().queryForObject(
				SPACE + "GET_NEWS_CONTENT_BY_ID", newsId);
	}
	
	public int deleteNewsContentById(String newsId) throws DataAccessException
	{
		if (newsId == null) {
			throw new NullPointerException("newsId can't be null");
		}
		return this.getSqlMapClientTemplate().delete(
				SPACE + "DELETE_NEWS_CONTENT_BY_ID", newsId);
	}
	public int updateNewsContentById(String newsId) throws DataAccessException
	{
		if (newsId == null) {
			throw new NullPointerException("newsId can't be null");
		}
		return this.getSqlMapClientTemplate().delete(
				SPACE + "UPDATE_NEWS_CONTENT_BY_ID", newsId);
		
	}
	public void insertNewsContent(NewsContent newsContent) throws DataAccessException
	{
		if(newsContent==null)
		{
			throw new NullPointerException("news content can't be null");
		}
		this.getSqlMapClientTemplate().insert(SPACE + "NEWS_CONTENT_INSERT", newsContent);
	}

}
