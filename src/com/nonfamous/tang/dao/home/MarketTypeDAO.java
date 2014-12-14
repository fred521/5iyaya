package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;

/**
 * @author: alan
 * 
 * <pre>
 * �г�����dao
 * </pre>
 * 
 * @version $Id: MarketTypeDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface MarketTypeDAO {

	/**
	 * ��ȡ�����г�����
	 * 
	 * @return
	 */
	public List<MarketType> getAllMarketType();

	/**
	 * �����г�����id��ȡ�г�������Ϣ
	 * 
	 * @param typeId
	 * @return
	 */
	public MarketType getMarketTypeById(String typeId);
	
//	/**
//	 * �����г�����id��ȡ�г�������Ϣ
//	 * 
//	 * @param typeId
//	 * @return
//	 */
//	public List<Shop> getShopByMarketId(String MarketId);
	
	/**
	 * �����г������ȡ�г�������Ϣ,�����г���Ϣ
	 * 
	 * @param typeId
	 * @return
	 */
	public List<Shop> getAllShopByMarketId();

}
