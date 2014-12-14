package com.nonfamous.commom.util.web;

import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;

/**
 * @author eyeieye
 * һ����Դ�Сд����ת���Ľ������� _a -> A , _r -> R �ȵ�
 */
public class CaseInsensitiveInternalPathMethodNameResolver extends
		InternalPathMethodNameResolver {
	protected String extractHandlerMethodNameFromUrlPath(String uri) {
		String name = super.extractHandlerMethodNameFromUrlPath(uri);
		int position = name.indexOf('_');
		if (position == -1) {
			return name;
		}
		StringBuilder sb = new StringBuilder(name);
		while ((position = sb.indexOf("_")) != -1) {
			sb.deleteCharAt(position);
			if (position < sb.length()) {
				char c = sb.charAt(position);
				sb.deleteCharAt(position);
				c = Character.toUpperCase(c);
				sb.insert(position, c);
			}

		}
		return sb.toString();
	}
}
