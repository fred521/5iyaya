/**
 * 
 */
package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;
import com.nonfamous.tang.domain.result.ShopResult;

/**
 * @author swordfish
 * 
 */
public interface ShopService {
	/**
	 * 新增店铺(同时新增店铺公告)
	 * 
	 * @param shop,needSelectByMemeberId是否要查询该用户的店铺，后台新增的时候，已经查询过，避免二次查询
	 * @return
	 */
	public ShopResult insertShop(Shop shop, boolean needSelectByMemeberId);

	/**
	 * 通过会员id查询店铺(全部shop信息)
	 * 
	 * @param memberId
	 * @return
	 */
	public Shop shopfullSelectByMemberId(String memberId);

	/**
	 * 通过会员id查询店铺
	 * 
	 * @param memberId
	 * @return
	 */
	public Shop shopSelectByMemberId(String memberId);

	/**
	 * 通过店铺id查询店铺
	 * 
	 * @param shopId
	 * @return
	 */
	public Shop shopSelectByShopId(String shopId);

	/**
	 * 通过店铺id查询店铺(全部的信息)
	 * 
	 * @param shopId
	 * @return
	 */
	public Shop shopfullSelectByShopId(String shopId, String shopAddress);

	/**
	 * memberId获得店铺公告
	 * 
	 * @param commend
	 * @return
	 */
	public ShopCommend getShopCommendByMemberId(String memberId);

	/**
	 * 修改店铺公告,需要更新店铺的最后修改时间
	 * 
	 * @param commend
	 * @param shop
	 * @return
	 */
	public ShopCommend updateShopCommend(ShopCommend commend, Shop shop);

	/**
	 * 修改店铺
	 * 
	 * @param shop
	 * @return
	 */
	public Shop updateShop(Shop shop);

	/**
	 * 添加签约店铺
	 * 
	 * @return
	 */
	public ShopResult addSignShop(String shopId, String memberId);

	/**
	 * 获取用户所有签约店铺
	 * 
	 * @param memberId
	 * @return
	 */
	public List<Shop> getMySignShopList(String memberId);
	
	/**
 	 * 得到有效商品数量
 	 * @return
 	 */   
     public Integer getEffectGoodsCount();
     

     /**
   	 * 得到店铺数量
   	 * @return
   	 */
      public Integer getShopCommendCount();
      
      /**
   	 * 得到店主数量
   	 * @return
   	 */
     public Integer getShopNameCount();
     
     /**
      * 得到分类信息数量
      * @return
      */
      public Integer getAllNewsCount();
      
      /**
       * 获取最新商家信息
       */
      public List<Shop> getLatestShopList();
}
