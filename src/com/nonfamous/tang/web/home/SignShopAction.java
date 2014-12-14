//===================================================================
// Created on Sep 20, 2007
//===================================================================
package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.home.SignShopDAO;
import com.nonfamous.tang.dao.query.SignShopQuery;
import com.nonfamous.tang.domain.result.ShopResult;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.web.common.Constants;

/**
 * <p>
 *  签约店铺action
 * </p>
 * @author jacky
 * @version $Id: SignShopAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */

public class SignShopAction extends MultiActionController {

    private ShopService shopService;

    private SignShopDAO signShopDAO;

    /**
     * 我的签约店铺
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
                                                                                      throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //用户id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        request.setAttribute("memberId", memberId);
        SignShopQuery query = new SignShopQuery();
        query.setMemberId(memberId);
        String page = rvp.getParameter("page").getString();
        query.setCurrentPageString(page);
        //query.setPageSize(5);
        //购物车
        query = this.signShopDAO.getMySignShopPaging(query);
        request.setAttribute("query", query);
        ModelAndView mv = new ModelAndView("/home/shop/signshopList");
        return mv;
    }

    /**
     * 添加签约店铺
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addShop(HttpServletRequest request, HttpServletResponse response)
                                                                                         throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //用户id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        //店铺id
        String shopId = rvp.getParameter("shopId").getString();
        //取不到店铺信息，return
        if (StringUtils.isBlank(memberId) || StringUtils.isBlank(shopId)) {
            return this.list(request, response);
        }
        //添加
        ShopResult result = this.shopService.addSignShop(shopId, memberId);
        request.setAttribute("result", result);
        return this.list(request, response);
    }

    /**
     * 删除签约店铺
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //用户id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        //签约店铺id
        String sid = rvp.getParameter("sid").getString();

        if (StringUtils.isBlank(sid)) {
            return this.list(request, response);
        }
        //删除
        this.signShopDAO.delete(sid, memberId);
        request.setAttribute("result", "delSuccess");
        return this.list(request, response);
    }

    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setSignShopDAO(SignShopDAO signShopDAO) {
        this.signShopDAO = signShopDAO;
    }

}
