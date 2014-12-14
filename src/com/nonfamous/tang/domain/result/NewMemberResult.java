package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * �����»�Ա��������
 * </p>
 * 
 * @author:daodao
 * @version $Id: NewMemberResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class NewMemberResult extends ResultBase {

	private static final long serialVersionUID = -7581422513923152767L;

	/** ���û������û��Ѿ����� */
	public static final String ERROR_MEMBER_EXIST = "ERROR_MEMBER_EXIST";

	/** ���û������ֻ��Ѿ���ע��� */
	public static final String ERROR_MOBILE_EXIST = "ERROR_MOBILE_EXIST";

	/** �����»�Ա��Ψһ��ʶ�û�Ա��id */
	public String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_EXIST)) {
			return "���û����Ѿ�������ע��ʹ��,�뻻һ���û���";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MOBILE_EXIST)) {
			return "���ֻ��Ѿ�������ע��ʹ�ã��뻻һ���ֻ���";
		}
		return super.getErrorMessage();
	}
}
