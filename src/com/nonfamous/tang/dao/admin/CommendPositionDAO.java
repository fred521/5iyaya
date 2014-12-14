//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.dao.admin;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * 推荐位置DAO
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendPositionDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */

public interface CommendPositionDAO {
	/**
	 * 获取页面的推荐的位置
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query)
			throws DataAccessException;

	/**
	 * 获取页面的推荐的位置
	 * 
	 * @param String
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage)
			throws DataAccessException;

	/**
	 * 新增推荐位置
	 * 
	 * @param cp
	 * @throws DataAccessException
	 */
	public void insert(CommendPosition cp) throws DataAccessException;

	/**
	 * 修改推荐位置
	 * 
	 * @param cp
	 */
	public void updateCommendPosition(CommendPosition cp);

	/**
	 * 根据推荐识别码获取推荐位置
	 * 
	 * @param commendCode
	 * @return
	 */
	public List<CommendPosition> getCommendPositionByCommendCode(String commendCode);

	/**
	 * 根据推荐推荐位置ID获取推荐位置
	 * 
	 * @param commendCode
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId);

	/**
	 * 删除推荐位置
	 * 
	 * @param commendId
	 */
	public void deleteCommendPosition(String commendId);
}
