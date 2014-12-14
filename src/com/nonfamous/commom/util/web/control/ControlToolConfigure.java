/**
 * 
 */
package com.nonfamous.commom.util.web.control;

/**
 * @author fred
 * 
 */
public class ControlToolConfigure {
	// �Ƿ���ȫ�ֵ�context
	private boolean sameContext;

	// ģ��·����ǰ׺
	private String prefix = "control";

	// ģ�����չ��
	private String suffix = ".vm";

	// ��controlִ�г���ʱ���Ƿ��׳��쳣������ĬĬ��ִ����ȥ
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
