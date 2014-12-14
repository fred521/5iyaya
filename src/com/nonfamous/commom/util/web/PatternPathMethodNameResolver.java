package com.nonfamous.commom.util.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.multiaction.AbstractUrlMethodNameResolver;
import org.springframework.web.util.WebUtils;

/**
 * һ��url�����detail: /product/124f34d.htm,before add: /product/init.htm,
 * add: /product/add.htm update:/product/update.htm remove: /product/remove.htm
 * 
 * @author eyeieye
 * 
 */
public class PatternPathMethodNameResolver extends AbstractUrlMethodNameResolver {
	private static final String Load = "load";

	private final Map<String, String> methodNames = new HashMap<String, String>();

	public PatternPathMethodNameResolver() {
		super();
		methodNames.put(Load, "load");//load(get) ����
		methodNames.put("init", "init");//�������߸���ǰ��ʾ
		methodNames.put("add", "add");//����
		methodNames.put("update", "update");//����
		methodNames.put("remove", "remove");//ɾ��
	}

	/**
	 * ͨ��������������ȱʡ�����ã���������Ӧ��url -> method ӳ��
	 * ���磺���"load.htm"�ķ������ڶ�Ӧ��control�е�������"detail"������map�з�һ��"load","detail"
	 * �����control��һ�����еķ���"updateName",��Ӧurl��׺"update_name",
	 * ����map�з�һ��"update_name","updateName"
	 * ע�⣺����������Ϊ"get"����Ϊ��MultiActionController.getLastModified ��ͻ
	 * 
	 * @param names
	 */
	public void setMethodNames(Map<String, String> names) {
		if (names == null || names.isEmpty()) {
			return;
		}
		this.methodNames.putAll(names);
	}

	@Override
	protected String getHandlerMethodNameForUrlPath(String urlPath) {
		String filenameFromUrlPath = WebUtils
				.extractFilenameFromUrlPath(urlPath);
		String methodName = this.methodNames.get(filenameFromUrlPath);
		if (methodName == null) {
			methodName = this.methodNames.get(Load);
		}
		if(this.logger.isDebugEnabled()){
			logger.debug("find methodName:"+methodName);
		}
		return methodName;
	}

}
