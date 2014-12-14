package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.service.home.GoodsCatalogService;

/**
 * @author: fish
 * @version $Id: POJOGoodsCatalogService.java,v 1.1 2008/07/11 00:46:46 fred Exp $
 */
public class POJOGoodsCatalogService extends POJOServiceBase implements
		GoodsCatalogService {
	private GoodsCatDAO goodsCatDAO;

	public GoodsCatDAO getGoodsCatDAO() {
		return goodsCatDAO;
	}

	public void setGoodsCatDAO(GoodsCatDAO goodsCatDAO) {
		this.goodsCatDAO = goodsCatDAO;
	}

	public List<GoodsCat> getAllGoodsCat() {
		return this.goodsCatDAO.getAllGoodsCat();
	}

	public GoodsCat getGoodsCatById(String catId) {
		return this.goodsCatDAO.getGoodsCatById(catId);
	}

	public List<GoodsCat> getRootGoodsCat() {
		return this.goodsCatDAO.getRootGoodsCat();
	}

	public GoodsCat getGoosCatByName(String catName) {
		if (catName == null) {
			throw new NullPointerException("cat name cat't be null");
		}
		return this.goodsCatDAO.getGoosCatByName(catName);
	}

}
