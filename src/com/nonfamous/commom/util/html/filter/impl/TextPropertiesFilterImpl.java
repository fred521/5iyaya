package com.nonfamous.commom.util.html.filter.impl;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.util.html.filter.TextPropertiesFilter;
import com.nonfamous.commom.util.html.remover.HtmlRemover;

/**
 * 
 * @author shenyu
 * 
 */
public class TextPropertiesFilterImpl implements TextPropertiesFilter {

	private static Log logger = LogFactory
			.getLog(TextPropertiesFilterImpl.class);

	private HtmlRemover htmlFilter;

	private Map<String, List<String>> classNameToProperties;

	public void filterProperties(Object obj) {
		if (obj == null) {
			throw new NullPointerException("obj can't be null");
		}
		Class clazz = obj.getClass();
		if (clazz.isArray()) {
			int size = Array.getLength(obj);
			for (int i = 0; i < size; i++) {
				Object one = Array.get(obj, i);
				if (one != null) {
					filterOneObject(one);
				}
			}
			return;
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			Collection coll = (Collection) obj;
			for (Iterator it = coll.iterator(); it.hasNext();) {
				Object one = it.next();
				if (one != null) {
					filterOneObject(one);
				}
			}
			return;
		}
		filterOneObject(obj);
	}

	private void filterOneObject(Object obj) {
		String className = obj.getClass().getName();
		List<String> properties = classNameToProperties.get(className);
		if (properties == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("class [" + className
						+ "] not config , so not need filter");
			}
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("not begin filter [" + className + "]");
		}
		for (String property : properties) {
			try {
				String value = BeanUtils.getProperty(obj, property);
				value = this.htmlFilter.filterHtml(value);
				BeanUtils.setProperty(obj, property, value);
			} catch (IllegalAccessException e) {
				logger.error("error than filter property", e);
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				logger.error("error than filter property", e);
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				logger.error("error than filter property", e);
				throw new RuntimeException(e);
			}
		}
	}

	public Map<String, List<String>> getClassNameToProperties() {
		return classNameToProperties;
	}

	public void setClassNameToProperties(
			Map<String, List<String>> classNameToProperties) {
		this.classNameToProperties = classNameToProperties;
	}

	public HtmlRemover getHtmlFilter() {
		return htmlFilter;
	}

	public void setHtmlFilter(HtmlRemover htmlFilter) {
		this.htmlFilter = htmlFilter;
	}

}
