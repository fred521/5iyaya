package com.nonfamous.tang.dao.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;

import com.nonfamous.tang.domain.SearchGoodsCat;
import com.nonfamous.tang.domain.SearchGoodsResult;

/**
 * 
 * @author fred
 * 
 */
public class SearchGoodsQuery extends SearchQueryBase {

	private static final long serialVersionUID = 8315380438874039293L;

	private String goodsCat;// Ҫ��������ƷĿ¼
	
	private String goodsType;//�Ƿ��Ź���Ʒ�ı�־

	private String marketType;// �����г�,������������֮һ

	private List<SearchGoodsCat> searchGoodsCatList;// ������Ŀ¼����Ŀ��ʾ

	private List<SearchGoodsResult> goodsList;// �����������

	private List<Query> queryList = new ArrayList<Query>();// ������ϸ��ϲ�ѯ����
	

	public String getGoodsCat() {
		return goodsCat;
	}

	public void setGoodsCat(String goodsCat) {
		this.goodsCat = goodsCat;
	}

	public List<SearchGoodsResult> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<SearchGoodsResult> goodsList) {
		this.goodsList = goodsList;
	}

	public List<SearchGoodsCat> getSearchGoodsCatList() {
		return searchGoodsCatList;
	}

	public void setSearchGoodsCatList(List<SearchGoodsCat> searchGoodsCatList) {
		this.searchGoodsCatList = searchGoodsCatList;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public List<Query> getQueryList() {
		return queryList;
	}

	public void setQueryList(List<Query> queryList) {
		this.queryList = queryList;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

}
