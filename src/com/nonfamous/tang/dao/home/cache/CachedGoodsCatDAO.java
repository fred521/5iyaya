package com.nonfamous.tang.dao.home.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.domain.GoodsCat;

/**
 * @author eyeieye
 * @version $Id: CachedGoodsCatDAO.java,v 1.1 2008/07/11 00:46:47 fred Exp $
 */
public class CachedGoodsCatDAO implements GoodsCatDAO {
	private CompactCache cache;

	private GoodsCatDAO target;

	private String allCatsKey = "allCatsKey";

	@SuppressWarnings("unchecked")
	public List<GoodsCat> getAllGoodsCat() {
		List<GoodsCat> all = (List<GoodsCat>) cache.get(allCatsKey);
		if (all == null) {
			all = injectCache();
		}
		return Collections.unmodifiableList(all);
	}

	private synchronized List<GoodsCat> injectCache() {
		List<GoodsCat> all = target.getAllGoodsCat();
		cache.put(allCatsKey, all);// put all
		for (GoodsCat gc : all) {// put every goodsCat
			cache.put(gc.getTypeId(), gc);
		}
		for (GoodsCat gc : all) {// build children{
			String parentId = gc.getParentId();
			if (parentId == null || parentId.equals(gc.getTypeId())) {
				continue;
			}
			GoodsCat parent = (GoodsCat) cache.get(parentId);
			if (parent != null) {
				parent.getChildren().add(gc);
			}
			gc.setParent(parent);
		}
		return all;
	}

	public GoodsCat getGoodsCatById(String catId) {
		if (catId == null) {
			throw new NullPointerException("GoodsCat can't be null");
		}
		GoodsCat gc = (GoodsCat) cache.get(catId);
		if (gc != null) {
			return gc;
		}
		getAllGoodsCat();
		gc = (GoodsCat) cache.get(catId);
		return gc;
	}

	public List<GoodsCat> getRootGoodsCat() {
		List<GoodsCat> all = getAllGoodsCat();
		List<GoodsCat> rtn = new ArrayList<GoodsCat>();
		for (GoodsCat cat : all) {
			if (cat.isRoot()) {
				rtn.add(cat);
			}
		}
		return rtn;
	}

	public GoodsCat getGoosCatByName(String catName) {
		if (catName == null) {
			throw new NullPointerException("cat name can't be null");
		}
		List<GoodsCat> all = getAllGoodsCat();
		for (GoodsCat gc : all) {
			if (gc.getTypeName().equals(catName)) {
				return gc;
			}
		}
		return null;
	}

	public String getAllCatsKey() {
		return allCatsKey;
	}

	public void setAllCatsKey(String allCatsKey) {
		this.allCatsKey = allCatsKey;
	}

	public CompactCache getCache() {
		return cache;
	}

	public void setCache(CompactCache cache) {
		this.cache = cache;
	}

	public GoodsCatDAO getTarget() {
		return target;
	}

	public void setTarget(GoodsCatDAO target) {
		this.target = target;
	}

}
