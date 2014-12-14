/**
 * 
 */
package com.nonfamous.commom.util.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.view.context.ViewContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author eyeieye
 * 
 */
public abstract class SpringWebContextAware {
	protected static final Log LOG = LogFactory
			.getLog(SpringWebContextAware.class);

	protected ViewContext viewContext;

	protected ApplicationContext springWebApplicationContext;

	public SpringWebContextAware() {
		super();
	}

	public void init(Object obj) {
		if (!(obj instanceof ViewContext)) {
			throw new IllegalArgumentException(
					"Tool can only be initialized with a ViewContext");
		}
		viewContext = (ViewContext) obj;
		springWebApplicationContext = (ApplicationContext) viewContext
				.getRequest().getAttribute(
						DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}
}
