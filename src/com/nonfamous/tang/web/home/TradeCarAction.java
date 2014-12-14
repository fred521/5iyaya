//===================================================================
// Created on 2007-9-18
//===================================================================
package com.nonfamous.tang.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.TradeCarQuery;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.result.TradeResult;
import com.nonfamous.tang.domain.trade.TradeCarItem;
import com.nonfamous.tang.domain.trade.TradeCarShopExt;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.TradeCarService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 *  ���ﳵaction
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarAction.java,v 1.2 2009/01/06 07:51:19 jason Exp $
 */

public class TradeCarAction extends MultiActionController {

    private TradeCarService tradeCarService;
    
    private MemberService memberService;
    
    /**
     * �ҵĹ��ﳵ
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
                                                                                      throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //�û�id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        request.setAttribute("memberId", memberId);
        TradeCarQuery query = new TradeCarQuery();
        query.setOwner(memberId);
        String page = rvp.getParameter("page").getString();
        query.setCurrentPageString(page);
        //query.setPageSize(5);
        //���ﳵ
        query = this.tradeCarService.getMyTradeCarPaging(query);
        request.setAttribute("query", query);
        ModelAndView mv = new ModelAndView("/home/trade/beorderList");
        return mv;
    }

    /**
     * �����Ʒ�����ﳵ
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addCar(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //�û�id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        //��Ʒid
        String goodsId = rvp.getParameter("gid").getString();
        //ȡ������Ʒ��Ϣ��return
        if (StringUtils.isBlank(goodsId)) {
            return this.list(request, response);
        }
        //�����Ʒ�����ﳵ��
        TradeResult result = this.tradeCarService.addGoodsToCar(goodsId, memberId);
        request.setAttribute("result", result);
        return this.list(request, response);
    }

    /**
     * ɾ�����ﳵ�����Ʒ
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //�û�id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        //���ﳵid
        String id = rvp.getParameter("id").getString();

        if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) {
            return this.list(request, response);
        }
        //�ӹ��ﳵ��ɾ����Ʒ
        this.tradeCarService.deleteGoodsFromCar(Long.valueOf(id), memberId);
        request.setAttribute("result", "delSuccess");
        return this.list(request, response);
    }

    /**
     * ѡ����Ʒ
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView select(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //�û�id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        
    	if(!checkIfHasFullMemberInfo(memberId)){
    		ModelAndView mv = new ModelAndView("/home/home/regStep2");
    		mv.addObject("gotoUrl", "/tradecar/select.htm");
    		return mv;
    	}
    	
    	ModelAndView mv = new ModelAndView("/home/trade/beorderSelect");

        //���ﳵ���������б�
        List<TradeCarShopExt> shopList = this.tradeCarService.getTradecarshopList(memberId);
        if (shopList == null || shopList.size() == 0) {
            return mv;
        }
        String shopId = rvp.getParameter("sid").getString();
        if (StringUtils.isBlank(shopId)) {
            shopId = shopList.get(0).getShopId();
        }
        //������Ʒ�б�
        List<TradeCarItem> tcList = this.tradeCarService.getGoodsByShopIdAndOwner(shopId, memberId);

        request.setAttribute("shopList", shopList);
        request.setAttribute("tcList", tcList);
        request.setAttribute("shopId", shopId);

        return mv;
    }

	
    private boolean checkIfHasFullMemberInfo(String memberId){
    	Member member = this.memberService.getMemberById(memberId);
    	if(Constants.Default_Member_Info.equals(member.getName())){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public void setTradeCarService(TradeCarService tradeCarService) {
        this.tradeCarService = tradeCarService;
    }
    
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
