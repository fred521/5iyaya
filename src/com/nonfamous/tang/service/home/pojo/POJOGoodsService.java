package com.nonfamous.tang.service.home.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nonfamous.commom.cache.count.HitsCountService;
import com.nonfamous.commom.cache.count.impl.AbstractHitsCountService;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.dao.query.IndexGoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsProperty;
import com.nonfamous.tang.domain.GoodsPropertyType;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;

/**
 * 
 * @author fred
 * 
 */
public class POJOGoodsService extends POJOServiceBase implements GoodsService {

	private GoodsDAO goodsDAO;

	private ShopDAO shopDAO;

	private UploadService uploadService;

	private HitsCountService<String> viewCountService = new AbstractHitsCountService<String>() {

		@Override
		public void writerHits(Map<String, Integer> id2HitsMap) {
			goodsDAO.updateGoodsViewCount(id2HitsMap);
		}

	};

	public String addGoods(GoodsBaseInfo gbi, MultipartFile file) {

		if (gbi == null) {
			throw new NullPointerException(
					"GoodsBaseInfo object can not be null");
		}

		try {
			if (file != null && file.getSize() > 0) {
				String path = uploadService.uploadGoodsImageWithCompress(file);
				gbi.setGoodsPic(path);
			}
		} catch (UploadFileException e) {
			logger.error("upload goodsPic failed." + e.getMessage());
			throw new ServiceException("上传图片失败");
		}

		String goodsId = goodsDAO.addGoods(gbi);
		return goodsId;

	}

	public int updateGoods(GoodsBaseInfo gbi, MultipartFile file) {

		if (gbi == null) {
			throw new NullPointerException("GoodsBaseInfo object is null");
		}
		GoodsBaseInfo goods = goodsDAO.getGoodsBaseInfoById(gbi.getGoodsId());

		try {
			if (file != null && file.getSize() > 0) {
				if (StringUtils.isBlank(goods.getGoodsPic())) {
					uploadService.deleteImageFile(goods.getGoodsPic());
				}
				String path = uploadService.uploadGoodsImageWithCompress(file);
				gbi.setGoodsPic(path);
			}
		} catch (UploadFileException e) {
			logger.error("error when upload goods picture", e);
			throw new ServiceException("上传图片失败");
		}

		return goodsDAO.updateGoods(gbi);
	}

	public int add2Shelves(GoodsBaseInfo gbi) {

		if (gbi == null) {
			throw new NullPointerException("GoodsBaseInfo is null");
		}

		return goodsDAO.upGoods(gbi);
	}

	public int downFromShelves(GoodsBaseInfo gbi) {
		if (gbi == null) {
			throw new NullPointerException("GoodsBaseInfo is null");
		}

		return goodsDAO.downGoods(gbi);
	}

	public int deleteGoods(String goodsId) {

		if (StringUtils.isBlank(goodsId)) {
			throw new NullPointerException("goodsId is null");
		}
		GoodsBaseInfo gbi = goodsDAO.getGoodsBaseInfoById(goodsId);

		if (gbi.getGoodsPic() != null) {
			uploadService.deleteImageFile(gbi.getGoodsPic());
		}
		return goodsDAO.deleteGoods(gbi);
	}

	public GoodsQuery getActiveGoods(GoodsQuery query) {

		query.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		return goodsDAO.getActiveGoodsList(query);
	}

	public GoodsQuery getDownGoods(GoodsQuery query) {

		query.setGoodsStatus(GoodsBaseInfo.DOWN_STATUS);

		return goodsDAO.getDownGoodsList(query);
	}

	public GoodsQuery getGoodsList(GoodsQuery query) {

		query.setGoodsStatus(GoodsBaseInfo.DOWN_STATUS);
		query.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		return goodsDAO.getSearchList(query);
	}

	public List<GoodsBaseInfo> getLatestGroupBuyGoodsList(){
		GoodsQuery query = new GoodsQuery();
		query.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		query.setGoodsType(GoodsBaseInfo.TEAM_TYPE);
		query.setCurrentPage(1);
		query.setPageSize(10);
		query.setOrderBy("orderByDate");
		return goodsDAO.getSearchList(query).getGoods();
	}
	
	public GoodsBaseInfo getGoodsBaseInfo(String goodsId) {
		if (StringUtils.isBlank(goodsId)) {
			throw new NullPointerException("goodsId is null");
		}
		return goodsDAO.getGoodsBaseInfoById(goodsId);
	}

	public GoodsBaseInfo getGoodsWholeInfo(String goodsId) {

		if (StringUtils.isBlank(goodsId)) {
			throw new NullPointerException("goodsId is null");
		}
		return this.goodsDAO.getGoodsWholeInfoById(goodsId);
	}

	public Shop getShopByMemberId(String memberId) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(memberId)) {
			throw new NullPointerException("memberId为空");
		}
		return shopDAO.shopSelectByMemberId(memberId);
	}
	/**
	 * 得到数据库当前时间
	 * @return
	 */	
	public Date getSysDate()
	{
		return goodsDAO.getSysDate();
	}

	public GoodsQuery getActiveShopGoods(GoodsQuery query) {
		if (StringUtils.isBlank(query.getShopId())) {
			throw new NullPointerException("shopId is null");
		}
		return goodsDAO.getShopActiveGoods(query);
	}

	public void addViewCount(String goodsId) {
		if (goodsId == null) {
			throw new NullPointerException("goodsId is null");
		}
		this.viewCountService.addHitOnce(goodsId);
	}

	public void destroy() {
		this.viewCountService.flush();
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public void setGoodsDAO(GoodsDAO goodsDAO) {
		this.goodsDAO = goodsDAO;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public HitsCountService<String> getViewCountService() {
		return viewCountService;
	}

	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		return goodsDAO.findGoodsBaseInfos(ids);
	}

	public void updateGoodGroupNum(String goodsId,Long num) {
		if(goodsId==null){
			throw new NullPointerException("goodsId is null");
		}
		if(num==null){
			throw new NullPointerException("num is null");
		}
		goodsDAO.updateGoodGroupNum(goodsId,num);
		
	}
	
	public List<GoodsProperty> getGoodsPropertys(String propertyTypeName){
		return this.goodsDAO.getGoodsPropertys(propertyTypeName);
		
	}
	
	public GoodsPropertyType getGoodsPropertyType(int typeId){
		return this.goodsDAO.getGoodsPropertyType(typeId);
	}
	
	
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter){
		return this.goodsDAO.getGoodsPropertyTypeList(parameter);
	}
	
	/**
	 * 通过属性类型Id查询 某个候选属性的所有可选值
	 * @param propertyId
	 */
	public List<GoodsProperty> getGoodsPropertys(int typeId){
		return this.goodsDAO.getGoodsPropertys(typeId);
	}
	

}
