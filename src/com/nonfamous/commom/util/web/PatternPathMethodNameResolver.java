package com.nonfamous.commom.util.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.multiaction.AbstractUrlMethodNameResolver;
import org.springframework.web.util.WebUtils;

/**
 * 一种url风格，如detail: /product/124f34d.htm,before add: /product/init.htm,
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
		methodNames.put(Load, "load");//load(get) 方法
		methodNames.put("init", "init");//新增或者更新前显示
		methodNames.put("add", "add");//新增
		methodNames.put("update", "update");//更新
		methodNames.put("remove", "remove");//删除
	}

	/**
	 * 通过此配置来覆盖缺省的配置，或新增对应的url -> method 映射
	 * 比如：如果"load.htm"的方法名在对应的control中的名字是"detail"，则在map中放一对"load","detail"
	 * 如果此control有一个独有的方法"updateName",对应url后缀"update_name",
	 * 则在map中放一对"update_name","updateName"
	 * 注意：方法名不能为"get"，因为与MultiActionController.getLastModified 冲突
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
