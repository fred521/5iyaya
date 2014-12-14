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
public class SearchSeoAction implements Controller {

	private String idName = "searchcate";// search页面，idType入参的名字

	private String searchURL;// 重定向的url，也就是真正的search result page
	
	private String type; //属于哪类的. good, shop, etc

	private String forward;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (this.forward == null) {
			initForward(request);
		}
		String urlPath = request.getRequestURI();
		String id = WebUtils.extractFilenameFromUrlPath(urlPath);
		request.setAttribute(idName, id);
		request.setAttribute("searchtype",type);
		return new ModelAndView(this.forward);
	}

	private void initForward(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("forward:");
		if (!this.searchURL.startsWith("/")) {
			sb.append("/");
		}
		sb.append(searchURL);
		this.forward = sb.toString();
	}


	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getSearchURL() {
		return searchURL;
	}

	public void setSearchURL(String searchURL) {
		this.searchURL = searchURL;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
