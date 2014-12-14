package com.nonfamous.commom.form;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.nonfamous.commom.form.validator.ByteLengthValidator;
import com.nonfamous.commom.form.validator.LengthValidator;
import com.nonfamous.commom.form.validator.MoneyValidator;
import com.nonfamous.commom.form.validator.NumberValidator;
import com.nonfamous.commom.form.validator.RegexpValidator;
import com.nonfamous.commom.form.validator.RequiredValidator;
import com.nonfamous.commom.form.validator.Validator;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author:alan
 * 
 * <pre>
 * 默认的FormFactory实现
 * </pre>
 * 
 * @version $Id: DefaultFormFactory.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public class DefaultFormFactory implements InitializingBean,
		ServletContextAware, FormFactory {
	private List formFiles;

	private Map<String, Form> forms = new HashMap<String, Form>();

	private ServletContext servletContext;

	public Form getForm(String formName, HttpServletRequest request) {
		Form form = ((DefaultForm) forms.get(formName)).colne();
		for (Iterator keys = form.getAllKeys().iterator(); keys.hasNext();) {
			String key = (String) keys.next();
			Field field = form.getField(key);
			field.setMessage(null);
			field.setValue(StringUtils.trim(request.getParameter(key)));
		}
		return form;
	}

	/**
	 * 参数设置完了之后，spring会自动调用该方法 init Form group
	 */
	public void afterPropertiesSet() throws Exception {
		String path = servletContext.getRealPath("/");

		SAXReader reader = new SAXReader();
		Document document = null;
		for (int i = 0; i < formFiles.size(); i++) {
			document = reader.read(new File(path + (String) formFiles.get(i)));
			// document = reader.read(new
			// File("E:/develop/SpringMvcTest/webroot/WEB-INF/form/form.xml"));
			Element root = document.getRootElement();

			// group elements
			for (Iterator groups = root.elementIterator(); groups.hasNext();) {
				Form form = new DefaultForm();
				Element group = (Element) groups.next();
				// 迭代根元素的属性attributes）元素
				for (Iterator groupAttr = group.attributeIterator(); groupAttr
						.hasNext();) {
					Attribute attribute = (Attribute) groupAttr.next();
					form.setFormName(attribute.getValue());
				}
				// field elements
				for (Iterator fields = group.elementIterator(); fields
						.hasNext();) {
					Element field = (Element) fields.next();
					Field formField = new Field();
					for (Iterator fieldAttr = field.attributeIterator(); fieldAttr
							.hasNext();) {
						Attribute attribute = (Attribute) fieldAttr.next();
						formField.setName(attribute.getValue());
					}
					// validator elements
					for (Iterator validators = field.elementIterator(); validators
							.hasNext();) {
						FieldConfig fieldConfig = new FieldConfig();
						Element validator = (Element) validators.next();

						Validator validatorInstance = createValidator(validator
								.getName());
						Map<String, String> validatorConfig = new HashMap<String, String>();
						for (Iterator validatorAttr = validator
								.attributeIterator(); validatorAttr.hasNext();) {
							Attribute attribute = (Attribute) validatorAttr
									.next();
							validatorConfig.put(attribute.getName(), attribute
									.getValue());
						}
						BeanUtils.copyProperties(validatorInstance,
								validatorConfig);
						fieldConfig.setValidator(validatorInstance);
						// get message
						for (Iterator messages = validator.elementIterator(); messages
								.hasNext();) {
							Element message = (Element) messages.next();
							fieldConfig.setMessage(message.getStringValue());
						}
						formField.setFieldConfigs(fieldConfig);
					}
					form.setField(formField);
					form.setKey(formField.getName());
				}
				forms.put(form.getFormName(), form);
			}

		}

	}

	/**
	 * 垃圾代码啊
	 * 
	 * @param validatorKey
	 * @return
	 */
	private Validator createValidator(String validatorKey) {
		if (validatorKey.equalsIgnoreCase("required-validator")) {
			return new RequiredValidator();
		} else if (validatorKey.equalsIgnoreCase("length-validator")) {
			return new LengthValidator();
		} else if (validatorKey.equalsIgnoreCase("regexp-validator")) {
			return new RegexpValidator();
		} else if (validatorKey.equalsIgnoreCase("number-validator")) {
			return new NumberValidator();
		} else if (validatorKey.equalsIgnoreCase("money-validator")) {
			return new MoneyValidator();
		} else if (validatorKey.equalsIgnoreCase("byte-length-validator")) {
			return new ByteLengthValidator();
		} else {
			return null;
		}

	}

	public void formCopy(Object from, Object to) {
		if (from == null || to == null) {
			throw new java.lang.NullPointerException("from or to can't null");
		}
		if (from instanceof Form) {
			// form to do
			Form form = (Form) from;
			// iterator all form key
			for (Iterator itr = form.getAllKeys().iterator(); itr.hasNext();) {
				String key = (String) itr.next();
				try {
					// get do properties class type
					PropertyDescriptor p = PropertyUtils.getPropertyDescriptor(
							to, key);
					if (p.getPropertyType().getName().equals("java.util.Date")) {
						try {
							// siingle copy property from form to do
							BeanUtils.copyProperty(to, key, DateUtils
									.string2DateTime(form.getField(key)
											.getValue()));
						} catch (ParseException e) {
						}
					} else {
						BeanUtils.copyProperty(to, key, form.getField(key)
								.getValue());
					}
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				}
			}

		} else {
			// do to form
			DomainBase domain = (DomainBase) from;
			Form form = (Form) to;
			for (Iterator itr = form.getAllKeys().iterator(); itr.hasNext();) {
				String key = (String) itr.next();
				try {
					PropertyDescriptor p = PropertyUtils.getPropertyDescriptor(
							domain, key);
					if (p != null
							&& p.getPropertyType().getName().equals(
									"java.util.Date")) {
						// set form field value
						form.getField(key).setValue(
								DateUtils.dtSimpleFormat(DateUtils.gmtFormatStringDate((BeanUtils
										.getProperty(domain, key)))));
					} else {
						form.getField(key).setValue(
								BeanUtils.getProperty(domain, key));
					}
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				}
			}

		}

	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;

	}

	public void setFormFiles(List formFiles) {
		this.formFiles = formFiles;
	}
}
