package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.GoodsCat;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: GoodsCatDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface GoodsCatDAO {

	/**
	 * ��ȡ������Ʒ��Ŀ
	 * 
	 * @return
	 */
	public List<GoodsCat> getAllGoodsCat();

	/**
	 * ������Ʒ��Ŀid��ȡ��Ʒ��Ŀ
	 * 
	 * @param catId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsCat getGoodsCatById(String catId);

	/**
	 * ��ȡһ����Ŀ
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsCat> getRootGoodsCat();

	/**
	 * �������ƻ�ȡ��Ʒ��Ŀ
	 * 
	 * @param catName
	 * @return
	 */
	public GoodsCat getGoosCatByName(String catName);
}
