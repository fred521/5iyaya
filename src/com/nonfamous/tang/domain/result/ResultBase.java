package com.nonfamous.tang.domain.result;

import java.io.Serializable;

/**
 * <p>
 * 用于Service层返回处理结果给上一层,自己想想的，大家觉得好就一起用好了
 * </p>
 * 
 * @author:fred
 * @version $Id: ResultBase.java,v 1.2 2008/11/29 02:53:47 fred Exp $
 */
public class ResultBase implements Serializable {

	private static final long serialVersionUID = -2655356456731762301L;

	/** 无效参数 */
	public static final String ERROR_INVALID_PARAMETER = "ERROR_INVALID_PARAMETER";

	// 处理是否成功
	private boolean isSuccess = false;

	// 错误代码(用于后续逻辑处理)
	private String errorCode;

	// 错误信息描述（可能用于前台显示）
	private String errorMessage;

	public String getErrorMessage() {
		if (errorMessage == null) {
			return errorCode;
		}
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
