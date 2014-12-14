/**
 * 
 */
package com.nonfamous.commom.util.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author hgs-…ÚË§
 * 
 */
public class NoSessionRequestWrapper extends HttpServletRequestWrapper {

	private static final Log log = LogFactory
			.getLog(NoSessionRequestWrapper.class);

	private HttpServletRequest request;

	public NoSessionRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public HttpSession getSession() {
		if (log.isDebugEnabled()) {
			log.debug("HttpServletRequest.getSession method is invoking in:"
					+ request.getRequestURI());
		}
		return null;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		if (log.isDebugEnabled()) {
			log.debug("HttpServletRequest.getSession method is invoking in:"
					+ request.getRequestURI());
		}
		return null;
	}

}
