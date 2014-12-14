package com.nonfamous.tang.service.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.GoodsCat;

/**
 * @author: fish
 * @version $Id: GoodsCatalogService.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public interface GoodsCatalogService {
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
	 * @param catName
	 * @return
	 */
	public GoodsCat getGoosCatByName(String catName);
}
