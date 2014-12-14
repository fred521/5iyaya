package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.service.home.GoodsCatalogService;

/**
 * The class handles the cascade select component request and return the json to client.
 * 
 * @author frank.liu
 *
 */
public class ListCascadeCategoryController implements Controller{

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private GoodsCatalogService goodsCatalogService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<GoodsCat> goodsCats = null;
		String typeId = ServletRequestUtils.getStringParameter(request, "typeId");
		if(StringUtils.isNotBlank(typeId)){
			if(logger.isInfoEnabled()){
				logger.info("List goods categories by typeId: " + typeId);
			}
			if(!StringUtils.equals(typeId, "-1")){
				GoodsCat goodsCat = goodsCatalogService.getGoodsCatById(typeId);
				goodsCats = goodsCat.getChildren();
			}
		}
		else{
			goodsCats = goodsCatalogService.getRootGoodsCat();
			if(logger.isInfoEnabled()){
				logger.info("List root goods categories.");
			}
		}
		
		if(goodsCats == null){
			goodsCats = new ArrayList<GoodsCat>();
		}
		ModelAndView mv = new ModelAndView("home/goods/cascadeCategorySelect");
		mv.addObject("goodsCats", goodsCats);
		return mv;
	}

	public GoodsCatalogService getGoodsCatalogService() {
		return goodsCatalogService;
	}

	public void setGoodsCatalogService(GoodsCatalogService goodsCatalogService) {
		this.goodsCatalogService = goodsCatalogService;
	}

}
