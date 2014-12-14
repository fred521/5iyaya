package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchMemberShopQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchMemberShopDAO {
	/**
	 * 搜索资讯，返回资讯集合
	 * */
	public SearchMemberShopQuery findAllMemberShop(SearchMemberShopQuery query);
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
}
