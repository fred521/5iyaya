package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: Sms.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class Sms extends DomainBase {

	private static final long serialVersionUID = 7592397494202357029L;

	public static final String SMS_WAIT_SEND = "W";

	public static final String SMS_SEND_SUCCESS = "S";

	// 字段描述:短信编号
	private java.lang.Long smsId;

	// 字段描述:本次发送手机号
	private java.lang.String phone;

	// 字段描述:发送状态:W，等待发送；S，发送成功
	private java.lang.String status;

	// 字段描述:发送内容
	private java.lang.String context;

	// 字段描述:创建时间
	private java.util.Date gmtCreate;

	// 字段描述:最后修改时间
	private java.util.Date gmtModify;

	// 发送次数
	private Integer sendCount;

	public void setSmsId(java.lang.Long smsId) {
		this.smsId = smsId;
	}

	public java.lang.Long getSmsId() {
		return this.smsId;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public void setContext(java.lang.String context) {
		this.context = context;
	}

	public java.lang.String getContext() {
		return this.context;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public java.util.Date getGmtModify() {
		return this.gmtModify;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

}