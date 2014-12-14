package com.nonfamous.tang.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.service.home.TradeCarService;


/**
 * 购物车后台查询系统
 * 
 */
public class TradeCarAction extends MultiActionController {
	
	private TradeCarService tradeCarService;
    
    /**
     * 查询指定用户采购单列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	RequestValueParse rvp = new RequestValueParse(request);
        //用户id
        String memberId = rvp.getParameter("memberId").getString();
        TradeCarQuery query = new TradeCarQuery();
        query.setOwner(memberId);
        String page = rvp.getParameter("page").getString();
        query.setCurrentPageString(page);
        //query.setPageSize(5);
        //购物车
        query = this.tradeCarService.getMyTradeCarPaging(query);
        request.setAttribute("query", query);
        ModelAndView mv = new ModelAndView("/admin/trade/beorderList");
        return mv;
    }
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	RequestValueParse rvp = new RequestValueParse(request);
        //用户id
        String memberId = rvp.getParameter("memberId").getString();
        TradeCarQuery query = new TradeCarQuery();
        query.setOwner(memberId);
        String page = rvp.getParameter("page").getString();
        query.setCurrentPageString(page);
        //query.setPageSize(5);
        //购物车
        query = this.tradeCarService.getMyTradeCarPaging(query);
        request.setAttribute("query", query);
        ModelAndView mv = new ModelAndView("/admin/trade/beorderList");
        return mv;
    }
    public void setTradeCarService(TradeCarService tradeCarService) {
        this.tradeCarService = tradeCarService;
    }
    
}
