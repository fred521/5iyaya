package com.nonfamous.commom.util.html.remover;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;

import com.nonfamous.commom.util.StringUtils;

public class HtmlRemover {
	private static Log log = LogFactory.getLog(HtmlRemover.class);

	private String encoding;

	private Set<String> acceptElements;

	private Set<String> removeElements;

	private Set<String> attributeMatchers;

	private ThreadLocal<Remover> local = new ThreadLocal<Remover>();

	public String filterHtml(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		Remover r = this.local.get();
		if (r == null) {
			r = new Remover(acceptElements, removeElements, attributeMatchers,
					encoding);
			this.local.set(r);
		}
		return r.filter(s);
	}

	private static class Remover {
		// 过滤器
		private XMLParserConfiguration parser;

		private String encoding = "UTF-8";

		// 处理缓冲区的最大长度16k
		private static int MAX_BUFFER_LENGTH = 1024 * 16;

		// 数据输出缓冲区
		private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream(
				MAX_BUFFER_LENGTH);

		/**
		 * 构造函数
		 * 
		 * @param acceptElements
		 * @param removeElements
		 * @param attributeMatchers
		 */
		public Remover(Set<String> acceptElements, Set<String> removeElements,
				Set<String> attributeMatchers, String encoding) {
			if (encoding != null) {
				this.encoding = encoding;
			}
			try {
				// 初始化过滤器
				EnhanceElementRemover remover = new EnhanceElementRemover();

				// 初始化需要接受的标签
				for (String accept : acceptElements) {
					//
					String[] ret = accept.split(",");
					String[] attrs = null;
					if (ret.length > 1) {
						attrs = (String[]) ArrayUtils.subarray(ret, 1,
								ret.length);
					}

					//
					remover.acceptElement(ret[0], attrs);
				}

				// 初始化需要过滤的标签
				for (String remove : removeElements) {
					remover.removeElement(remove);
				}

				// 初始化用正则表达式的属性检查器
				for (String item : attributeMatchers) {
					String[] ret = item.split(",", 3);
					remover.acceptElementAttribute(ret[0], ret[1], ret[2]);
				}

				// 初始化html配置对象
				parser = new HTMLConfiguration();

				// create writer filter
				org.cyberneko.html.filters.Writer writer = new org.cyberneko.html.filters.Writer(
						outputBuffer, this.encoding);

				// setup filter chain
				XMLDocumentFilter[] filters = { remover, writer };

				//
				parser.setProperty(
						"http://cyberneko.org/html/properties/names/elems",
						"lower");
				parser
						.setProperty(
								"http://cyberneko.org/html/properties/filters",
								filters);

				//
				parser
						.setFeature(
								"http://apache.org/xml/features/scanner/notify-builtin-refs",
								true);
				parser
						.setFeature(
								"http://cyberneko.org/html/features/scanner/notify-builtin-refs",
								true);
				parser
						.setFeature(
								"http://cyberneko.org/html/features/scanner/ignore-specified-charset",
								true);
				parser.setFeature("http://cyberneko.org/html/features/balance-tags", true);

				//
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 过滤一段原始的HTML
		 * 
		 * @param origHtml
		 * @return
		 */
		public String filter(String origHtml) {
			if (log.isDebugEnabled()) {
				log.debug("origHtml:" + origHtml);
			}
			try {
				// 清空输出缓冲区
				outputBuffer.reset();
				XMLInputSource is = new XMLInputSource(null, null, null,
						new StringReader(origHtml), this.encoding);
				parser.parse(is);
				return new String(outputBuffer.toByteArray(), this.encoding);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Set<String> getAcceptElements() {
		return acceptElements;
	}

	public void setAcceptElements(Set<String> acceptElements) {
		this.acceptElements = acceptElements;
	}

	public Set<String> getAttributeMatchers() {
		return attributeMatchers;
	}

	public void setAttributeMatchers(Set<String> attributeMatchers) {
		this.attributeMatchers = attributeMatchers;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Set<String> getRemoveElements() {
		return removeElements;
	}

	public void setRemoveElements(Set<String> removeElements) {
		this.removeElements = removeElements;
	}

}
