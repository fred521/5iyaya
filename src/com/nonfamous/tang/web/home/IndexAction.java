package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.NewsType;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.web.common.PageConstants;
import com.nonfamous.tang.web.common.SearchTypeConstants;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * <p>
 * 首页
 * </p>
 * 
 * @author victor
 */
public class IndexAction extends MultiActionController {

	private GoodsService goodsService;

	private ShopService shopService;
	
	private CommendContentDAO commendContentDAO;

	private NewsTypeDAO newsTypeDAO;

	private MarketTypeDAO marketTypeDAO;

	private NewsDAO newsDAO;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为good，即商品搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Goods);
		this.commend(request, PageConstants.INDEX);
		List<MarketType> mtypeList = marketTypeDAO.getAllMarketType();
		Map<String, NewsTypeAndInfoDTO> map = newsDAO.getYYNewsInfo();
		Map<String, NewsTypeAndInfoDTO> otherMap = newsDAO.getAllNewsInfo();
		//最新团购商品
		List<GoodsBaseInfo> groupBuyGoodsList = goodsService.getLatestGroupBuyGoodsList();
		//最新商家信息
		List<Shop> shopList = shopService.getLatestShopList(); 
			
		ModelAndView mv = new ModelAndView("home/home/index");
		mv.addObject("marketList", mtypeList);
		mv.addObject("groupBuyGoodsList", groupBuyGoodsList);
		mv.addObject("shopList", shopList);
		mv.addObject("map", map);
		mv.addObject("otherMap", otherMap);
		return mv;
	}
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为good，即商品搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Goods);
		this.commend(request, PageConstants.INDEX);
		List<MarketType> mtypeList = marketTypeDAO.getAllMarketType();
		Map<String, NewsTypeAndInfoDTO> map = newsDAO.getYYNewsInfo();
		Map<String, NewsTypeAndInfoDTO> otherMap = newsDAO.getAllNewsInfo();
		//最新团购商品
		List<GoodsBaseInfo> groupBuyGoodsList = goodsService.getLatestGroupBuyGoodsList();
		//最新商家信息
		List<Shop> shopList = shopService.getLatestShopList(); 
			
		ModelAndView mv = new ModelAndView("home/home/main");
		mv.addObject("marketList", mtypeList);
		mv.addObject("groupBuyGoodsList", groupBuyGoodsList);
		mv.addObject("shopList", shopList);
		mv.addObject("map", map);
		mv.addObject("otherMap", otherMap);
		return mv;
	}

	public ModelAndView baby(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为baby，即商品搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Baby);
		this.commend(request, PageConstants.BABY_HOME);
		return new ModelAndView("home/home/baby");
	}

	public ModelAndView lady(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为lady，即商品搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Lady);
		this.commend(request, PageConstants.LADY_HOME);
		return new ModelAndView("home/home/lady");
	}

	public ModelAndView goods(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为good，即商品搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Goods);
		this.commend(request, PageConstants.GOODS_INDEX);
		return new ModelAndView("home/home/goods");
	}

	public ModelAndView news(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为news，即资讯搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_News);
		this.commend(request, PageConstants.NEWS_INDEX);
		List<NewsType> typeList = newsTypeDAO.getAllNewsType();
		Map<String, NewsTypeAndInfoDTO> map = newsDAO.getAllNewsInfo();
		request.setAttribute("typeList", typeList);
		request.setAttribute("map", map);
		/*
		 * if (typeList != null && typeList.size() > 0) {
		 * request.setAttribute("typeList", typeList); for (NewsType type :
		 * typeList) { NewsTypeAndInfoDTO info =
		 * newsDAO.getNewsInfoByNewsType(type.getNewsType());
		 * request.setAttribute("type" + type.getNewsType(), info); } }
		 */
		return new ModelAndView("home/home/news");
	}

	public ModelAndView gis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为shop，即店铺搜索
		request.setAttribute("searchType", SearchTypeConstants.Search_Shop);
		this.commend(request, PageConstants.GIS_INDEX);
		return new ModelAndView("home/home/gis");
	}

	public ModelAndView group(HttpServletRequest request,
			HttpServletResponse respsonse) throws Exception {
		// 给search control用，用以指定搜索的类型，此处应为group，即团购信息
		request.setAttribute("searchType", SearchTypeConstants.Search_Group);
		this.commend(request, PageConstants.GROUP_PURCHASE_HOME);
		return new ModelAndView("home/home/group");
	}

	@SuppressWarnings("unchecked")
	private void commend(HttpServletRequest request, String commendPage) {
		List<String> codeList = commendContentDAO
				.getCommendCodesByPage(commendPage);
		if (codeList != null && codeList.size() > 0) {
			Map<String, List> map = commendContentDAO
					.getCommendContentsByPage(commendPage);
			for (String code : codeList) {
				request.setAttribute(code, map.get(code));
			}
		}
	}

	public void setCommendContentDAO(CommendContentDAO commendContentDAO) {
		this.commendContentDAO = commendContentDAO;
	}

	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}

	public MarketTypeDAO getMarketTypeDAO() {
		return marketTypeDAO;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

	public GoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
}
