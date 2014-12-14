package com.nonfamous.tang.web.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.dao.home.SearchKeyWordDAO;
import com.nonfamous.tang.domain.SearchKeyWord;
/**
 * 
 * @author victor
 * 
 */
public class SearchControl extends AbstractController implements Controller {
    private static final int WORDSNUM=10;
	private GoodsCatDAO goodsCatDAO;
    private SearchKeyWordDAO searchKeyWordDAO;
    private NewsTypeDAO newsTypeDAO;
    private MarketTypeDAO marketTypeDAO;
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/search");
		mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
		mv.addObject("newsType", newsTypeDAO.getAllNewsType());
		mv.addObject("marketType", marketTypeDAO.getAllMarketType());
		mv.addObject("goodKeyWords",searchKeyWordDAO.getHotKeyWord(WORDSNUM,SearchKeyWord.KeyTypeGoods));
		mv.addObject("shopKeyWords",searchKeyWordDAO.getHotKeyWord(WORDSNUM,SearchKeyWord.KeyTypeShop));
		mv.addObject("memberKeyWords",searchKeyWordDAO.getHotKeyWord(WORDSNUM,SearchKeyWord.KeyTypeMember));
		mv.addObject("newsKeyWords",searchKeyWordDAO.getHotKeyWord(WORDSNUM,SearchKeyWord.KeyTypeNews));
		return mv;
	}

	public void setGoodsCatDAO(GoodsCatDAO goodsCatDAO) {
		this.goodsCatDAO = goodsCatDAO;
	}

	public void setSearchKeyWordDAO(SearchKeyWordDAO searchKeyWordDAO) {
		this.searchKeyWordDAO = searchKeyWordDAO;
	}

	public void setNewsTypeDAO(NewsTypeDAO newsTypeDAO) {
		this.newsTypeDAO = newsTypeDAO;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}
	
}
