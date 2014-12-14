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
	 * ����Shop
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void insertShop(Shop shop) throws DataAccessException;

	/**
	 * �������̹���
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void insertShopCommend(ShopCommend commend)
			throws DataAccessException;

	/**
	 * �޸ĵ��̹��� ��Ҫ���µ�������޸�ʱ��
	 * 
	 * @param commend
	 * @param shop
	 * @throws DataAccessException
	 */
	public void updateShopCommend(ShopCommend commend, Shop shop)
			throws DataAccessException;

	/**
	 * ͨ����Աid��ѯ����(�г����ƣ�����)
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopfullSelectByMemberId(String memberId)
			throws DataAccessException;

	/**
	 * ͨ����Աid��ѯ����
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByMemberId(String memberId)
			throws DataAccessException;

	/**
	 * ͨ������id��ѯ����
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByShopId(String shopId) throws DataAccessException;

	/**
	 * ͨ������id��ѯ����(ȫ����Ϣ)
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopfullSelectByShopId(String shopId, String gisAddress)
			throws DataAccessException;

	/**
	 * �޸ĵ���
	 * 
	 * @param nbi
	 * @return
	 * @throws DataAccessException
	 */
	public void updateShop(Shop shop) throws DataAccessException;

	/**
	 * add by victor
	 * 
	 * ���ݲ�ѯ�����õ���Ӧ�б�,Ϊ����������
	 * 
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<Shop> getShopListForIndex(SearchMemberShopQuery query)
			throws DataAccessException;

	public ShopCommend getShopCommendByMemberId(String memberId);

	/**
	 * �������һ�Ա��Ϣ��ȡ������Ϣ
	 */
	public Map queryShopByMember(List memberList);

	/**
	 * ����query��Ϣ��ȡ������Ϣ
	 * 
	 * @param query
	 * @return
	 */
	public Map quickQueryShop(MemberQuery query);

	/**
	 * ͨ��������ά��ַ��ѯ����
	 * 
	 * @param buyer
	 * @return
	 */
	public Shop shopSelectByGisAddress(String gisAddress);

	/**
	 * �������еĵ���id��ȡ������Ϣ
	 * 
	 * @param ids
	 * @return
	 */
	public List<Shop> getShopListByIds(String[] ids);

	/**
	 * ��ȡ����ע����̼���Ϣ
	 * 
	 * @return
	 */
	public List<Shop> getLatestShopList();
	
	/**
	 * �������ҵ�ID��ȡ������Ϣ
	 * 
	 * @param memberIds
	 * @return
	 */
	public Map<String,Shop> getgetShopMapByMemberIds(String[] memberIds);
	
	/**
	 * �õ���Ч��Ʒ����
	 * 
	 * @return
	 */
	public Integer getEffectGoodsCount();
	
	/**
	 * �õ���������
	 * 
	 * @return
	 */
	public Integer getShopCommendCount();
	
	/**
	 * �õ���������
	 * 
	 * @return
	 */
	public Integer getShopNameCount();
	
	/**
	 * �õ�������Ϣ����
	 * 
	 * @return
	 */
	public Integer getAllNewsCount();
	/**
	 * ������ݿ�ϵͳʱ��
	 * 
	 * @return
	 */
	public Date getSysDate()throws DataAccessException;

}
