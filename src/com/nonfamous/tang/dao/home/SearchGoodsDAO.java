package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchGoodsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchGoodsDAO {
	/**
	 * 搜索商品，返回商品集合
	 * */
	public SearchGoodsQuery findAllGoods(SearchGoodsQuery query);
	/**
	 * 重新建立商品索引，取数据的开始时间从定时记录数据表中读取
	 * */
	public void createGoodsIndex();
	/**
	 * 完全重新建立商品索引，取数据的开始时间为2000年
	 * */
	public void rebuildGoodsIndex();
	/**
	 * 更新商品索引
	 * */
	public void updateGoodsIndex();
	
	public void setIndexFilePath(String id);
	/**
	 * 搜索商品，返回有效商品集合
	 * */
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query);
}
