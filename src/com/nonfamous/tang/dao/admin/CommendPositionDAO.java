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
 * �Ƽ�λ��DAO
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendPositionDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */

public interface CommendPositionDAO {
	/**
	 * ��ȡҳ����Ƽ���λ��
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query)
			throws DataAccessException;

	/**
	 * ��ȡҳ����Ƽ���λ��
	 * 
	 * @param String
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage)
			throws DataAccessException;

	/**
	 * �����Ƽ�λ��
	 * 
	 * @param cp
	 * @throws DataAccessException
	 */
	public void insert(CommendPosition cp) throws DataAccessException;

	/**
	 * �޸��Ƽ�λ��
	 * 
	 * @param cp
	 */
	public void updateCommendPosition(CommendPosition cp);

	/**
	 * �����Ƽ�ʶ�����ȡ�Ƽ�λ��
	 * 
	 * @param commendCode
	 * @return
	 */
	public List<CommendPosition> getCommendPositionByCommendCode(String commendCode);

	/**
	 * �����Ƽ��Ƽ�λ��ID��ȡ�Ƽ�λ��
	 * 
	 * @param commendCode
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId);

	/**
	 * ɾ���Ƽ�λ��
	 * 
	 * @param commendId
	 */
	public void deleteCommendPosition(String commendId);
}
