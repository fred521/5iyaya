package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: MobileValidate.java,v 1.1 2008/07/11 00:47:02 fred Exp $
 */
public class MobileValidate extends DomainBase {

	private static final long serialVersionUID = -4759957031737828203L;

	// �ֶ�����:��Ա���
	private java.lang.String memberId;

	// �ֶ�����:У����ֻ�����
	private java.lang.String phone;

	// �ֶ�����:�����ֻ�У����:6λ�ֻ�У����
	private java.lang.String validateCode;

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}

	public java.lang.String getMemberId() {
		return this.memberId;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setValidateCode(java.lang.String validateCode) {
		this.validateCode = validateCode;
	}

	public java.lang.String getValidateCode() {
		return this.validateCode;
	}

}