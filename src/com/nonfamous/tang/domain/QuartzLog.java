package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: QuartzLog.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class QuartzLog extends DomainBase {

	private static final long serialVersionUID = -6798750723846982410L;

	// �ֶ�����:��ʱ�����磺QUARTZ_INDEX_GOODS����Ʒ��������ʱ����QUARTZ_COMMEND���Ƽ������ִ��ʱ�䣩
	private java.lang.String quartzType;

	// �ֶ�����:���ִ�ж�ʱʱ��
	private java.util.Date gmtExecute;

	// �ֶ�����:��ʱ˵��
	private java.lang.String quartzMemo;

	public void setQuartzType(java.lang.String quartzType) {
		this.quartzType = quartzType;
	}

	public java.lang.String getQuartzType() {
		return this.quartzType;
	}

	public void setGmtExecute(java.util.Date gmtExecute) {
		this.gmtExecute = gmtExecute;
	}

	public java.util.Date getGmtExecute() {
		return this.gmtExecute;
	}

	public void setQuartzMemo(java.lang.String quartzMemo) {
		this.quartzMemo = quartzMemo;
	}

	public java.lang.String getQuartzMemo() {
		return this.quartzMemo;
	}

}