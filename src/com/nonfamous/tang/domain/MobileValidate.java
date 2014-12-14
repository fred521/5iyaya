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

	// 字段描述:会员编号
	private java.lang.String memberId;

	// 字段描述:校验的手机号码
	private java.lang.String phone;

	// 字段描述:本次手机校验码:6位手机校验码
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