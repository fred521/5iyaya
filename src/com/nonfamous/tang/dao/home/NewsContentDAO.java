package com.nonfamous.tang.dao.home;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.NewsContent;

/**
 * @author: fred
 * 
 * <pre>
 * 咨询信息分类dao
 * </pre>
 * 
 * @version $Id: NewsContentDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface NewsContentDAO {


	/**
	 * 根据咨询分类id获取新闻内容信息
	 * 
	 * @param typeId
	 * @return
	 * @throws DataAccessException
	 */
	public NewsContent getNewsContentById(String newsId)
			throws DataAccessException;

	/**
	 * insert the news content object
	 * 
	 * @param newsContent
	 * @throws DataAccessException
	 */
	public void insertNewsContent(NewsContent newsContent)
			throws DataAccessException;

	/**
	 * delete the specific new content object by news Id
	 * 
	 * @param newsId
	 * @throws DataAccessException
	 */

	public int deleteNewsContentById(String newsId) throws DataAccessException;

}
