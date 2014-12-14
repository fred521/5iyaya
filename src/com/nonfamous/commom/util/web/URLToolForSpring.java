package com.nonfamous.commom.util.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author eyeieye
 * 
 */
public class URLToolForSpring extends SpringWebContextAware {
	protected static final Log logger = LogFactory
			.getLog(URLToolForSpring.class);

	private String requestCharacterEncoding = "UTF-8";

	private static final String ConfigBeanName = "configBeanName";

	private String beanName = null;

	// 不以"/" 结尾
	private StringBuffer server = new StringBuffer();

	public void init(Object obj) {
		super.init(obj);
		HttpServletRequest request = viewContext.getRequest();
		if (StringUtils.isNotBlank(request.getCharacterEncoding())) {
			requestCharacterEncoding = request.getCharacterEncoding();
		}
		URLConfig config = (URLConfig) this.springWebApplicationContext
				.getBean(beanName);
		this.server.append(config.getURL());
		if (config.isContextPathSensitive()) {
			server.append(request.getContextPath());
		}
		if (server.charAt(server.length() - 1) == '/') {
			server.deleteCharAt(server.length() - 1);
		}

		if (StringUtils.isNotBlank(config.getPath())) {
			if (config.getPath().startsWith("/")) {
				server.append(config.getPath());
			} else {
				server.append('/').append(config.getPath());
			}
		}

		if (server.charAt(server.length() - 1) == '/') {
			server.deleteCharAt(server.length() - 1);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("URLToolForSpring init,server:" + this.server);
		}
	}

	/**
	 * 自动获取配置参数
	 * 
	 * @param params
	 */
	public void configure(Map params) {
		beanName = (String) params.get(ConfigBeanName);

	}

	public QueryData setTarget(String target) {
		return new QueryData(target);
	}

	@Override
	public String toString() {
		return this.server.toString();
	}

	public class QueryData {
		private StringBuffer query;

		QueryData(String target) {
			if (target == null) {
				return;
			}
			query = new StringBuffer();
			query.append(URLToolForSpring.this.server);
			if (target.startsWith("/")) {
				query.append(target);
			} else {
				query.append('/').append(target);
			}
			query.append('?');
		}

		public QueryData addQueryData(String id, String value) {
			query.append(id).append('=');
			try {
				query.append(URLEncoder.encode(value,
						URLToolForSpring.this.requestCharacterEncoding));
			} catch (UnsupportedEncodingException e) {
				if (logger.isErrorEnabled()) {
					logger
							.error(
									"UnsupportedEncoding:"
											+ URLToolForSpring.this.requestCharacterEncoding,
									e);
				}
			}
			query.append('&');
			return this;
		}

		public QueryData addQueryData(String id, long value) {
			return addQueryData(id, String.valueOf(value));
		}

		public QueryData addQueryData(String id, Object value) {
			return addQueryData(id, String.valueOf(value));
		}

		public QueryData addQueryData(String id, double value) {
			return addQueryData(id, String.valueOf(value));
		}

		public QueryData addQueryData(String id, int value) {
			return addQueryData(id, String.valueOf(value));
		}

		@Override
		public String toString() {
			if (this.query == null) {
				return URLToolForSpring.this.toString();
			}
			String s = this.query.toString();
			if (s.endsWith("?") || s.endsWith("&")) {
				s = s.substring(0, s.length() - 1);
			}
			return s;
		}

	}
}
