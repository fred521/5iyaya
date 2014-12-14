//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.service.admin.pojo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.dao.admin.CommendPositionDAO;
import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;
import com.nonfamous.tang.service.admin.CommendService;

/**
 * <p>
 * 推荐管理实现类
 * </p>
 * 
 * @author jacky
 * @version $Id: POJOCommendService.java,v 1.1 2008/07/11 00:47:16 fred Exp $
 */

public class POJOCommendService extends POJOServiceBase implements
		CommendService {

	private static final Log logger = LogFactory
			.getLog(POJOCommendService.class);

	private CommendPositionDAO commendPositionDAO;

	private CommendContentDAO commendContentDAO;

	/**
	 * 删除推荐内容
	 */
	public void deleteCommendContent(String contentId) {
		if (logger.isInfoEnabled()) {
			logger.info("delete commend content id :" + contentId);
		}
		this.commendContentDAO.deleteCommendContent(contentId);
	}

	/**
	 * 删除推荐位置
	 */
	public void deleteCommendPosition(String commendId) {
		if (logger.isInfoEnabled()) {
			logger.info("delete commend position id :" + commendId);
		}
		this.commendPositionDAO.deleteCommendPosition(commendId);
	}

	/**
	 * 获取页面的推荐的内容
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query) {
		return this.commendContentDAO.getCommendContents(query);
	}

	/**
	 * 获取页面的推荐的位置
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query) {
		return this.commendPositionDAO.getCommendPositions(query);
	}

	/**
	 * 获取页面的推荐的位置
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage) {
		return this.commendPositionDAO.getCommendPositionsByPage(commendPage);
	}

	/**
	 * 新增推荐信息
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc) {
		this.commendContentDAO.insertCommendContent(cc);
	}

	/**
	 * 新增推荐位置
	 */
	public void insertCommendPosition(CommendPosition cp) {
		this.commendPositionDAO.insert(cp);
	}

	/**
	 * 修改推荐信息
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc) {
		this.commendContentDAO.updateCommendContent(cc);
	}

	/**
	 * 修改推荐位置
	 * 
	 * @param cc
	 */
	public void updateCommendPosition(CommendPosition cp) {
		this.commendPositionDAO.updateCommendPosition(cp);
	}

	/**
	 * 根据推荐识别码获取推荐位置
	 * 
	 * @param commendCode
	 * @return
	 */
	public List<CommendPosition> getCommendPositionByCommendCode(
			String commendCode) {
		return this.commendPositionDAO
				.getCommendPositionByCommendCode(commendCode);
	}

	/**
	 * 根据推荐推荐位置ID获取推荐位置
	 * 
	 * @param commendId
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId) {
		return this.commendPositionDAO.getCommendPositionByCommendId(commendId);
	}

	/**
	 * 根据推荐内容ID获取推荐内容
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByContentId(String contentId) {
		return this.commendContentDAO.getCommendContentsByContetnId(contentId);
	}

	/**
	 * 根据查询条件获取记录数
	 * 
	 * @param query
	 * @return
	 */

	public Integer getCommendCount(CommendTextQuery query) {
		return this.commendContentDAO.getCommendCount(query);
	}

	/**
	 * 根据推荐ID得到推荐代码
	 * 
	 * @param commendId
	 * @return
	 */
	public String getCommendCode(int commendId) {
		return this.commendContentDAO.getCommendCode(commendId);
	}

	public CommendContent getCommendContentByPosId(String posId) {
		return this.commendContentDAO.getCommendContentsByPositionId(posId);
	}

	/**
	 * @param commendPositionDAO
	 *            The commendPositionDAO to set.
	 */
	public void setCommendPositionDAO(CommendPositionDAO commendPositionDAO) {
		this.commendPositionDAO = commendPositionDAO;
	}

	/**
	 * @param commendContentDAO
	 *            The commendContentDAO to set.
	 */
	public void setCommendContentDAO(CommendContentDAO commendContentDAO) {
		this.commendContentDAO = commendContentDAO;
	}

}
