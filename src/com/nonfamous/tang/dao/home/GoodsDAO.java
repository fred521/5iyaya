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
	 * ������Ʒ
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public String addGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * ������Ʒ��Ϣ
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * ɾ����Ʒ�������ɾ��ͳָ�߼�ɾ��
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * �õ���Ʒ�Ļ�����Ϣ
	 * 
	 * @param goodsId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsBaseInfo getGoodsBaseInfoById(String goodsId)
			throws DataAccessException;

	/**
	 * �õ���Ʒ�����飨����content��
	 * 
	 * @param goodsId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsBaseInfo getGoodsWholeInfoById(String goodsId)
			throws DataAccessException;

	/**
	 * �õ�һ�����ҵ������ϼ���Ʒ
	 * 
	 * @param memberId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getActiveGoodsList(GoodsQuery query)
			throws DataAccessException;

	/**
	 * �õ�һ�����̵������ϼ���Ʒ
	 * 
	 * @param memberId
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getShopActiveGoods(GoodsQuery query)
			throws DataAccessException;

	/**
	 * �õ��¼���Ʒ
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public GoodsQuery getDownGoodsList(GoodsQuery query)
			throws DataAccessException;

	// /**
	// * �õ�������Ʒbase��Ϣ
	// *
	// * @param
	// * @return
	// * @throws DataAccessException
	// */
	// public GoodsQuery getAllGoodsList(GoodsQuery query)
	// throws DataAccessException;

	/**
	 * �õ�����ʱ������ĳʱ���������Ʒbase��Ϣ
	 * 
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsBaseInfo> getGoodsListByDate(Date date)
			throws DataAccessException;

	/**
	 * ���ݲ�ѯ�����õ���Ӧ�б�
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
	 * ���ݲ�ѯ�����õ���Ӧ�б�,Ϊ����������
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<GoodsBaseInfo> getGoodsListForIndex(IndexGoodsQuery query)
			throws DataAccessException;

	/**
	 * �ϼ���Ʒ
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int upGoods(GoodsBaseInfo gbi) throws DataAccessException;

	/**
	 * �¼���Ʒ
	 * 
	 * @param gbi
	 * @return
	 * @throws DataAccessException
	 */
	public int downGoods(GoodsBaseInfo gbi) throws DataAccessException;

	public void updateGoodsViewCount(Map<String, Integer> id2ViewMap);
	
	/**
	 * ������ȡ��Ʒ������Ϣ������ҳ
	 * @param ids
	 * @return
	 */
	public List<GoodsBaseInfo> findGoodsBaseInfos(String[] ids);
	
	/**
	 * qjy add 20071114
	 * �õ����ݿ�ϵͳʱ��
	 */
	public Date getSysDate() throws DataAccessException;

	public void updateGoodGroupNum(String goodsId,Long num);
	
	
	public List<GoodsProperty> getGoodsPropertys(int typeId);
	
	public List<GoodsProperty> getGoodsPropertys(String typeName);
	
	public GoodsPropertyType getGoodsPropertyType(int typeId);
	
	public List<GoodsPropertyType> getGoodsPropertyTypeList(Map parameter);
}
