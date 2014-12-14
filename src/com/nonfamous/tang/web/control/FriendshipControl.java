package com.nonfamous.tang.web.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;
import com.nonfamous.tang.dao.home.URLDAO;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: FriendshipControl.java,v 1.1 2009/07/04 04:09:03 fred Exp $
 */
public class FriendshipControl extends AbstractController implements Controller {

	private URLDAO urlDAO;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("main_friendship");
		mv.addObject("urlList", urlDAO.getAllURLs());
		return mv;
	}

	public void setUrlDAO(URLDAO urlDAO) {
		this.urlDAO = urlDAO;
	}
}
