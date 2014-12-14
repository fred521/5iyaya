//===================================================================
// Created on 2007-9-19
//===================================================================
package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.SignShopQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.trade.SignShop;

/**
 * <p>
 * �û�ǩԼ����
 * </p>
 * 
 * @author HGS-gonglei
 * @version $Id: SignShopDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */

public interface SignShopDAO {
	/**
	 * �ҵ�ǩԼ���̣���ҳ��
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public SignShopQuery getMySignShopPaging(SignShopQuery query)
			throws DataAccessException;

	/**
	 * ����ǩԼ����
	 * 
	 * @param tc
	 * @throws DataAccessException
	 */
	public void insert(SignShop ss) throws DataAccessException;

	/**
	 * ɾ��ǩԼ����
	 * 
	 * @param shopId
	 * @param memberId
	 * @throws DataAccessException
	 */
	public void delete(String shopId, String memberId)
			throws DataAccessException;

	/**
	 * ��ȡǩԼ����
	 * 
	 * @param shopId
	 * @param memberId
	 * @throws DataAccessException
	 */
	public SignShop getByShopAndMember(String shopId, String memberId)
			throws DataAccessException;

	/**
	 * �ҵ�ǩԼ���̣�����ҳ��
	 * 
	 * @param memberId
	 * @return
	 */
	public List<Shop> getMySignShopList(String memberId);

}
