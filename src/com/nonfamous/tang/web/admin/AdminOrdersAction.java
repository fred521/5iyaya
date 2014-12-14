package com.nonfamous.tang.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.web.common.Constants;

public class AdminOrdersAction extends MultiActionController {
	private TradeOrderService tradeOrderService;
	/**
	 * 根据查询条件显示所有的交易订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminOrders/adminOrdersList");

		RequestValueParse rvp = new RequestValueParse(request);
		
		OrderQuery query = new OrderQuery();

		String page = rvp.getParameter("currentPage").getString();
		// 关键字类型
		String keyType = rvp.getParameter("keyType").getString();
		// 关键字
		String keyWord = rvp.getParameter("keyWord").getString();
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);
		
		
		query.setCurrentPageString(page);
		/*if(StringUtils.equals(keyType, "status")){
			
		}
		else if(StringUtils.equals(keyType, "memberId")){
			
		}
		else if(StringUtils.equals(keyType, "type")){
			
		}
		*/
		//query.setSearchStatus(status);
		//query.setType(TradeOrder.TypeTrade);
		// query.setPageSize(5);
		query.setSearchStatus(-1);
		query = this.tradeOrderService.findOrders(query);
		request.setAttribute("list", query.getOrders());
		request.setAttribute("query", query);
		
		return mv;
	}
	public TradeOrderService getTradeOrderService() {
		return tradeOrderService;
	}
	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}
}
