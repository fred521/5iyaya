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
	 * 获取所有商品类目
	 * 
	 * @return
	 */
	public List<GoodsCat> getAllGoodsCat();

	/**
	 * 根据商品类目id获取商品类目
	 * 
	 * @param catId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsCat getGoodsCatById(String catId);

	/**
	 * 获取一级类目
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsCat> getRootGoodsCat();
	
	/**
	 * 根据名称获取商品类目
	 * @param catName
	 * @return
	 */
	public GoodsCat getGoosCatByName(String catName);
}
