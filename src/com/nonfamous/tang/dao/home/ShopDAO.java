/**
 * 
 */
package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;

/**
 * @author swordfish
 * 
 */
public interface ShopDAO {

	/**
	 * 新增Shop
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void insertShop(Shop shop) throws DataAccessException;

	/**
	 * 新增店铺公告
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void insertShopCommend(ShopCommend commend)
			throws DataAccessException;

	/**
	 * 修改店铺公告 需要更新店铺最后修改时间
	 * 
	 * @param commend
	 * @param shop
	 * @throws DataAccessException
	 */
	public void updateShopCommend(ShopCommend commend, Shop shop)
			throws DataAccessException;

	/**
	 * 通过会员id查询店铺(市场名称，评论)
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopfullSelectByMemberId(String memberId)
			throws DataAccessException;

	/**
	 * 通过会员id查询店铺
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByMemberId(String memberId)
			throws DataAccessException;

	/**
	 * 通过店铺id查询店铺
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByShopId(String shopId) throws DataAccessException;

	/**
	 * 通过店铺id查询店铺(全部信息)
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopfullSelectByShopId(String shopId, String gisAddress)
			throws DataAccessException;

	/**
	 * 修改店铺
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void updateShop(Shop shop) throws DataAccessException;

	/**
	 * add by victor
	 * 
	 * 根据查询条件得到相应列表,为建索引调用
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<Shop> getShopListForIndex(SearchMemberShopQuery query)
			throws DataAccessException;

	public ShopCommend getShopCommendByMemberId(String memberId);

	/**
	 * 根据卖家会员信息获取店铺信息
	 */
	public Map queryShopByMember(List memberList);

	/**
	 * 根据query信息获取店铺信息
	 * 
	 * @param query
	 * @return
	 */
	public Map quickQueryShop(MemberQuery query);

	/**
	 * 通过店铺三维地址查询店铺
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByGisAddress(String gisAddress);

	/**
	 * 根据所有的店铺id获取店铺信息
	 * 
	 * @param ids
	 * @return
	 */
	public List<Shop> getShopListByIds(String[] ids);

	/**
	 * 获取最新注册的商家信息
	 * 
	 * @return
	 */
	public List<Shop> getLatestShopList();
	
	/**
	 * 根据卖家的ID获取店铺信息
	 * 
	 * @param memberIds
	 * @return
	 */
	public Map<String,Shop> getgetShopMapByMemberIds(String[] memberIds);
	
	/**
	 * 得到有效商品数量
	 * 
	 * @return
	 */
	public Integer getEffectGoodsCount();
	
	/**
	 * 得到店铺数量
	 * 
	 * @return
	 */
	public Integer getShopCommendCount();
	
	/**
	 * 得到店主数量
	 * 
	 * @return
	 */
	public Integer getShopNameCount();
	
	/**
	 * 得到分类信息数量
	 * 
	 * @return
	 */
	public Integer getAllNewsCount();
	/**
	 * 获得数据库系统时间
	 * 
	 * @return
	 */
	public Date getSysDate()throws DataAccessException;

}
