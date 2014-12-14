package com.nonfamous.tang.web.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.dao.home.GoodsCatDAO;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: GoodsCatControl.java,v 1.1 2008/07/11 00:46:42 fred Exp $
 */
public class GoodsCatControl extends AbstractController implements Controller {

	private GoodsCatDAO goodsCatDAO;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("goodsCatPage");
		request.getAttribute("catType");
		mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
		return mv;
	}

	public void setGoodsCatDAO(GoodsCatDAO goodsCatDAO) {
		this.goodsCatDAO = goodsCatDAO;
	}

}
