/**
 * 
 */
package com.nonfamous.commom.util.web.control;

/**
 * @author fred
 * 
 */
public class ControlToolConfigure {
	// 是否共享全局的context
	private boolean sameContext;

	// 模版路径的前缀
	private String prefix = "control";

	// 模版的扩展名
	private String suffix = ".vm";

	// 当control执行出错时，是否抛出异常，或者默默的执行下去
	private boolean throwsException = false;

	public String getControlTemplate(String controlName) {
		StringBuilder sb = new StringBuilder(prefix).append(controlName)
				.append(suffix);
		return sb.toString();
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the sameContext
	 */
	public boolean isSameContext() {
		return sameContext;
	}

	/**
	 * @param sameContext
	 *            the sameContext to set
	 */
	public void setSameContext(boolean sameContext) {
		this.sameContext = sameContext;
	}

	public boolean isThrowsException() {
		return throwsException;
	}

	public void setThrowsException(boolean throwsException) {
		this.throwsException = throwsException;
	}

}
