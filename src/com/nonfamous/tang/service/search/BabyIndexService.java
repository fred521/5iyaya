package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchGoodsQuery;

public interface BabyIndexService {
	/**
	 * 重新建立商品索引，取数据的开始时间从定时记录数据表中读取
	 * */
	public void createBabyIndex();
	/**
	 * 完全重新建立商品索引，取数据的开始时间为2000年
	 * */
	public void rebuildBabyIndex();
	/**
	 * 更新商品索引
	 * */
	public void updateBabyIndex();
	/**
	 * 搜索商品，返回商品集合
	 * */
	public SearchGoodsQuery findBaby(SearchGoodsQuery query);
	/**
	 * 取索引有效商品的个数
	 * */
	public SearchGoodsQuery findEffectBaby(SearchGoodsQuery query);
	
}