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
 * �Ƽ�����
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */

public interface CommendService {

	/**
	 * �����Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void insertCommendContent(CommendContent cc);

	/**
	 * �����Ƽ�λ��
	 * 
	 * @param cp
	 */
	public void insertCommendPosition(CommendPosition cp);

	/**
	 * �޸��Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void updateCommendContent(CommendContent cc);

	/**
	 * �޸��Ƽ�λ��
	 * 
	 * @param cp
	 */
	public void updateCommendPosition(CommendPosition cp);

	/**
	 * ɾ���Ƽ���Ϣ
	 * 
	 * @param cc
	 */
	public void deleteCommendContent(String contentId);

	/**
	 * ɾ���Ƽ�λ��
	 * 
	 * @param cp
	 */
	public void deleteCommendPosition(String commendId);

	/**
	 * ��ȡҳ����Ƽ���λ��
	 * 
	 * @param query
	 * @return
	 */
	public List<CommendPosition> getCommendPositions(CommendPositionQuery query);

	/**
	 * ��ȡҳ����Ƽ���λ��(����ҳ)
	 * 
	 * @param commendPage
	 * @return
	 */
	public List<CommendPosition> getCommendPositionsByPage(String commendPage);

	/**
	 * ��ȡ�Ƽ�λ���µ��Ƽ�����
	 * 
	 * @param positionId
	 * @return
	 */
	public List<CommendContent> getCommendContents(CommendContentQuery query);

	/**
	 * �����Ƽ�ʶ�����ȡ�Ƽ�λ��
	 * 
	 * @param commendCode
	 * @return
	 */
	public List<CommendPosition> getCommendPositionByCommendCode(
			String commendCode);

	/**
	 * �����Ƽ�λ��ID��ȡ�Ƽ�λ��
	 * 
	 * @param commendId
	 * @return
	 */
	public CommendPosition getCommendPositionByCommendId(String commendId);

	/**
	 * �����Ƽ�����ID��ȡ�Ƽ�����
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByContentId(String contentId);

	/**
	 * �����Ƽ�λ��ID��ȡ�Ƽ�����
	 * 
	 * @param contentId
	 * @return
	 */
	public CommendContent getCommendContentByPosId(String posId);

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
