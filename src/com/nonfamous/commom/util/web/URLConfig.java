package com.nonfamous.commom.util.web;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author eyeieye
 * 
 */

public class URLConfig {

	private String protocol = "http";

	private String host;

	private Integer port = 80;

	private String path;

	private String buildURL = null;

	// �Ƿ����������У�������У�����Ҫ����contextpath��·����
	private boolean contextPathSensitive = false;

	/**
	 * �������úõ�url��${protocol}://${host}:{port} ����http://www.nof.com:8080 ,
	 * http://www.aef.com https://img.server.com ����"/"��β
	 * 
	 * @return
	 */
	public String getURL() {
		if (buildURL == null) {
			StringBuffer sb = new StringBuffer();
			if (StringUtils.isNotBlank(protocol)) {
				sb.append(protocol).append("://");
			}
			if (StringUtils.isNotBlank(host)) {
				sb.append(host);
			}
			if (port != null) {
				if ((protocol.equals("http") && port == 80)
						|| (protocol.equals("https") && port == 445)) {

				} else {
					sb.append(":").append(port);
				}
			}
			if (sb.charAt(sb.length() - 1) == '/') {
				sb.deleteCharAt(sb.length() - 1);
			}
			buildURL = sb.toString();
		}
		return buildURL;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host.toLowerCase();
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol.toLowerCase();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setContextPathSensitive(boolean contextPathSensitive) {
		this.contextPathSensitive = contextPathSensitive;
	}

	public boolean isContextPathSensitive() {
		/*
		 * if (StringUtils.isNotBlank(contextPathSensitive)) { return
		 * !contextPathSensitive.trim().equalsIgnoreCase("false"); }
		 */
		return contextPathSensitive;
	}

}
