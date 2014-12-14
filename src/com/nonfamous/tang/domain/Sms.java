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

	// �ֶ�����:���ű��
	private java.lang.Long smsId;

	// �ֶ�����:���η����ֻ���
	private java.lang.String phone;

	// �ֶ�����:����״̬:W���ȴ����ͣ�S�����ͳɹ�
	private java.lang.String status;

	// �ֶ�����:��������
	private java.lang.String context;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	// �ֶ�����:����޸�ʱ��
	private java.util.Date gmtModify;

	// ���ʹ���
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