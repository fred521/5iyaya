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
 * �Ƽ�����ʵ����
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
	 * ɾ���Ƽ�����
	 */
	public void deleteCommendContent(String contentId) {
		if (logger.isInfoEnabled()) {
			logger.info("delete commend content id :" + contentId);
		}
		this.commendContentDAO.deleteCommendContent(contentId);
	}

	/**
	 * ɾ���Ƽ�λ��
	 */
	public void deleteCommendPosition(String commendId) {
		if (logger.isInfoEnabled()) {
			logger.info("delete commend position id :" + commendId);
		}
		this.commendPositionDAO.deleteCommendPosition(commendId);
	}

	/**
	 * ��ȡҳ����Ƽ�������
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query) {
		return this.commendContentDAO.getCommendContents(query);
	}

	/**
	 * ��ȡҳ����Ƽ���λ��
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query) {
		return this.commendPositionDAO.getCommendPositions(query);
	}

	/**
	 * ��ȡҳ����Ƽ���λ��
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage) {
		return this.commendPositionDAO.getCommendPositionsByPage(commendPage);
	}

	/**
	 * �����Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc) {
		this.commendContentDAO.insertCommendContent(cc);
	}

	/**
	 * �����Ƽ�λ��
	 */
	public void insertCommendPosition(CommendPosition cp) {
		this.commendPositionDAO.insert(cp);
	}

	/**
	 * �޸��Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc) {
		this.commendContentDAO.updateCommendContent(cc);
	}

	/**
	 * �޸��Ƽ�λ��
	 * 
	 * @param cc
	 */
	public void updateCommendPosition(CommendPosition cp) {
		this.commendPositionDAO.updateCommendPosition(cp);
	}

	/**
	 * �����Ƽ�ʶ�����ȡ�Ƽ�λ��
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
	 * �����Ƽ��Ƽ�λ��ID��ȡ�Ƽ�λ��
	 * 
	 * @param commendId
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId) {
		return this.commendPositionDAO.getCommendPositionByCommendId(commendId);
	}

	/**
	 * �����Ƽ�����ID��ȡ�Ƽ�����
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByContentId(String contentId) {
		return this.commendContentDAO.getCommendContentsByContetnId(contentId);
	}

	/**
	 * ���ݲ�ѯ������ȡ��¼��
	 * 
	 * @param query
	 * @return
	 */

	public Integer getCommendCount(CommendTextQuery query) {
		return this.commendContentDAO.getCommendCount(query);
	}

	/**
	 * �����Ƽ�ID�õ��Ƽ�����
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
