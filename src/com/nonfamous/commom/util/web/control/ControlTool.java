package com.nonfamous.commom.util.web.control;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.commom.util.profiler.TemplateWithProfile;
import com.nonfamous.commom.util.web.SpringWebContextAware;

/**
 * @author fred
 * 
 */
public class ControlTool extends SpringWebContextAware {

	public static final String ConfigureBeanName = "controlToolConfigure";

	private static final Log log = LogFactory.getLog(ControlTool.class);

	private Context velocityContext;

	private VelocityEngine velocityEngine;

	private ControlToolConfigure cfg;

	private HttpServletRequest request;

	private HttpServletResponse response;

	public ControlTool() {
		super();
		if (log.isDebugEnabled()) {
			log.debug("ControlTool constructor");
		}
	}

	public void init(Object obj) {
		super.init(obj);
		this.request = viewContext.getRequest();
		this.response = viewContext.getResponse();
		this.velocityContext = viewContext.getVelocityContext();
		this.velocityEngine = viewContext.getVelocityEngine();
		ControlToolConfigure cc = (ControlToolConfigure) this.springWebApplicationContext
				.getBean(ConfigureBeanName);
		if (cc != null) {
			cfg = cc;
		} else {
			cfg = new ControlToolConfigure();
		}

		if (log.isDebugEnabled()) {
			log.debug("ControlTool init finish.");
		}
	}

	public ControlRender setTemplate(String controlName) {
		if (controlName == null) {
			throw new NullPointerException(
					"control template name can't be null.");
		}
		return new ControlRender(controlName);
	}

	public ControlRender get(String controlName) {
		return setTemplate(controlName);
	}

	public class ControlRender {
		private String controlName;

		private Map<String, Object> parameters;

		private ControlRender(String controlName) {
			this.controlName = controlName;
		}

		/**
		 * 为contro增加参数值对,注意,在共享context的情况下,同时会写入到共享的context中
		 */
		public ControlRender put(String key, Object value) {
			if (this.parameters == null) {
				this.parameters = new HashMap<String, Object>();
			}
			this.parameters.put(key, value);
			request.setAttribute(key, value);
			return this;
		}

		public ControlRender setParameter(String key, Object value) {
			return this.put(key, value);
		}

		@Override
		public String toString() {
			if (this.controlName == null) {
				return "ControlRender@" + System.identityHashCode(this);
			}
			Context renderContext = buildContext();

			ModelAndView mv = getModelAndView();

			return doRender(renderContext, mv);
		}

		private String doRender(Context renderContext, ModelAndView mv) {
			String realControlName = this.controlName;
			if (mv != null && !mv.isEmpty() && mv.hasView()) {
				realControlName = mv.getViewName();
			}
			if (log.isDebugEnabled()) {
				log.debug("real control name is:" + realControlName);
			}
			if (mv != null) {
				Map model = mv.getModel();
				for (Iterator it = model.keySet().iterator(); it.hasNext();) {
					String key = (String) it.next();
					Object value = model.get(key);
					renderContext.put(key, value);
				}
			}
			StringWriter sw = new StringWriter();
			try {
				String controlTemplateName = ControlTool.this.cfg
						.getControlTemplate(realControlName);
				Template t = getTemplate(controlTemplateName);
				t.merge(renderContext, sw);
				return sw.toString();
			} catch (ResourceNotFoundException e) {
				log.error("ControlTool mergeTemplate error", e);
				throw new RuntimeException(e);
			} catch (ParseErrorException e) {
				log.error("ControlTool mergeTemplate error", e);
				throw new RuntimeException(e);
			} catch (MethodInvocationException e) {
				log.error("ControlTool mergeTemplate error", e);
				throw new RuntimeException(e);
			} catch (Exception e) {
				log.error("ControlTool mergeTemplate error", e);
				throw new RuntimeException(e);
			}
		}

		private Template getTemplate(String controlTemplateName)
				throws Exception {
			return new TemplateWithProfile(ControlTool.this.velocityEngine
					.getTemplate(controlTemplateName));
		}

		private ModelAndView getModelAndView() {
			// do java control
			if (!ControlTool.this.springWebApplicationContext
					.containsBean(this.controlName)) {
				if (log.isDebugEnabled()) {
					log.debug("control with name:" + this.controlName
							+ " not find in application context.");
				}
				return null;
			}

			Object handle = ControlTool.this.springWebApplicationContext
					.getBean(this.controlName);
			if (!(handle instanceof Controller)) {
				if (log.isDebugEnabled()) {
					log.debug("control with name:" + this.controlName
							+ " finded,but not a instanceof "
							+ Controller.class.getName());
				}
				return null;
			}
			
			ModelAndView mv = null;
			Controller con = (Controller) handle;
			try {
				if (log.isDebugEnabled()) {
					log.debug("control with name:" + this.controlName
							+ " finded,so invoke it.");
				}
				mv = con.handleRequest(ControlTool.this.request,
						new ReadOnlyHttpResponseWrapper(
								ControlTool.this.response) {

						});
			} catch (Exception e) {
				log.error("Controller.handleRequest error ", e);
				if(ControlTool.this.cfg.isThrowsException()){
					throw new RuntimeException(e);
				}
			}
			return mv;

		}

		private Context buildContext() {
			Context renderContext;
			if (ControlTool.this.cfg.isSameContext()) {
				renderContext = ControlTool.this.velocityContext;
			} else {
				renderContext = new VelocityContext(
						ControlTool.this.velocityContext);
			}
			if (this.parameters != null) {
				for (Entry<String, Object> entry : this.parameters.entrySet()) {
					renderContext.put(entry.getKey(), entry.getValue());
				}
			}
			return renderContext;
		}
	}

	private class ReadOnlyHttpResponseWrapper extends
			HttpServletResponseWrapper {

		public ReadOnlyHttpResponseWrapper(HttpServletResponse hsr) {
			super(hsr);
		}

		@Override
		public void addDateHeader(String arg0, long arg1) {
			super.addDateHeader(arg0, arg1);
		}

		@Override
		public void addHeader(String arg0, String arg1) {
			super.addHeader(arg0, arg1);
		}

		@Override
		public void addIntHeader(String arg0, int arg1) {
			super.addIntHeader(arg0, arg1);
		}

		@Override
		public void sendError(int arg0, String arg1) throws IOException {
			super.sendError(arg0, arg1);
		}

		@Override
		public void sendError(int arg0) throws IOException {
			super.sendError(arg0);
		}

		@Override
		public void sendRedirect(String arg0) throws IOException {
			super.sendRedirect(arg0);
		}

		@Override
		public void setDateHeader(String arg0, long arg1) {
			super.setDateHeader(arg0, arg1);
		}

		@Override
		public void setHeader(String arg0, String arg1) {
			super.setHeader(arg0, arg1);
		}

		@Override
		public void setIntHeader(String arg0, int arg1) {
			super.setIntHeader(arg0, arg1);
		}

		@Override
		public void setStatus(int arg0, String arg1) {
			super.setStatus(arg0, arg1);
		}

		@Override
		public void setStatus(int arg0) {
			super.setStatus(arg0);
		}

		@Override
		public void setBufferSize(int arg0) {
			super.setBufferSize(arg0);
		}

		@Override
		public void setCharacterEncoding(String arg0) {
			super.setCharacterEncoding(arg0);
		}

		@Override
		public void setContentLength(int arg0) {
			super.setContentLength(arg0);
		}

		@Override
		public void setContentType(String arg0) {
			super.setContentType(arg0);
		}

		@Override
		public void setLocale(Locale arg0) {
			super.setLocale(arg0);
		}

	}

}
