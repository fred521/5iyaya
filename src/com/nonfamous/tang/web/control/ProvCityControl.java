package com.nonfamous.tang.web.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.commom.util.AddressHelper;
import com.nonfamous.commom.util.AddressInfo;

/**
 * <p>
 * 省、市、区选择control
 * </p>
 * 
 * @author:daodao
 * @version $Id: ProvCityControl.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public class ProvCityControl extends AbstractController implements Controller {
	private AddressHelper addressHelper;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<AddressInfo> provList = addressHelper.getProvList();
		//将省列表传到页面上
		request.setAttribute("provList",provList);
		ModelAndView mv = new ModelAndView("/home/member/provCity");
		return mv;
	}

	public void setAddressHelper(AddressHelper addressHelper) {
		this.addressHelper = addressHelper;
	}
}
