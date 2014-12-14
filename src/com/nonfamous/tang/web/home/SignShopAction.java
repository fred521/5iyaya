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
 *  ǩԼ����action
 * </p>
 * @author jacky
 * @version $Id: SignShopAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */

public class SignShopAction extends MultiActionController {

    private ShopService shopService;

    private SignShopDAO signShopDAO;

    /**
     * �ҵ�ǩԼ����
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
        SignShopQuery query = new SignShopQuery();
        query.setMemberId(memberId);
        String page = rvp.getParameter("page").getString();
        query.setCurrentPageString(page);
        //query.setPageSize(5);
        //���ﳵ
        query = this.signShopDAO.getMySignShopPaging(query);
        request.setAttribute("query", query);
        ModelAndView mv = new ModelAndView("/home/shop/signshopList");
        return mv;
    }

    /**
     * ���ǩԼ����
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addShop(HttpServletRequest request, HttpServletResponse response)
                                                                                         throws Exception {
        RequestValueParse rvp = new RequestValueParse(request);
        //�û�id
        String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
        //����id
        String shopId = rvp.getParameter("shopId").getString();
        //ȡ����������Ϣ��return
        if (StringUtils.isBlank(memberId) || StringUtils.isBlank(shopId)) {
            return this.list(request, response);
        }
        //���
        ShopResult result = this.shopService.addSignShop(shopId, memberId);
        request.setAttribute("result", result);
        return this.list(request, response);
    }

    /**
     * ɾ��ǩԼ����
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
        //ǩԼ����id
        String sid = rvp.getParameter("sid").getString();

        if (StringUtils.isBlank(sid)) {
            return this.list(request, response);
        }
        //ɾ��
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
