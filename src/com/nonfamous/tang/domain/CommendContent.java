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

	// 字段描述:推荐内容编号
	private java.lang.Long contentId;

	// 字段描述:所属推荐位置编号
	private java.lang.Long commendPositionId;

	// 字段描述:推荐类型：1、商品、2、店铺、3、咨询信息
	private java.lang.Long commendType;

	// 字段描述:推荐文字
	private java.lang.String commendText;

	// 字段描述:推荐图片路径、如果图片url为空，则需要用这个地址替换图片地址
	private java.lang.String picPath;

	// 字段描述:推荐图片url
	private java.lang.String picUrl;

	// 字段描述:起批数量
	private java.lang.Long batchNum;

	// 字段描述:起批价格，控制到分
	private java.lang.Long batchPrice;

	// 字段描述:推荐信息url
	private java.lang.String commendUrl;

	// 字段描述:推荐说明
	private java.lang.String commendDesc;

	// 字段描述:推荐开始日期
	private java.util.Date gmtStart;

	// 字段描述:推荐截至日期
	private java.util.Date gmtEnd;

	// 字段描述:创建时间
	private java.util.Date gmtCreate;

	// 字段描述:创建人
	private java.lang.String creator;

	// 字段描述:最后修改时间
	private java.util.Date gmtModify;

	// 字段描述:最后修改人
	private java.lang.String modifier;

	// 字段描述:所属推荐页面：0：首页1：咨询页面、2：商品页面、3：三维首页
	private java.lang.Long commendPage;

	// 字段描述:推荐内容状态:N正常、D删除、取消C
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