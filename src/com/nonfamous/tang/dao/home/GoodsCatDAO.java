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
	 * 
	 * @param catName
	 * @return
	 */
	public GoodsCat getGoosCatByName(String catName);
}
