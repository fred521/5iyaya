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
 * �Ƽ�����
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendContentDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */

public interface CommendContentDAO {
	/**
	 * ��ȡҳ����Ƽ�������
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query)
			throws DataAccessException;

	/**
	 * ��ȡҳ����Ƽ�������
	 * 
	 * @param contetnId
	 * @return
	 */
	public CommendContent getCommendContentsByContetnId(String contentId)
			throws DataAccessException;

	/**
	 * ��ȡҳ����Ƽ�������
	 * 
	 * @param commendPositionId
	 * @return
	 */
	public CommendContent getCommendContentsByPositionId(
			String commendPositionId);

	/**
	 * ɾ���Ƽ���Ϣ
	 * 
	 * @param contentId
	 */
	public void deleteCommendContent(String contentId);

	/**
	 * �����Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc);

	/**
	 * �޸��Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc);

	// ====================================��Ҫcache====================================
	/**
	 * �Ƽ�����
	 * 
	 * @param commendPag
	 * @return
	 * @throws DataAccessException
	 */
	// public Map<String, List> getCommendContents() throws DataAccessException;
	public Map<String, List> getCommendContentsByPage(String commendPage)
			throws DataAccessException;

	/**
	 * �Ƽ�λ��
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<CommendPosition> getCommendPositions()
			throws DataAccessException;

	/**
	 * ��ȡҳ����Ƽ�ʶ����
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<String> getCommendCodesByPage(String commendPage)
			throws DataAccessException;

	/**
	 * ���ݲ�ѯ������ȡ��¼��
	 * 
	 * @param query
	 * @return
	 */
	public Integer getCommendCount(CommendTextQuery query);

	/**
	 * �����Ƽ�ID�õ��Ƽ�����
	 * 
	 * @param commendId
	 * @return
	 */
	public String getCommendCode(int commendId);
}
