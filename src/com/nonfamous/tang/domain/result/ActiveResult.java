package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * �����Ա�������
 * </p>
 * 
 * @author:daodao
 * @version $Id: ActiveResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class ActiveResult extends ResultBase {

	private static final long serialVersionUID = 7623393416413453518L;

	/** У������� */
	public static final String ERROR_INVALID_CHECK_CODE = "ERROR_INVALID_CHECK_CODE";

	/** �û��ֻ��Ѽ��� */
	public static final String ERROR_HAS_ACTIVE = "ERROR_HAS_ACTIVE";

	/** û�и�ע���û� */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** ��������ģ�����ԭ���Ժ�����չ */
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_INVALID_CHECK_CODE)) {
			return "У�������,����������";
		} else if (StringUtils.equals(getErrorCode(), ERROR_HAS_ACTIVE)) {
			return "�ֻ��Ѿ�������";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "�޸�ע���û�";
		}
		return super.getErrorMessage();
	}

}
