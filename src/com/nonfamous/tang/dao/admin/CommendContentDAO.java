//===================================================================
// Created on Jun 9, 2007
//===================================================================
package com.nonfamous.tang.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * 推荐内容
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendContentDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */

public interface CommendContentDAO {
	/**
	 * 获取页面的推荐的内容
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query)
			throws DataAccessException;

	/**
	 * 获取页面的推荐的内容
	 * 
	 * @param contetnId
	 * @return
	 */
	public CommendContent getCommendContentsByContetnId(String contentId)
			throws DataAccessException;

	/**
	 * 获取页面的推荐的内容
	 * 
	 * @param commendPositionId
	 * @return
	 */
	public CommendContent getCommendContentsByPositionId(
			String commendPositionId);

	/**
	 * 删除推荐信息
	 * 
	 * @param contentId
	 */
	public void deleteCommendContent(String contentId);

	/**
	 * 新增推荐信息
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc);

	/**
	 * 修改推荐信息
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc);

	// ====================================需要cache====================================
	/**
	 * 推荐内容
	 * 
	 * @param commendPag
	 * @return
	 * @throws DataAccessException
	 */
	// public Map<String, List> getCommendContents() throws DataAccessException;
	public Map<String, List> getCommendContentsByPage(String commendPage)
			throws DataAccessException;

	/**
	 * 推荐位置
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<CommendPosition> getCommendPositions()
			throws DataAccessException;

	/**
	 * 获取页面的推荐识别码
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<String> getCommendCodesByPage(String commendPage)
			throws DataAccessException;

	/**
	 * 根据查询条件获取记录数
	 * 
	 * @param query
	 * @return
	 */
	public Integer getCommendCount(CommendTextQuery query);

	/**
	 * 根据推荐ID得到推荐代码
	 * 
	 * @param commendId
	 * @return
	 */
	public String getCommendCode(int commendId);
}
