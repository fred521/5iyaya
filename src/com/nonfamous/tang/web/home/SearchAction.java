package com.nonfamous.tang.web.home;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
import com.nonfamous.tang.dao.query.SearchMemberShopQuery;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.SearchKeyWord;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.service.search.GoodsIndexService;
import com.nonfamous.tang.service.search.KeyWordService;
import com.nonfamous.tang.service.search.MemberShopIndexService;
import com.nonfamous.tang.service.search.NewsIndexService;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.PageConstants;
import com.nonfamous.tang.web.common.SearchTypeConstants;

/**
 * 
 * @author victor
 * 
 */
public class SearchAction extends MultiActionController {

	private GoodsIndexService goodsIndexService;

	private NewsIndexService newsIndexService;

	private MemberShopIndexService memberShopIndexService;

	private KeyWordService keyWordService;

	private MarketTypeDAO marketTypeDAO;

	private CommendContentDAO commendContentDAO;

	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String searchType = rvp.getParameterOrAttribute("searchtype")
				.getString();
		// 推荐信息

		if (searchType.equals(SearchTypeConstants.Search_Goods)) {
			this.commend(request, PageConstants.GOODS_SEARCH);
			return goodslist(request, response);
		} else if (searchType.equals(SearchTypeConstants.Search_News)) {
			this.commend(request, PageConstants.NEWS_SEARCH);
			return newslist(request, response);
		}
		else if (searchType.equals(SearchTypeConstants.Search_YY_News)) {
			this.commend(request, PageConstants.YY_NEWS_SEARCH);
			return yynewslist(request, response);
		}
		else if (searchType.equals(SearchTypeConstants.Search_Group)) {
			this.commend(request, PageConstants.GROUP_SEARCH);
			return grouplist(request, response);
		}
		else if (searchType.equals(SearchTypeConstants.Search_Helper)) {
			return helperlist(request, response);
		}
		else {
			this.commend(request, PageConstants.SHOP_SEARCH);
			return shoplist(request, response);
		}
	}

	private ModelAndView helperlist(HttpServletRequest request,
			HttpServletResponse response) {
		RequestValueParse rvp = new RequestValueParse(request);
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String newsType = rvp.getParameterOrAttribute("searchcate").getString();
		String page = rvp.getParameter("page").getString();
		String sort = rvp.getParameter("sort").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		SearchNewsQuery query = new SearchNewsQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setNewsType(newsType);
		query.setSort(sort);
		query.setReverse(reverse);
		query = newsIndexService.findNews(query);
		if (query.getKeyWordList() != null)
			keyWordService.keyWordHit(query.getKeyWordList(),
					SearchKeyWord.KeyTypeNews);
		request.setAttribute("query", query);
		request.setAttribute("news", query.getSearchNewsList());
		request.setAttribute("searchType", "news");
		// 标题
		StringBuilder sb = new StringBuilder("信息列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		ModelAndView mv = new ModelAndView("home/search/helperList");
		return null;
	}

	private ModelAndView grouplist(HttpServletRequest request,
			HttpServletResponse response) {
		RequestValueParse rvp = new RequestValueParse(request);
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String goodsCat = rvp.getParameterOrAttribute("searchcate").getString();
		String sort = rvp.getParameter("sort").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		String marketType = rvp.getParameter("marketType").getString();
		String page = rvp.getParameter("page").getString();
		SearchGoodsQuery query = new SearchGoodsQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setGoodsCat(goodsCat);
		query.setSort(sort);
		query.setReverse(reverse);
		query.setMarketType(marketType);
		query.setGoodsType("T");//search Team buy goods
		query = (SearchGoodsQuery) goodsIndexService.findGoods(query);
		if (query.getKeyWordList() != null)
			keyWordService.keyWordHit(query.getKeyWordList(),
					SearchKeyWord.KeyTypeGoods);
		ModelAndView mv = new ModelAndView("home/search/groupList");
		mv.addObject("goods", query.getGoodsList());
		Object[] cats = query.getSearchGoodsCatList().toArray();
		Arrays.sort(cats);
		mv.addObject("cats", cats);
		mv.addObject("subcatnum", cats.length);
		mv.addObject("total", query.getTotalItem());
		request.setAttribute("query", query);
		request.setAttribute("searchType", "group");
		request.setAttribute("marketType", marketTypeDAO.getAllMarketType());
		// 标题
		StringBuilder sb = new StringBuilder("商品列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		return mv;
	}

	public ModelAndView rebuild(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("home/search/rebuild");
		RequestValueParse rvp = new RequestValueParse(request);
		String type = rvp.getParameterOrAttribute("type").getString();
		long totaltime;
		if (StringUtils.isBlank(type)) {
			request.setAttribute("result", "type参数为空！");
			return mv;
		}
		try {
			Date begin = new Date();
			if (type.equalsIgnoreCase("goods"))
				this.goodsIndexService.rebuildGoodsIndex();
			else if (type.equalsIgnoreCase("shop"))
				this.memberShopIndexService.rebuildMemberShopIndex();
			else if (type.equalsIgnoreCase("news"))
				this.newsIndexService.rebuildNewsIndex();
			else if (type.equalsIgnoreCase("all")) {
				this.goodsIndexService.rebuildGoodsIndex();
				this.memberShopIndexService.rebuildMemberShopIndex();
				this.newsIndexService.rebuildNewsIndex();
			}
			Date end = new Date();
			totaltime = end.getTime() - begin.getTime();
		} catch (Exception e) {
			request.setAttribute("result", "索引重建出错" + e.getMessage());
			return mv;
		}
		request.setAttribute("result", "索引重建成功！");
		request.setAttribute("time", totaltime);
		return mv;
	}

	private ModelAndView shoplist(HttpServletRequest request,
			HttpServletResponse response) {
		RequestValueParse rvp = new RequestValueParse(request);
		String searchType = rvp.getParameterOrAttribute("searchtype").getString();
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String marketType = rvp.getParameterOrAttribute("marketType").getString();
		String page = rvp.getParameter("page").getString();
		String sort = rvp.getParameter("sort").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		SearchMemberShopQuery query = new SearchMemberShopQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setMarketType(marketType);
		query.setSort(sort);
		query.setReverse(reverse);
		if (searchType != null && searchType.equals("shop"))
			query.setSearchType(SearchMemberShopQuery.SHOP);
		else
			query.setSearchType(SearchMemberShopQuery.MEMBER);
		query = memberShopIndexService.findMemberShop(query);
		if (query.getKeyWordList() != null) {
			if (searchType != null && searchType.equals("shop"))
				keyWordService.keyWordHit(query.getKeyWordList(),
						SearchKeyWord.KeyTypeShop);
			else
				keyWordService.keyWordHit(query.getKeyWordList(),
						SearchKeyWord.KeyTypeMember);
		}
		request.setAttribute("query", query);
		request.setAttribute("searchType", searchType);
		request.setAttribute("marketType", marketTypeDAO.getAllMarketType());
		// 标题
		StringBuilder sb = new StringBuilder("店铺列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		ModelAndView mv = null ; 
		
		if( query.getShopList().size() == 1 ){
			Shop shop = query.getShopList().get(0) ;			
			mv = new ModelAndView( new RedirectView( request.getContextPath() + "/shop/" + shop.getShopId( ) + ".htm" ) ) ;
		} else {
			mv = new ModelAndView("home/search/shopList") ;
		}		
		
		return mv;
	}

	private ModelAndView newslist(HttpServletRequest request,
			HttpServletResponse response) {
		RequestValueParse rvp = new RequestValueParse(request);
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String newsType = rvp.getParameterOrAttribute("searchcate").getString();
		String page = rvp.getParameter("page").getString();
		String sort = rvp.getParameter("sort").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		SearchNewsQuery query = new SearchNewsQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setNewsType(newsType);
		query.setSort(sort);
		query.setReverse(reverse);
		query = newsIndexService.findNews(query);
		if (query.getKeyWordList() != null)
			keyWordService.keyWordHit(query.getKeyWordList(),
					SearchKeyWord.KeyTypeNews);
		request.setAttribute("query", query);
		request.setAttribute("news", query.getSearchNewsList());
		request.setAttribute("searchType", "news");
		// 标题
		StringBuilder sb = new StringBuilder("信息列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		ModelAndView mv = new ModelAndView("home/search/newsList");
		return mv;
	}
	private ModelAndView yynewslist(HttpServletRequest request,
			HttpServletResponse response) {
		RequestValueParse rvp = new RequestValueParse(request);
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String newsType = rvp.getParameterOrAttribute("searchcate").getString();
		String page = rvp.getParameter("page").getString();
		String sort = rvp.getParameter("sort").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		SearchNewsQuery query = new SearchNewsQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setNewsType(newsType);
		query.setSort(sort);
		query.setReverse(reverse);
		query = newsIndexService.findNews(query);
		if (query.getKeyWordList() != null)
			keyWordService.keyWordHit(query.getKeyWordList(),
					SearchKeyWord.KeyTypeNews);
		request.setAttribute("query", query);
		request.setAttribute("news", query.getSearchNewsList());
		request.setAttribute("searchType", "news");
		// 标题
		StringBuilder sb = new StringBuilder("信息列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		ModelAndView mv = new ModelAndView("home/search/newsList");
		return mv;
	}

	@SuppressWarnings("unchecked")
	private ModelAndView goodslist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String keywords = rvp.getParameterOrAttribute("keywords").getString();
		String goodsCat = rvp.getParameterOrAttribute("searchcate").getString();
		String sort = rvp.getParameter("sort").getString();
		//String goodsType=rvp.getParameter("type").getString();
		boolean reverse = Boolean.parseBoolean(rvp.getParameter("reverse")
				.getString());
		String marketType = rvp.getParameter("marketType").getString();
		String page = rvp.getParameter("page").getString();
		SearchGoodsQuery query = new SearchGoodsQuery();
		query.setCurrentPageString(page);
		query.setKeyWords(keywords);
		query.setGoodsCat(goodsCat);
		query.setSort(sort);
		query.setReverse(reverse);
		query.setMarketType(marketType);
		query.setGoodsType("N");//search normal batch goods
		query = (SearchGoodsQuery) goodsIndexService.findGoods(query);
		if (query.getKeyWordList() != null)
			keyWordService.keyWordHit(query.getKeyWordList(),
					SearchKeyWord.KeyTypeGoods);
		ModelAndView mv = new ModelAndView("home/search/goodsList");
		mv.addObject("goods", query.getGoodsList());
		Object[] cats = query.getSearchGoodsCatList().toArray();
		Arrays.sort(cats);
		mv.addObject("cats", cats);
		mv.addObject("subcatnum", cats.length);
		mv.addObject("total", query.getTotalItem());
		request.setAttribute("query", query);
		request.setAttribute("searchType", "good");
		request.setAttribute("marketType", marketTypeDAO.getAllMarketType());
		// 标题
		StringBuilder sb = new StringBuilder("商品列表");
		if (StringUtils.isNotBlank(query.getKeyWords())) {
			sb.append(" 搜索 ").append(HtmlUtils.parseHtml(query.getKeyWords()));
		}
		request.setAttribute(Constants.PageTitle_Page, sb);
		return mv;
	}

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

	public void setGoodsIndexService(GoodsIndexService goodsIndexService) {
		this.goodsIndexService = goodsIndexService;
	}

	public void setKeyWordService(KeyWordService keyWordService) {
		this.keyWordService = keyWordService;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

	public void setMemberShopIndexService(
			MemberShopIndexService memberShopIndexService) {
		this.memberShopIndexService = memberShopIndexService;
	}

	public void setNewsIndexService(NewsIndexService newsIndexService) {
		this.newsIndexService = newsIndexService;
	}

	public void setCommendContentDAO(CommendContentDAO commendContentDAO) {
		this.commendContentDAO = commendContentDAO;
	}

}
