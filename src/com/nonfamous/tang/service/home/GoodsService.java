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
	 * �����Ʒ
	 * @param gbi
	 * @param content
	 * @param file
	 * @return
	 */
	public String addGoods(GoodsBaseInfo gbi,MultipartFile file);
	/**
	 * ������Ʒ��Ϣ
	 * @param gbi
	 * @param content
	 * @param file
	 */
	public int updateGoods(GoodsBaseInfo gbi,MultipartFile file);
	/**
	 * ��Ʒ�ϼ�
	 * @param gbi
	 */
	public int add2Shelves(GoodsBaseInfo gbi);
	/**
	 * ��Ʒ�¼�
	 * @param gbi
	 */
	public int downFromShelves(GoodsBaseInfo gbi);
	/**
	 * ��Ʒɾ��
	 * @param goodsId
	 */
	public int deleteGoods(String goodsId);
	/**
	 * �õ���Ʒ�б��ϼܵģ�������memberid����
	 * @param memberId
	 * @return
	 */
	public GoodsQuery getActiveGoods(GoodsQuery query);
	/**
	 * �õ��¼ܵ���Ʒ�б�
	 * @param memberId
	 * @return
	 */
	public GoodsQuery getDownGoods(GoodsQuery query);
	
	/**
	 * ��̨�ã��õ��ϼܺ��¼���Ʒ���б�
	 * @param query
	 * @return
	 */
	public GoodsQuery getGoodsList(GoodsQuery query);
	
	/**
	 * ��ҳ�ã���ȡ��ҳ�Ź���Ʒ�б�
	 * 
	 * @param query
	 * @return
	 */
	public List<GoodsBaseInfo> getLatestGroupBuyGoodsList();
	
	/**
	 * �õ���Ʒ���飬����goods��content
	 * @param goodsId
	 * @return
	 */
	public GoodsBaseInfo getGoodsWholeInfo(String goodsId);
	/**
	 * �õ���Ʒ��Ϣ��������content
	 * @param goodsId
	 * @return
	 */
	public GoodsBaseInfo getGoodsBaseInfo(String goodsId);

	/**
	 * ����shopid�õ��ϼ���Ʒ
	 * @param query
	 * @return
	 */
	public GoodsQuery getActiveShopGoods(GoodsQuery query);
	
	/**
	 * ������Ʒ�����һ
	 * @param goodsId
	 */
	public void addViewCount(String goodsId);
	
	/**
	 * ������ȡ��Ʒ������Ϣ������ҳ
	 * @param ids
	 * @return
	 */
	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids);
	
	/**
	 * �õ����ݿ⵱ǰʱ��
	 * @return
	 */	
     public Date getSysDate();
     /**
      * �����µ����Ź���
      * @param num
      */
	public void updateGoodGroupNum(String goodsId,Long num);
	
	/**
	 * ͨ����������Id��ѯ ĳ����ѡ���Ե����п�ѡֵ
	 * @param propertyId
	 */
	public List<GoodsProperty> getGoodsPropertys(int propertyId);
	
	/**
	 * ͨ��������������ѯ ĳ����ѡ���Ե����п�ѡֵ
	 * @param propertyTypeName
	 */
	public List<GoodsProperty> getGoodsPropertys(String propertyTypeName);
	
	/**
	 * ͨ����������Id��ѯ
	 * @param typeId
	 * @return
	 */
	public GoodsPropertyType getGoodsPropertyType(int typeId);
	
	/**
	 * ͨ����������ID�б��ѯ
	 * @param parameter
	 * @return
	 */
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter);

}
