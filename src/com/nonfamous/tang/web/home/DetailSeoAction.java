package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author fred
 * 
 */
public class DetailSeoAction implements Controller {

	private String idName = "id";// detail页面，id入参的名字

	private String detailURL;// 重定向的url，也就是真正的detail

	private String forward;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (this.forward == null) {
			initForward(request);
		}
		String urlPath = request.getRequestURI();
		String id = WebUtils.extractFilenameFromUrlPath(urlPath);
		request.setAttribute(idName, id);
		return new ModelAndView(this.forward);
	}

	private void initForward(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("forward:");
		if (!this.detailURL.startsWith("/")) {
			sb.append("/");
		}
		sb.append(detailURL);
		this.forward = sb.toString();
	}

	public String getDetailURL() {
		return detailURL;
	}

	public void setDetailURL(String detailURL) {
		this.detailURL = detailURL;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

}
