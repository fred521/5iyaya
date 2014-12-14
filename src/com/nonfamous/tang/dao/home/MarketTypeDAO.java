package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;

/**
 * @author: alan
 * 
 * <pre>
 * 市场分类dao
 * </pre>
 * 
 * @version $Id: MarketTypeDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MarketTypeDAO {

	/**
	 * 获取所有市场分类
	 * 
	 * @return
	 */
	public List<MarketType> getAllMarketType();

	/**
	 * 根据市场分类id获取市场分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	public MarketType getMarketTypeById(String typeId);
	
//	/**
//	 * 根据市场分类id获取市场分类信息
//	 * 
//	 * @param typeId
//	 * @return
//	 */
//	public List<Shop> getShopByMarketId(String MarketId);
	
	/**
	 * 根据市场分类获取市场分类信息,所有市场信息
	 * 
	 * @param typeId
	 * @return
	 */
	public List<Shop> getAllShopByMarketId();

}
