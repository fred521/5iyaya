//===================================================================
// Created on May 29, 2007
//===================================================================
package com.nonfamous.tang.service.admin;

import java.util.List;

import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * 推荐管理
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */

public interface CommendService {

	/**
	 * 新增推荐信息
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc);

	/**
	 * 新增推荐位置
	 * 
	 * @param cp
	 */
	public void insertCommendPosition(CommendPosition cp);

	/**
	 * 修改推荐信息
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc);

	/**
	 * 修改推荐位置
	 * 
	 * @param cp
	 */
	public void updateCommendPosition(CommendPosition cp);

	/**
	 * 删除推荐信息
	 * 
	 * @param cc
	 */
	public void deleteCommendContent(String contentId);

	/**
	 * 删除推荐位置
	 * 
	 * @param cp
	 */
	public void deleteCommendPosition(String commendId);

	/**
	 * 获取页面的推荐的位置
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query);

	/**
	 * 获取页面的推荐的位置(不分页)
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage);

	/**
	 * 获取推荐位置下的推荐内容
	 * 
	 * @param positionId
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query);

	/**
	 * 根据推荐识别码获取推荐位置
	 * 
	 * @param commendCode
	 * @return
	 */
	public List<CommendPosition> getCommendPositionByCommendCode(
			String commendCode);

	/**
	 * 根据推荐位置ID获取推荐位置
	 * 
	 * @param commendId
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId);

	/**
	 * 根据推荐内容ID获取推荐内容
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByContentId(String contentId);

	/**
	 * 根据推荐位置ID获取推荐内容
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByPosId(String posId);

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
