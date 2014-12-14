//===================================================================
// Created on 2007-6-15
//===================================================================
package com.nonfamous.tang.web;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.commom.util.OperateFile;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.search.GoodsIndexService;
import com.nonfamous.tang.service.search.MemberShopIndexService;
import com.nonfamous.tang.service.search.NewsIndexService;



/**
 * <p>
 * 这里写注释
 * </p>
 * 
 * @author alan
 * @version $Id: About.java,v 1.1 2008/07/11 00:47:18 fred Exp $
 */

public class About implements Controller {

	private String currentVersion;

	private String buildNumber;

	private String buildDate;
	
	private GoodsService goodsService;
	
	private ShopService shopService;
	
    private MemberShopIndexService memberShopIndexService;
	
	private GoodsIndexService goodsIndexService;
	
	private NewsIndexService newsIndexService;
	
	private String indexFilePathGood ;
	
	private String indexFilePathShop;
	
    private String indexFilePathNews;
	
	public void setIndexFilePathNews(String indexFilePathNews) {
		this.indexFilePathNews = indexFilePathNews;
	}

	public void setIndexFilePathGood(String indexFilePathGood) {
		this.indexFilePathGood = indexFilePathGood;
	}

	public void setIndexFilePathShop(String indexFilePathShop) {
		this.indexFilePathShop = indexFilePathShop;
	}

	public void setNewsIndexService(NewsIndexService newsIndexService) {
		this.newsIndexService = newsIndexService;
	}

	public void setGoodsIndexService(GoodsIndexService goodsIndexService) {
		this.goodsIndexService = goodsIndexService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = format.format(goodsService.getSysDate());
		//取数据库有效商品的个数
		int effectGoods = shopService.getEffectGoodsCount();
		//取数据库店铺的个数
		int shopCommendCount = shopService.getShopCommendCount();
		//取数据库店主的个数
		int shopNameCount = shopService.getShopNameCount();
		//取数据库所有分类信息的个数
		int allNewsCount = shopService.getAllNewsCount();
		
		SearchMemberShopQuery query = new SearchMemberShopQuery();
		query.setSearchType(SearchMemberShopQuery.MEMBER);
		query = memberShopIndexService.findMemberShop(query);
		//取索引店铺的个数
		int indexShopCommendCount = query.getTotalItem();
		//取索引店主的个数
		int indexShopNameCount = query.getTotalItem();
		
		SearchGoodsQuery query1 = new SearchGoodsQuery();
		query1 = (SearchGoodsQuery) goodsIndexService.findEffectGoods(query1);
		//取索引有效商品的个数
		int indexEffectGoods= query1.getTotalItem();
		 
		
		SearchNewsQuery query2 = new SearchNewsQuery();
		query2 = newsIndexService.findNews(query2);
		//取索引所有分类信息的个数
		int indexAllNewsCount = query2.getTotalItem();
		
		File file=new File(indexFilePathGood+"/timestamp");
		//取索引商品最后更新时间的记录
		String dateGood= format.format(OperateFile.getTimestamp(file));
		//取索引店铺最后更新时间的记录
		String dateShop = format.format(OperateFile.getTimestamp(new File(indexFilePathShop+"/timestamp")));
		//取索引分类信息最后更新时间的记录
		String dateNews = format.format(OperateFile.getTimestamp(new File(indexFilePathNews+"/timestamp")));
	 
		ModelAndView mv = new ModelAndView("about");
		mv.addObject("currentVersion", currentVersion);
		mv.addObject("buildNumber", buildNumber);
		mv.addObject("buildDate", buildDate);
		mv.addObject("date",date);
		mv.addObject("effectGoods",effectGoods);
		mv.addObject("shopCommendCount",shopCommendCount);
		mv.addObject("shopNameCount",shopNameCount);
		mv.addObject("allNewsCount",allNewsCount);
		mv.addObject("indexShopCommendCount",indexShopCommendCount);
		mv.addObject("indexShopNameCount",indexShopNameCount);
		mv.addObject("indexAllNewsCount",indexAllNewsCount);
		mv.addObject("indexEffectGoods",indexEffectGoods);
		mv.addObject("dateGood",dateGood);
		mv.addObject("dateShop",dateShop);
		mv.addObject("dateNews",dateNews);
		//throw new java.lang.NullPointerException("");
		return mv;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

 
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public void setMemberShopIndexService(
			MemberShopIndexService memberShopIndexService) {
		this.memberShopIndexService = memberShopIndexService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

}
