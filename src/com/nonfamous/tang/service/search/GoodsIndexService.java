package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchGoodsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface GoodsIndexService {
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
	/**
	 * 搜索商品，返回商品集合
	 * */
	public SearchGoodsQuery findGoods(SearchGoodsQuery query);
	/**
	 * 取索引有效商品的个数
	 * */
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query);
	
}
