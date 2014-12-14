/**
 * 
 */
package com.nonfamous.tang.web.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.domain.MarketType;
import com.nonfamous.tang.domain.Shop;

/**
 * @author swordfish
 * 
 */
public class MarketShopControl extends AbstractController implements Controller {

	private MarketTypeDAO marketTypeDAO;

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("marketShopPage");
		List showMarket = new ArrayList();
		String belongMarketId = "";
		String belongMarketName = "";

		// 获得全部的店铺list
		List shopsInMarket = (List) marketTypeDAO.getAllShopByMarketId();
		Map marketShops = new HashMap();
		if (shopsInMarket != null && shopsInMarket.size() > 0) {
			Shop shop = (Shop) shopsInMarket.get(0);
			belongMarketId = shop.getBelongMarketId();
			MarketType m = (MarketType) marketTypeDAO.getMarketTypeById(shop
					.getBelongMarketId());
			if (m == null) {
				belongMarketName = "未知市场";
			} else {
				belongMarketName = m.getMarketName();
			}
		}

		for (int a = 0; a < shopsInMarket.size(); a++) {
			Shop shop = (Shop) shopsInMarket.get(a);
			if (belongMarketId.equals(shop.getBelongMarketId())) {
				showMarket.add(shop);
			} else {

				marketShops.put(belongMarketName, showMarket);
				belongMarketId = shop.getBelongMarketId();
				MarketType m = (MarketType) marketTypeDAO
						.getMarketTypeById(belongMarketId);
				if (m == null) {
					continue;
				}
				belongMarketName = m.getMarketName();
				showMarket = new ArrayList();
				showMarket.add(shop);
			}
		}
		marketShops.put(belongMarketName, showMarket);

		List orderMarket = (List) marketTypeDAO.getAllMarketType();
		List orderMarketShops = new ArrayList();
		for (int i = 0; i < orderMarket.size(); i++) {
			MarketType market = (MarketType) orderMarket.get(i);
			List shops = (List) marketShops.get(market.getMarketName());
			if (shops != null) {
				MarketShopDTO dto = new MarketShopDTO();
				dto.setMarketId(market.getMarketType());
				dto.setMarketName(market.getMarketName());
				dto.setMarketShop(shops);
				orderMarketShops.add(dto);
			}
		}

		mv.addObject("marketShops", orderMarketShops);
		return mv;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

	public class MarketShopDTO {
		private String marketId;

		private String marketName;

		private List marketShop;

		public String getMarketId() {
			return marketId;
		}

		public void setMarketId(String marketId) {
			this.marketId = marketId;
		}

		public String getMarketName() {
			return marketName;
		}

		public void setMarketName(String marketName) {
			this.marketName = marketName;
		}

		public List getMarketShop() {
			return marketShop;
		}

		public void setMarketShop(List marketShop) {
			this.marketShop = marketShop;
		}

	}

}
