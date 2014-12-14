package com.nonfamous.tang.domain.result;

import java.io.Serializable;

/**
 * <p>
 * ����Service�㷵�ش���������һ��,�Լ�����ģ���Ҿ��úþ�һ���ú���
 * </p>
 * 
 * @author:fred
 * @version $Id: ResultBase.java,v 1.2 2008/11/29 02:53:47 fred Exp $
 */
public class ResultBase implements Serializable {

	private static final long serialVersionUID = -2655356456731762301L;

	/** ��Ч���� */
	public static final String ERROR_INVALID_PARAMETER = "ERROR_INVALID_PARAMETER";

	// �����Ƿ�ɹ�
	private boolean isSuccess = false;

	// �������(���ں����߼�����)
	private String errorCode;

	// ������Ϣ��������������ǰ̨��ʾ��
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
