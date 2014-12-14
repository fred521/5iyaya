/**
 * 
 */
package com.nonfamous.commom.util.web.cookyjar;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.RandomStringUtils;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author eyeieye
 * 
 */
public class CookieConfigure {
	private String name;

	private String clientName;

	private String path;

	private int lifeTime = -1;

	// �Ƿ���м���
	private boolean isEncrypt;

	// �ַ����룬ȱʡΪutf8
	private String encoding = "UTF-8";

	// ��ֵǰ�����Ӷ���������֣���� <= 0 ,���ʾ������
	private int randomChar = 0;

	private String domain;

	// ���ͻ��˵�cookieֵ�������
	public String getRealValue(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		try {
			String back = value;
			if (this.isEncrypt) {
				back = Cryptography.getInstance().dectypt(back);
			}
			if (back == null) {
				return null;
			}
			if (this.randomChar > 0) {
				if (back.length() < this.randomChar) {
					return null;
				}
				back = back.substring(this.randomChar);
			}
			back = URLDecoder.decode(back, encoding);
			return back;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	// ����ʵֵ����ɿͻ���cookie�洢ֵ
	public String getClientValue(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		try {
			String back = value;
			back = URLEncoder.encode(back, encoding);
			if (back == null) {
				return "";
			}
			if (this.randomChar > 0) {
				back = RandomStringUtils.randomAlphanumeric(this.randomChar) + back;
			}
			if (isEncrypt) {
				back = Cryptography.getInstance().encrypt(back);
			}
			return back;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public Cookie getCookie(String value, String contextPath) {
		return this.getCookie(value, contextPath, this.lifeTime);
	}

	public Cookie getCookie(String value, String contextPath, int expiry) {
		Cookie c = new Cookie(getClientName(), getClientValue(value));
		c.setDomain(domain);
		c.setPath(contextPath + getPath());
		c.setMaxAge(expiry);
		return c;
	}

	// �õ�ɾ��һ��cookie��cookie
	public Cookie getDeleteCookie(String contextPath) {
		return this.getCookie("", contextPath, 0);// ������ʱ������Ϊ0��Ϊɾ��һ��cookie
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
		if (!this.path.startsWith("/")) {
			this.path = "/" + this.path;
		}
	}

	/**
	 * @return the clinetName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clinetName
	 *            the clinetName to set
	 */
	public void setClientName(String clinetName) {
		this.clientName = clinetName;
	}

	/**
	 * @return the isEncrypt
	 */
	public boolean isEncrypt() {
		return isEncrypt;
	}

	/**
	 * @param isEncrypt
	 *            the isEncrypt to set
	 */
	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the lifeTime
	 */
	public int getLifeTime() {
		return lifeTime;
	}

	/**
	 * @param lifeTime
	 *            the lifeTime to set
	 */
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getRandomChar() {
		return randomChar;
	}

	public void setRandomChar(int randomChar) {
		this.randomChar = randomChar;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
