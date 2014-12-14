package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchMemberShopQuery;

/**
 * 
 * @author victor
 * 
 */
public interface MemberShopIndexService {
	/**
	 * 重新建立会员店铺索引，取数据的开始时间从定时记录数据表中读取
	 * */
	public void createMemberShopIndex();
	/**
	 * 重新建立会员店铺索引，取数据的开始时间为2000年
	 * */
	public void rebuildMemberShopIndex();
	/**
	 * 更新会员店铺索引
	 * */
	public void updateMemberShopIndex();
	/**
	 * 搜索会员店铺，返回会员店铺集合
	 * */
	public SearchMemberShopQuery findMemberShop(SearchMemberShopQuery query);
	
}
