//===================================================================
// Created on Jun 9, 2007
//===================================================================
package com.nonfamous.tang.web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.nonfamous.tang.service.admin.CommendService;
import com.nonfamous.tang.web.common.PageConstants;

/**
 * <p>
 * 推荐内容管理
 * </p>
 * 
 * @author jacky
 * @version $Id: PositionControl.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public class PositionControl extends AbstractController {
	private CommendService commendService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/position");
		Map<String, List> map = new HashMap<String, List>();
		map.put(PageConstants.INDEX, commendService
				.getCommendPositionsByPage(PageConstants.INDEX));
		map.put(PageConstants.NEWS_INDEX, commendService
				.getCommendPositionsByPage(PageConstants.NEWS_INDEX));
		map.put(PageConstants.GOODS_INDEX, commendService
				.getCommendPositionsByPage(PageConstants.GOODS_INDEX));
		map.put(PageConstants.GIS_INDEX, commendService
				.getCommendPositionsByPage(PageConstants.GIS_INDEX));
		map.put(PageConstants.GOODS_SEARCH, commendService
				.getCommendPositionsByPage(PageConstants.GOODS_SEARCH));
		map.put(PageConstants.NEWS_SEARCH, commendService
				.getCommendPositionsByPage(PageConstants.NEWS_SEARCH));
		map.put(PageConstants.SHOP_SEARCH, commendService
				.getCommendPositionsByPage(PageConstants.SHOP_SEARCH));
		map.put(PageConstants.GROUP_SEARCH, commendService
				.getCommendPositionsByPage(PageConstants.GROUP_SEARCH));
		map.put(PageConstants.GROUP_PURCHASE_HOME, commendService
				.getCommendPositionsByPage(PageConstants.GROUP_PURCHASE_HOME));
		mv.addObject("posListMap", map);
		mv.addObject("all", commendService.getCommendPositionsByPage(null));
		return mv;
	}

	/**
	 * @param commendService
	 *            the commendService to set
	 */
	public void setCommendService(CommendService commendService) {
		this.commendService = commendService;
	}

}
