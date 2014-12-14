//===================================================================
// Created on 2007-6-10
//===================================================================
package com.nonfamous.tang.dao.home.cache;

import java.util.Collections;
import java.util.List;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;


public class CacheMarketTypeDAO implements MarketTypeDAO {

	private CompactCache cache;

	private MarketTypeDAO target;

	private String allMarketTypeKey = "allMarketTypeKey";
	
	private String allShopKey = "allShopKey";

	@SuppressWarnings("unchecked")
	public List<MarketType> getAllMarketType() {
		List<MarketType> all = (List<MarketType>) cache.get(allMarketTypeKey);
		if (all == null) {
			all  = injectCache();
		}
		return Collections.unmodifiableList(all);
	}

	private List<MarketType> injectCache() {
		List<MarketType> all;
		all = target.getAllMarketType();
		cache.put(allMarketTypeKey, all);
		for (MarketType mt : all) {
			cache.put(mt.getMarketType(), mt);
		}
		return all;
	}

	public MarketType getMarketTypeById(String typeId) {
		if (StringUtils.isBlank(typeId)) {
			throw new NullPointerException("market type id can't null");
		}
		MarketType type = (MarketType) this.cache.get(typeId);
		if(type != null){
			return type;
			
		}
		getAllMarketType();
		return (MarketType) this.cache.get(typeId);
	}

	public void setCache(CompactCache cache) {
		this.cache = cache;
	}

	public void setTarget(MarketTypeDAO target) {
		this.target = target;
	}

//	public List<Shop> getShopByMarketId(String MarketId) {
//		List<Shop> all;
//		all = target.getShopByMarketId(MarketId);
//		cache.put(allShopInMarketKey, all);
//		for (Shop mt : all) {
//			cache.put(mt.getBelongMarketId(), mt);
//		}
//		return all;
//	}

	@SuppressWarnings("unchecked")
	public List<Shop> getAllShopByMarketId() {
		List<Shop> all = (List<Shop>) cache.get(allShopKey);
		if (all == null) {
			all  = injectShopCache();
		}
		return Collections.unmodifiableList(all);
	}
	
	private List<Shop> injectShopCache() {
		List<Shop> all;
		all = target.getAllShopByMarketId();
		cache.put(allShopKey, all);
		return all;
	}

}
