package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: CommendContent.java,v 1.1 2008/07/11 00:47:00 fred Exp $
 */
public class CommendContent extends DomainBase {

	private static final long serialVersionUID = -5356401779137691046L;

	// �ֶ�����:�Ƽ����ݱ��
	private java.lang.Long contentId;

	// �ֶ�����:�����Ƽ�λ�ñ��
	private java.lang.Long commendPositionId;

	// �ֶ�����:�Ƽ����ͣ�1����Ʒ��2�����̡�3����ѯ��Ϣ
	private java.lang.Long commendType;

	// �ֶ�����:�Ƽ�����
	private java.lang.String commendText;

	// �ֶ�����:�Ƽ�ͼƬ·�������ͼƬurlΪ�գ�����Ҫ�������ַ�滻ͼƬ��ַ
	private java.lang.String picPath;

	// �ֶ�����:�Ƽ�ͼƬurl
	private java.lang.String picUrl;

	// �ֶ�����:��������
	private java.lang.Long batchNum;

	// �ֶ�����:�����۸񣬿��Ƶ���
	private java.lang.Long batchPrice;

	// �ֶ�����:�Ƽ���Ϣurl
	private java.lang.String commendUrl;

	// �ֶ�����:�Ƽ�˵��
	private java.lang.String commendDesc;

	// �ֶ�����:�Ƽ���ʼ����
	private java.util.Date gmtStart;

	// �ֶ�����:�Ƽ���������
	private java.util.Date gmtEnd;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	// �ֶ�����:������
	private java.lang.String creator;

	// �ֶ�����:����޸�ʱ��
	private java.util.Date gmtModify;

	// �ֶ�����:����޸���
	private java.lang.String modifier;

	// �ֶ�����:�����Ƽ�ҳ�棺0����ҳ1����ѯҳ�桢2����Ʒҳ�桢3����ά��ҳ
	private java.lang.Long commendPage;

	// �ֶ�����:�Ƽ�����״̬:N������Dɾ����ȡ��C
	private java.lang.String commendStatus;

	public void setContentId(java.lang.Long contentId) {
		this.contentId = contentId;
	}

	public java.lang.Long getContentId() {
		return this.contentId;
	}

	public void setCommendPositionId(java.lang.Long commendPositionId) {
		this.commendPositionId = commendPositionId;
	}

	public java.lang.Long getCommendPositionId() {
		return this.commendPositionId;
	}

	public void setCommendType(java.lang.Long commendType) {
		this.commendType = commendType;
	}

	public java.lang.Long getCommendType() {
		return this.commendType;
	}

	public void setCommendText(java.lang.String commendText) {
		this.commendText = commendText;
	}

	public java.lang.String getCommendText() {
		return this.commendText;
	}

	public void setPicPath(java.lang.String picPath) {
		this.picPath = picPath;
	}

	public java.lang.String getPicPath() {
		return this.picPath;
	}

	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}

	public java.lang.String getPicUrl() {
		return this.picUrl;
	}

	public void setBatchNum(java.lang.Long batchNum) {
		this.batchNum = batchNum;
	}

	public java.lang.Long getBatchNum() {
		return this.batchNum;
	}

	public void setBatchPrice(java.lang.Long batchPrice) {
		this.batchPrice = batchPrice;
	}

	public java.lang.Long getBatchPrice() {
		return this.batchPrice;
	}

	public void setCommendUrl(java.lang.String commendUrl) {
		this.commendUrl = commendUrl;
	}

	public java.lang.String getCommendUrl() {
		return this.commendUrl;
	}

	public void setCommendDesc(java.lang.String commendDesc) {
		this.commendDesc = commendDesc;
	}

	public java.lang.String getCommendDesc() {
		return this.commendDesc;
	}

	public void setGmtStart(java.util.Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public java.util.Date getGmtStart() {
		return this.gmtStart;
	}

	public void setGmtEnd(java.util.Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public java.util.Date getGmtEnd() {
		return this.gmtEnd;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	public java.lang.String getCreator() {
		return this.creator;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public java.util.Date getGmtModify() {
		return this.gmtModify;
	}

	public void setModifier(java.lang.String modifier) {
		this.modifier = modifier;
	}

	public java.lang.String getModifier() {
		return this.modifier;
	}

	public java.lang.Long getCommendPage() {
		return commendPage;
	}

	public void setCommendPage(java.lang.Long commendPage) {
		this.commendPage = commendPage;
	}

	public java.lang.String getCommendStatus() {
		return commendStatus;
	}

	public void setCommendStatus(java.lang.String commendStatus) {
		this.commendStatus = commendStatus;
	}
}