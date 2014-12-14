package com.nonfamous.tang.service.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

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
public interface GoodsService {
	
	/**
	 * 添加商品
	 * @param gbi
	 * @param content
	 * @param file
	 * @return
	 */
	public String addGoods(GoodsBaseInfo gbi,MultipartFile file);
	/**
	 * 更新商品信息
	 * @param gbi
	 * @param content
	 * @param file
	 */
	public int updateGoods(GoodsBaseInfo gbi,MultipartFile file);
	/**
	 * 商品上架
	 * @param gbi
	 */
	public int add2Shelves(GoodsBaseInfo gbi);
	/**
	 * 商品下架
	 * @param gbi
	 */
	public int downFromShelves(GoodsBaseInfo gbi);
	/**
	 * 商品删除
	 * @param goodsId
	 */
	public int deleteGoods(String goodsId);
	/**
	 * 得到商品列表（上架的），根据memberid来的
	 * @param memberId
	 * @return
	 */
	public GoodsQuery getActiveGoods(GoodsQuery query);
	/**
	 * 得到下架的商品列表
	 * @param memberId
	 * @return
	 */
	public GoodsQuery getDownGoods(GoodsQuery query);
	
	/**
	 * 后台用，得到上架和下架商品的列表
	 * @param query
	 * @return
	 */
	public GoodsQuery getGoodsList(GoodsQuery query);
	
	/**
	 * 首页用，获取首页团购商品列表
	 * 
	 * @param query
	 * @return
	 */
	public List<GoodsBaseInfo> getLatestGroupBuyGoodsList();
	
	/**
	 * 得到商品详情，包含goods的content
	 * @param goodsId
	 * @return
	 */
	public GoodsBaseInfo getGoodsWholeInfo(String goodsId);
	/**
	 * 得到商品信息，不包括content
	 * @param goodsId
	 * @return
	 */
	public GoodsBaseInfo getGoodsBaseInfo(String goodsId);

	/**
	 * 根据shopid得到上架商品
	 * @param query
	 * @return
	 */
	public GoodsQuery getActiveShopGoods(GoodsQuery query);
	
	/**
	 * 增加商品浏览量一
	 * @param goodsId
	 */
	public void addViewCount(String goodsId);
	
	/**
	 * 批量获取商品基本信息，不分页
	 * @param ids
	 * @return
	 */
	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids);
	
	/**
	 * 得到数据库当前时间
	 * @return
	 */	
     public Date getSysDate();
     /**
      * 更新下单的团购数
      * @param num
      */
	public void updateGoodGroupNum(String goodsId,Long num);
	
	/**
	 * 通过属性类型Id查询 某个候选属性的所有可选值
	 * @param propertyId
	 */
	public List<GoodsProperty> getGoodsPropertys(int propertyId);
	
	/**
	 * 通过属性类型名查询 某个候选属性的所有可选值
	 * @param propertyTypeName
	 */
	public List<GoodsProperty> getGoodsPropertys(String propertyTypeName);
	
	/**
	 * 通过属性类型Id查询
	 * @param typeId
	 * @return
	 */
	public GoodsPropertyType getGoodsPropertyType(int typeId);
	
	/**
	 * 通过属性类型ID列表查询
	 * @param parameter
	 * @return
	 */
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter);

}
