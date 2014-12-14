/**
 * 
 */
package com.nonfamous.commom.util.web.cookyjar;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author eyeieye
 * 
 */
public class CookyjarInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(CookyjarInterceptor.class);

	private Map<String, CookieConfigure> clientName2CfgMap;

	private Map<String, CookieConfigure> name2CfgMap;

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, Object handler)
			throws Exception {
		Cookyjar ck = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		if (ck != null) {
			if (log.isDebugEnabled()) {
				log.debug("Cookyjar alread exist in the request.");
			}
			return true;
		}
		ck = new CookyjarImpl(request, response);
		request.setAttribute(Cookyjar.CookyjarInRequest, ck);
		if (log.isDebugEnabled()) {
			log.debug("CookyjarImpl instance put to the request.");
		}
		return true;
	}

	@Required
	public void setCookieConfigures(List<CookieConfigure> configures) {
		if (configures == null) {
			throw new NullPointerException("configures list can't be null.");
		}
		name2CfgMap = new HashMap<String, CookieConfigure>(configures.size());

		clientName2CfgMap = new HashMap<String, CookieConfigure>(configures
				.size());
		for (CookieConfigure cfg : configures) {
			if (cfg.getName() == null) {
				throw new NullPointerException(
						"CookieConfigure's name can't be null.");
			}
			name2CfgMap.put(cfg.getName(), cfg);
			if (cfg.getClientName() == null) {
				throw new NullPointerException(
						"CookieConfigure's client name can't be null.");
			}

			clientName2CfgMap.put(cfg.getClientName(), cfg);
		}
		name2CfgMap = Collections.unmodifiableMap(name2CfgMap);
		clientName2CfgMap = Collections.unmodifiableMap(clientName2CfgMap);
	}

	class CookyjarImpl implements Cookyjar {
		private HttpServletRequest request;

		private HttpServletResponse response;

		private Map<String, String> cookieMap;

		public CookyjarImpl(HttpServletRequest request,
				HttpServletResponse response) {
			super();
			this.request = request;
			this.response = response;
			cookieMap = initCookieMap();
		}

		private Map<String, String> initCookieMap() {
			Map<String, String> map = new HashMap<String, String>();
			Cookie[] cookies = this.request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie ck : cookies) {
					String name = ck.getName();
					CookieConfigure cfg = CookyjarInterceptor.this.clientName2CfgMap
							.get(name);
					if (cfg == null) {
						if (log.isDebugEnabled()) {
							log.debug("get a unknow cookie name[" + name
									+ "]value[" + ck.getValue() + "]");
						}
						continue;
					}
					String value = ck.getValue();
					value = cfg.getRealValue(value);
					map.put(cfg.getName(), value);
				}
			}
			return map;
		}

		public void clean() {
			Set<String> cookieMapKeys = new HashSet<String>(this.cookieMap
					.keySet());
			for (String key : cookieMapKeys) {
				this.remove(key);
			}
			if (log.isDebugEnabled()) {
				log.debug("remove all cookies end.");
			}
		}

		public String get(String key) {
			if (key == null) {
				throw new NullPointerException("cookie name key can't be null.");
			}
			return this.cookieMap.get(key);
		}

		public Map<String, String> getAll() {
			return new HashMap<String, String>(this.cookieMap);
		}

		public String remove(String key) {
			if (key == null) {
				throw new NullPointerException("cookie name key can't be null.");
			}
			String value = this.cookieMap.remove(key);
			CookieConfigure cfg = CookyjarInterceptor.this.name2CfgMap.get(key);
			if (cfg != null) {
				Cookie c = cfg.getDeleteCookie(this.request.getContextPath());
				this.response.addCookie(c);
				if (log.isDebugEnabled()) {
					log.debug("remove a cookies" + getCookieString(c));
				}
			}
			return value;
		}

		public void set(String key, String value) {
			if (key == null) {
				throw new NullPointerException("cookie name key can't be null.");
			}
			if (value == null) {
				remove(key);
				return;
			}
			CookieConfigure cfg = CookyjarInterceptor.this.name2CfgMap.get(key);
			if (cfg == null) {// 配过的才能写入
				if (log.isDebugEnabled()) {
					log.debug("set cookie name[" + key + "] value[" + value
							+ "],but this name not configured.");
				}
			} else {
				this.cookieMap.put(key, value);
				Cookie c = cfg.getCookie(value, this.request.getContextPath());
				this.response.addCookie(c);
				if (log.isDebugEnabled()) {
					log.debug("add a new cookie " + getCookieString(c));
				}
			}
		}

		public void set(String key, String value, int expiry) {
			if (key == null) {
				throw new NullPointerException("cookie name key can't be null.");
			}
			if (value == null) {
				remove(key);
				return;
			}
			CookieConfigure cfg = CookyjarInterceptor.this.name2CfgMap.get(key);
			if (cfg == null) {// 配过的才能写入
				if (log.isDebugEnabled()) {
					log.debug("set cookie name[" + key + "] value[" + value
							+ "],but this name not configured.");
				}
			} else {
				this.cookieMap.put(key, value);
				Cookie c = cfg.getCookie(value, this.request.getContextPath(),
						expiry);
				this.response.addCookie(c);
				if (log.isDebugEnabled()) {
					log.debug("add a new cookie " + getCookieString(c));
				}
			}
		}

		public void set(String key, Long value, int expiry) {
			this.set(key, value!=null?Long.toString(value):null, expiry);
		}

		public void set(String key, Long value) {
			this.set(key, value!=null?Long.toString(value):null);
		}
	}

	private static final String getCookieString(Cookie c) {
		if (c == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(c.toString());
		sb.append(" name[").append(c.getName()).append("] value[").append(
				c.getValue()).append("] domain[");
		sb.append(c.getDomain()).append("] path[").append(c.getPath()).append(
				"] maxAge[").append(c.getMaxAge());
		sb.append("]");
		return sb.toString();
	}

}
