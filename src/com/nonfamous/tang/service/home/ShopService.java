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
	 * ��������(ͬʱ�������̹���)
	 * 
	 * @param shop,needSelectByMemeberId�Ƿ�Ҫ��ѯ���û��ĵ��̣���̨������ʱ���Ѿ���ѯ����������β�ѯ
	 * @return
	 */
	public ShopResult insertShop(Shop shop, boolean needSelectByMemeberId);

	/**
	 * ͨ����Աid��ѯ����(ȫ��shop��Ϣ)
	 * 
	 * @param memberId
	 * @return
	 */
	public Shop shopfullSelectByMemberId(String memberId);

	/**
	 * ͨ����Աid��ѯ����
	 * 
	 * @param memberId
	 * @return
	 */
	public Shop shopSelectByMemberId(String memberId);

	/**
	 * ͨ������id��ѯ����
	 * 
	 * @param shopId
	 * @return
	 */
	public Shop shopSelectByShopId(String shopId);

	/**
	 * ͨ������id��ѯ����(ȫ������Ϣ)
	 * 
	 * @param shopId
	 * @return
	 */
	public Shop shopfullSelectByShopId(String shopId, String shopAddress);

	/**
	 * memberId��õ��̹���
	 * 
	 * @param commend
	 * @return
	 */
	public ShopCommend getShopCommendByMemberId(String memberId);

	/**
	 * �޸ĵ��̹���,��Ҫ���µ��̵�����޸�ʱ��
	 * 
	 * @param commend
	 * @param shop
	 * @return
	 */
	public ShopCommend updateShopCommend(ShopCommend commend, Shop shop);

	/**
	 * �޸ĵ���
	 * 
	 * @param shop
	 * @return
	 */
	public Shop updateShop(Shop shop);

	/**
	 * ���ǩԼ����
	 * 
	 * @return
	 */
	public ShopResult addSignShop(String shopId, String memberId);

	/**
	 * ��ȡ�û�����ǩԼ����
	 * 
	 * @param memberId
	 * @return
	 */
	public List<Shop> getMySignShopList(String memberId);
	
	/**
 	 * �õ���Ч��Ʒ����
 	 * @return
 	 */   
     public Integer getEffectGoodsCount();
     

     /**
   	 * �õ���������
   	 * @return
   	 */
      public Integer getShopCommendCount();
      
      /**
   	 * �õ���������
   	 * @return
   	 */
     public Integer getShopNameCount();
     
     /**
      * �õ�������Ϣ����
      * @return
      */
      public Integer getAllNewsCount();
      
      /**
       * ��ȡ�����̼���Ϣ
       */
      public List<Shop> getLatestShopList();
}
