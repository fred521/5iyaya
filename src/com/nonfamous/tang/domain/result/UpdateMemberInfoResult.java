package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * �޸Ļ�Ա��Ϣ���ؽ����
 * </p>
 * 
 * @author:daodao
 * @version $Id: UpdateMemberInfoResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class UpdateMemberInfoResult extends ResultBase {

	private static final long serialVersionUID = 4356727719782634110L;

	/** û�и�ע���Ա */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** �û�Ա�Ѿ���ɾ�� */
	public static final String ERROR_MEMBER_DELETED = "ERROR_MEMBER_DELETED";

	/** ��������ģ�����ԭ���Ժ�����չ */
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";

	/** �����벻��ȷ */
	public static final String ERROR_OLD_PASSWORD = "ERROR_OLD_PASSWORD";

	/** ���ֻ����벻��ȷ */
	public static final String ERROR_OLD_MOBILE = "ERROR_OLD_MOBILE";

	/** �ֻ��Ѿ�������ʹ�� */
	public static final String ERROR_MOBILE_EXIST = "ERROR_MOBILE_EXIST";

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "�޸�ע���Ա";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_DELETED)) {
			return "��Ա�Ѿ���ɾ�������������κ��޸�";
		} else if (StringUtils.equals(getErrorCode(), ERROR_OLD_PASSWORD)) {
			return "�����벻��ȷ������������";
		} else if (StringUtils.equals(getErrorCode(), ERROR_OLD_MOBILE)) {
			return "���ֻ����벻��ȷ������������";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MOBILE_EXIST)) {
			return "���ֻ��Ѿ�������ע��ʹ�ã��뻻һ���ֻ���";
		}
		return super.getErrorMessage();
	}
}
