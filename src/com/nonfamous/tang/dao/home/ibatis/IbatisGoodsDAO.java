package com.nonfamous.tang.dao.home.ibatis;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.dao.query.IndexGoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsContent;
import com.nonfamous.tang.domain.GoodsProperty;
import com.nonfamous.tang.domain.GoodsPropertyType;
import com.nonfamous.tang.domain.PictureInfo;

/**
 * 
 * @author alien
 * 
 */
public class IbatisGoodsDAO extends SqlMapClientDaoSupport implements GoodsDAO {


	private String SHOP_SPACE = "SHOP_SPACE.";
	
	
	private static final String GOODS_PROPERTY_SPACE = "GOODSPROPERTY_SPACE.";
	
	private String GOODS_BASE_SPACE = "GOODSBASEINFO_SPACE.";

	private String CONTENT_SPACE = "GOODSCONTENT_SPACE.";
	
	private String PICTURE_SPACE = "GOODSPICTURE_SPACE.";

	public String addGoods(GoodsBaseInfo gbi) throws DataAccessException {

		if (gbi == null) {
			throw new NullPointerException("商品信息不能为空");
		}

		String goodsId = UUIDGenerator.generate();
		if (StringUtils.isBlank(goodsId)) {
			throw new NullPointerException("goodsId is null");
		}
		gbi.setGoodsId(goodsId);
		this.getSqlMapClientTemplate().insert(
				GOODS_BASE_SPACE + "goodsbaseinfo_insert", gbi);
		GoodsContent gc = new GoodsContent();
		gc.setGoodsId(goodsId);
		gc.setContent(gbi.getGoodsContent().getContent());
		this.getSqlMapClientTemplate().insert(
				CONTENT_SPACE + "goodscontent_insert", gc);
		
		addPictures(gbi);
		
		this.getSqlMapClientTemplate().update(
				SHOP_SPACE + "shop_goods_count_add", gbi.getShopId());
		return goodsId;
	}
	
	private void addPictures(GoodsBaseInfo gbi) throws DataAccessException {
	    String goodsId = gbi.getGoodsId();
	    for(PictureInfo pi: gbi.getPictures()) {
		pi.setGoodsId(goodsId);
		String pictureId = UUIDGenerator.generate();
		pi.setId(pictureId);
		this.getSqlMapClientTemplate().insert(
			PICTURE_SPACE + "pictureinfo_insert", pi);
	    }
	}

	public int upGoods(GoodsBaseInfo gbi) throws DataAccessException {
		if (gbi == null) {
			throw new NullPointerException("商品信息不能为空");
		}

		return this.getSqlMapClientTemplate().update(
				GOODS_BASE_SPACE + "goodsbaseinfo_add_shelve", gbi);
	}

	public int downGoods(GoodsBaseInfo gbi) throws DataAccessException {
		if (gbi == null) {
			throw new NullPointerException("商品信息不能为空");
		}

		return this.getSqlMapClientTemplate().update(
				GOODS_BASE_SPACE + "goodsbaseinfo_down_shelve", gbi);
	}

	public int updateGoods(GoodsBaseInfo gbi) throws DataAccessException {
		if (gbi == null) {
			throw new NullPointerException("商品信息不能为空");
		}

		this.getSqlMapClientTemplate().update(
				GOODS_BASE_SPACE + "goodsbaseinfo_update", gbi);
		// Date creatTime = gbi.getGmtCreate();
		// Calendar c = Calendar.getInstance();

		GoodsContent gc = new GoodsContent();
		gc.setGoodsId(gbi.getGoodsId());
		gc.setContent(gbi.getGoodsContent().getContent());
		return this.getSqlMapClientTemplate().update(
				CONTENT_SPACE + "goodscontent_update", gc);
	}

	@SuppressWarnings("unchecked")
	public int deleteGoods(GoodsBaseInfo gbi) throws DataAccessException {

		if (StringUtils.isBlank(gbi.getGoodsId())) {
			throw new NullPointerException("goodsId is null");
		}
		Map map = new HashMap();
		map.put("goodsId", gbi.getGoodsId());
		map.put("goodsStatus", GoodsBaseInfo.DELETE_STATUS);
		this.getSqlMapClientTemplate().update(
				SHOP_SPACE + "shop_goods_count_delete", gbi.getShopId());
		
		// 标记图片为删除状态
		this.getSqlMapClientTemplate().update(
			PICTURE_SPACE + "pictureinfo_delete_by_goodsid", map);
		
		return this.getSqlMapClientTemplate().update(
				GOODS_BASE_SPACE + "delete_goodsbaseinfo", map);

	}

	public GoodsBaseInfo getGoodsWholeInfoById(String goodsId)
			throws DataAccessException {
		if (goodsId == null) {
			throw new NullPointerException("goodsId is null");
		}
		
		GoodsBaseInfo gbi = (GoodsBaseInfo) this.getSqlMapClientTemplate().queryForObject(
				GOODS_BASE_SPACE + "get_goods_whole_info", goodsId);
		
		List<PictureInfo> pis =  (List<PictureInfo>)this.getSqlMapClientTemplate().queryForList(
			PICTURE_SPACE + "get_pictureinfo_list_by_goodsid", goodsId);
		gbi.setPictures(pis);
		return gbi;
	}

	public GoodsBaseInfo getGoodsBaseInfoById(String goodsId)
			throws DataAccessException {
		if (goodsId == null) {
			throw new NullPointerException("goodsId is null");
		}

		return (GoodsBaseInfo) this.getSqlMapClientTemplate().queryForObject(
				GOODS_BASE_SPACE + "get_goods_base_info", goodsId);
	}

	@SuppressWarnings("unchecked")
	public GoodsQuery getActiveGoodsList(GoodsQuery query)
			throws DataAccessException {
		return this.getGoodsWithStatus(query);
	}

	@SuppressWarnings("unchecked")
	public GoodsQuery getDownGoodsList(GoodsQuery query)
			throws DataAccessException {
		return this.getGoodsWithStatus(query);
	}

	@SuppressWarnings("unchecked")
	private GoodsQuery getGoodsWithStatus(GoodsQuery query) {

		Map map = new HashMap();
		map.put("memberId", query.getMemberId());
		map.put("goodsStatus", query.getGoodsStatus());
		Integer totalItem = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(GOODS_BASE_SPACE + "COUNT_GOODS_WITH_STATUS",
						map);
		if (totalItem == 0) {
			query.setTotalItem(0);
			query.setGoods(Collections.EMPTY_LIST);
			return query;
		}
		query.setTotalItem(totalItem);
		List<GoodsBaseInfo> list = this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "get_goodsbaseinfo_list_by_memberId", query);
		query.setGoods(list);
		return query;

	}

	@SuppressWarnings("unchecked")
	public List<GoodsBaseInfo> getGoodsListByDate(Date date)
			throws DataAccessException {
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "get_goodsbaseinfo_list_bydate", date);
	}

	public String getCreatorByGoodsId(String goodsId)
			throws DataAccessException {
		if (StringUtils.isBlank(goodsId)) {
			throw new NullPointerException("goodsId can not be null");
		}
		return (String) this.getSqlMapClientTemplate().queryForObject(
				GOODS_BASE_SPACE + "get_creator_by_goodsId", goodsId);
	}

	@SuppressWarnings("unchecked")
	public GoodsQuery getSearchList(GoodsQuery query)
			throws DataAccessException {

		Integer totalItem = (Integer) this
				.getSqlMapClientTemplate()
				.queryForObject(
						GOODS_BASE_SPACE + "get_goodscount_by_query_conditions",
						query);
		if (totalItem == 0) {
			query.setTotalItem(0);
			query.setGoods(Collections.EMPTY_LIST);
			return query;
		}
		query.setTotalItem(totalItem);
		List<GoodsBaseInfo> list = this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "get_goodslist_by_query_conditions", query);
		query.setGoods(list);
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<GoodsBaseInfo> getGoodsListForIndex(IndexGoodsQuery query)
			throws DataAccessException {
		Map map = new HashMap();
		map.put("begin", query.getBegin());
		map.put("end", query.getEnd());
		Integer totalItem = (Integer) this
				.getSqlMapClientTemplate()
				.queryForObject(GOODS_BASE_SPACE + "COUNT_GOODS_FOR_INDEX", map);
		if (totalItem == 0) {
			query.setTotalItem(0);
			return Collections.EMPTY_LIST;
		}
		query.setTotalItem(totalItem);
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "get_GoodsList_For_Index", query);
	}

	@SuppressWarnings("unchecked")
	public GoodsQuery getShopActiveGoods(GoodsQuery query)
			throws DataAccessException {

		Map map = new HashMap();
		map.put("shopId", query.getShopId());
		map.put("goodsStatus", query.getGoodsStatus());
		map.put("goodsType", query.getGoodsType());
		Integer totalItem = (Integer) this.getSqlMapClientTemplate()
				.queryForObject(GOODS_BASE_SPACE + "COUNT_GOODS_IN_SHOP", map);
		if (totalItem == 0) {
			query.setTotalItem(0);
			query.setGoods(Collections.EMPTY_LIST);
			return query;
		}

		query.setTotalItem(totalItem);
		List<GoodsBaseInfo> list = this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "GET_GOODS_IN_SHOP", query);
		query.setGoods(list);
		return query;
	}

	public void updateGoodsViewCount(final Map<String, Integer> id2ViewMap) {
		if (id2ViewMap == null) {
			throw new NullPointerException("id2ViewMap can't be null");
		}

		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (Entry<String, Integer> entry : id2ViewMap.entrySet()) {
					StringToInteger sti = new StringToInteger(entry.getKey(),
							entry.getValue());
					executor
							.update(GOODS_BASE_SPACE + "UDPATE_VIEW_COUNT", sti);
				}
				executor.executeBatch();
				return null;
			}
		});

	}

	@SuppressWarnings("unchecked")
	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		Map map = new HashMap();
		map.put("ids", ids);
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_BASE_SPACE + "GET_GOODS_BYIDS", map);
	}
	
	//qjy add 20071114
	public Date getSysDate() throws DataAccessException{
		return (Date) this.getSqlMapClientTemplate().queryForObject(GOODS_BASE_SPACE + "getSysDate");
	}

	public void updateGoodGroupNum(String goodsId,Long num) {
		if (goodsId == null) {
			throw new NullPointerException("goodsId is null");
		}
		if (num == null) {
			throw new NullPointerException("num is null");
		}
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		map.put("num", num);
		 this.getSqlMapClientTemplate().update(
				 GOODS_BASE_SPACE + "goodsgroupnum_update", map);
		
	}
	
	
	
	public List<GoodsProperty> getGoodsPropertys(int typeId){
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_PROPERTY_SPACE + "get_goods_whole_property_by_type_id", typeId);
	}
	
	public List<GoodsProperty> getGoodsPropertys(String typeName){
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_PROPERTY_SPACE + "get_goods_whole_property_by_type_name", typeName);
	}
	
	public GoodsPropertyType getGoodsPropertyType(int typeId){
		return  (GoodsPropertyType)this.getSqlMapClientTemplate().queryForObject(
				GOODS_PROPERTY_SPACE + "get_goods_propertys_by_type_id",typeId);
	}
	
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter){
		return this.getSqlMapClientTemplate().queryForList(
				GOODS_PROPERTY_SPACE + "get_goods_propertys_by_type_id_list", parameter);
	}

}
