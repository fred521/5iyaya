/**
 * 
 */
package com.nonfamous.tang.dao.home.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;

/**
 * @author swordfish
 * 
 */
public class IbatisShopDAO extends SqlMapClientDaoSupport implements ShopDAO {

	public static final String SPACE = "SHOP_SPACE.";

	public void insertShop(Shop shop) throws DataAccessException {
		if (shop == null) {
			throw new NullPointerException("shop can not be null");
		}
		this.getSqlMapClientTemplate().insert(SPACE + "shop_insert", shop);
	}

	public void insertShopCommend(ShopCommend commend)
			throws DataAccessException {
		if (commend == null) {
			throw new NullPointerException("commend can not be null");
		}

		this.getSqlMapClientTemplate().insert(SPACE + "shopcommend_insert",
				commend);
	}

	public void updateShopCommend(ShopCommend commend, Shop shop)
			throws DataAccessException {
		if (commend == null || StringUtils.isBlank(commend.getShopId())
				|| shop == null || StringUtils.isBlank(shop.getShopId())) {
			throw new NullPointerException("can't not update");
		}
		this.getSqlMapClientTemplate().update(SPACE + "shopcommend_update",
				commend);
		this.getSqlMapClientTemplate().update(SPACE + "shop_update", shop);
	}

	public Shop shopfullSelectByMemberId(String memberId)
			throws DataAccessException {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return (Shop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shopfull_by_member_id", memberId);
	}

	public Shop shopSelectByShopId(String shopId) throws DataAccessException {
		if (shopId == null) {
			throw new NullPointerException("shopId can't be null");
		}
		return (Shop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shop_by_shop_id", shopId);
	}

	public void updateShop(Shop shop) throws DataAccessException {
		if (shop == null || StringUtils.isBlank(shop.getShopId())) {
			throw new NullPointerException("can't not update");
		}
		this.getSqlMapClientTemplate().update(SPACE + "shop_update", shop);
	}

	@SuppressWarnings("unchecked")
	public List<Shop> getShopListForIndex(SearchMemberShopQuery query)
			throws DataAccessException {
		Integer totalItem = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "QUERY_SHOP_COUNT_FOR_INDEX", query);
		query.setTotalItem(totalItem);
		if (totalItem.intValue() == 0) {
			return java.util.Collections.EMPTY_LIST;
		}
		return this.getSqlMapClientTemplate().queryForList(
				SPACE + "QUERY_SHOP_LIST_FOR_INDEX", query);
	}

	public ShopCommend getShopCommendByMemberId(String memberId) {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return (ShopCommend) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shopcommend_by_member_id", memberId);
	}

	public Shop shopSelectByMemberId(String memberId)
			throws DataAccessException {
		if (memberId == null) {
			throw new NullPointerException("memberId can't be null");
		}
		return (Shop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shop_by_member_id", memberId);
	}

	public Shop shopfullSelectByShopId(String shopId, String gisAddress)
			throws DataAccessException {
		if (StringUtils.isBlank(gisAddress) && StringUtils.isBlank(shopId)) {
			throw new NullPointerException(
					"shopId or shopAddress can't be null");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("shopId", shopId);
		map.put("gisAddress", gisAddress);
		return (Shop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shopfull_by_shop_id", map);
	}

	public Map queryShopByMember(List memberList) {
		if (memberList == null) {
			throw new NullPointerException("memberList can't be null");
		}
		Map<String, List> param = new HashMap<String, List>();
		param.put("memberList", memberList);
		return this.getSqlMapClientTemplate().queryForMap(
				SPACE + "GET_QUERY_SHOP_BY_MEMBER_INFO", param, "memberId");
	}

	public Map quickQueryShop(MemberQuery query) {
		if (query == null) {
			throw new NullPointerException("query can't be null");
		}
		Map shopMap = null;
		Integer total = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(SPACE + "GET_QUERY_SHOP_COUNT_BY_QUERY_INFO",
						query);
		query.setTotalItem(total);
		if (total != null && total.intValue() > 0) {
			shopMap = this.getSqlMapClientTemplate().queryForMap(
					SPACE + "GET_QUERY_SHOP_BY_QUERY_INFO", query, "memberId");
		}
		return shopMap;
	}

	public Shop shopSelectByGisAddress(String gisAddress) {
		if (StringUtils.isBlank(gisAddress)) {
			throw new NullPointerException("gisAddress can't be null");
		}
		return (Shop) this.getSqlMapClientTemplate().queryForObject(
				SPACE + "get_shop_by_gis_address", gisAddress);
	}

	@SuppressWarnings("unchecked")
	public List<Shop> getShopListByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		Map map = new HashMap();
		map.put("ids", ids);
		return this.getSqlMapClientTemplate().queryForList(
				SPACE + "QUERY_SHOP_LIST_BY_IDS", map);
	}

	public List<Shop> getLatestShopList(){
		return this.getSqlMapClientTemplate().queryForList(SPACE + "getLatestShopList");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Shop> getgetShopMapByMemberIds(String[] memberIds) {
		if (memberIds == null || memberIds.length == 0) {
			return null;
		}
		Map<String, String[]> param = new HashMap<String, String[]>();
		param.put("ids", memberIds);
		return this.getSqlMapClientTemplate().queryForMap(
				SPACE + "QUERY_SHOP_LIST_BY_MEMBER_IDS", param, "memberId");
	}
	
	public Integer getEffectGoodsCount()throws DataAccessException
	{
		return (Integer)this.getSqlMapClientTemplate().queryForObject(SPACE+"get_effectgoods_count");
	}
	
	public Integer getShopCommendCount()
	{
		return (Integer)this.getSqlMapClientTemplate().queryForObject(SPACE+"get_shopcommend_count");
	}
	
	public Integer getShopNameCount()
	{
		return (Integer)this.getSqlMapClientTemplate().queryForObject(SPACE+"get_shopname_count");
	}
	
	/**
	 * 得到分类信息数量
	 * 
	 * @return
	 */
	public Integer getAllNewsCount()
	{
		return (Integer)this.getSqlMapClientTemplate().queryForObject(SPACE+"get_allnews_count");
	}
	
	/**
	 * 获得数据库系统时间
	 * 
	 * @return
	 */
	public Date getSysDate()throws DataAccessException{
		return (Date) this.getSqlMapClientTemplate().queryForObject(SPACE+"getSysDate");
	}

}
