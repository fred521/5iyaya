/**
 * 
 */
package com.nonfamous.commom.util.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hgs-…ÚË§
 * 
 */
public class NoSessionFilter implements Filter {

	public void init(FilterConfig cfg) throws ServletException {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		NoSessionRequestWrapper wrapper = new NoSessionRequestWrapper(
				httpRequest);
		chain.doFilter(wrapper, response);

	}

}
