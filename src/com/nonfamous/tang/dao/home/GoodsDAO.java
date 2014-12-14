package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.dao.query.IndexGoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsProperty;
import com.nonfamous.tang.domain.GoodsPropertyType;

/**
 * 
 * @author alien
 * 
 */
public interface GoodsDAO {

	/**
	 * 新增商品
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public String addGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * 更新商品信息
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * 删除商品，这里的删除统指逻辑删除
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * 得到商品的基本信息
	 * 
	 * @param goodsId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsBaseInfo getGoodsBaseInfoById(String goodsId)
			throws DataAccessException;

	/**
	 * 得到商品的详情（包括content）
	 * 
	 * @param goodsId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsBaseInfo getGoodsWholeInfoById(String goodsId)
			throws DataAccessException;

	/**
	 * 得到一个卖家的所有上架商品
	 * 
	 * @param memberId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getActiveGoodsList(GoodsQuery query)
			throws DataAccessException;

	/**
	 * 得到一个店铺的所有上架商品
	 * 
	 * @param memberId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getShopActiveGoods(GoodsQuery query)
			throws DataAccessException;

	/**
	 * 得到下架商品
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getDownGoodsList(GoodsQuery query)
			throws DataAccessException;

	// /**
	// * 得到所有商品base信息
	// *
	// * @param
	// * @return
	// * @throws DataAccessException
	// */
	// public GoodsQuery getAllGoodsList(GoodsQuery query)
	// throws DataAccessException;

	/**
	 * 得到更新时间晚于某时间的所有商品base信息
	 * 
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsBaseInfo> getGoodsListByDate(Date date)
			throws DataAccessException;

	/**
	 * 根据查询条件得到相应列表
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getSearchList(GoodsQuery query)
			throws DataAccessException;

	/**
	 * add by victor
	 * 
	 * 根据查询条件得到相应列表,为建索引调用
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsBaseInfo> getGoodsListForIndex(IndexGoodsQuery query)
			throws DataAccessException;

	/**
	 * 上架商品
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int upGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * 下架商品
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int downGoods(GoodsBaseInfo gbi) throws DataAccessException;

	public void updateGoodsViewCount(Map<String, Integer> id2ViewMap);
	
	/**
	 * 批量获取商品基本信息，不分页
	 * @param ids
	 * @return
	 */
	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids);
	
	/**
	 * qjy add 20071114
	 * 得到数据库系统时间
	 */
	public Date getSysDate() throws DataAccessException;

	public void updateGoodGroupNum(String goodsId,Long num);
	
	
	public List<GoodsProperty> getGoodsPropertys(int typeId);
	
	public List<GoodsProperty> getGoodsPropertys(String typeName);
	
	public GoodsPropertyType getGoodsPropertyType(int typeId);
	
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter);
}
