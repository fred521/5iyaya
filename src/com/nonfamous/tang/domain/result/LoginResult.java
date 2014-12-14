package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.Member;

/**
 * <p>
 * ��Ա��¼�������
 * </p>
 * 
 * @author:daodao
 * @version $Id: LoginResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class LoginResult extends ResultBase {

	private static final long serialVersionUID = -7170528761238487494L;

	/** û�и�ע���û� */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** ����������� */
	public static final String ERROR_PASS_ERROR = "ERROR_PASS_ERROR";

	/** �û�״̬�����������ܱ����ã� */
	public static final String ERROR_MEMBER_STATUS = "ERROR_MEMBER_STATUS";

	// ��¼��Ա��Ϣ
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_PASS_ERROR)
				|| StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "��¼���󣬻�Ա�ʺŻ������벻��ȷ";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_STATUS)) {
			return "��ǰ״̬������״̬";
		}
		return super.getErrorMessage();
	}
}
