package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: fred
 * 
 *          <pre>
 * comment
 * </pre>
 * 
 * @version $Id: Helper.java,v 1.2 2008/09/21 11:59:04 fred Exp $
 */
public class Helper extends DomainBase {

	private static final long serialVersionUID = 5743129589700578124L;
	// ×Ö¶ÎÃèÊö:°ïÖúĞÅÏ¢±àºÅ
	private java.lang.String helperId;
	// ×Ö¶ÎÃèÊö:°ïÖúĞÅÏ¢±êÌâ
	private java.lang.String helperTitle;
	// ×Ö¶ÎÃèÊö:°ïÖúĞÅÏ¢ËùÊô·ÖÀà
	private java.lang.Integer helperType;
	// ×Ö¶ÎÃèÊö:°ïÖúĞÅÏ¢ËùÊô»áÔ±
	private java.lang.String memberId;
	// ×Ö¶ÎÃèÊö:°ïÖúĞÅÏ¢×´Ì¬:N Õı³£¡¢D É¾³ı
	private java.lang.String helperStatus;
	// ×Ö¶ÎÃèÊö:
	private java.util.Date gmtCreate;
	// ×Ö¶ÎÃèÊö:
	private java.lang.String creator;
	// ×Ö¶ÎÃèÊö:
	private java.util.Date gmtModify;
	// ×Ö¶ÎÃèÊö:
	private java.lang.String modifier;
	// Helper description
	private java.lang.String content;
	
	private HelperType helperTypeDO= new HelperType();

	public HelperType getHelperTypeDO() {
		return helperTypeDO;
	}

	public void setHelperTypeDO(HelperType helperTypeDO) {
		this.helperTypeDO = helperTypeDO;
	}

	public java.lang.String getContent() {
		return this.content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public final static String NORMAL_STATUS = "N";

	public final static String DELETE_STATUS = "D";

	public void setHelperId(java.lang.String helperId) {
		this.helperId = helperId;
	}

	public java.lang.String getHelperId() {
		return this.helperId;
	}

	public void setHelperTitle(java.lang.String helperTitle) {
		this.helperTitle = helperTitle;
	}

	public java.lang.String getHelperTitle() {
		return this.helperTitle;
	}


	public java.lang.Integer getHelperType() {
		return this.helperType;
	}

	public void setHelperType(java.lang.Integer helperType) {
		this.helperType = helperType;
	}

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}

	public java.lang.String getMemberId() {
		return this.memberId;
	}

	public void setHelperStatus(java.lang.String helperStatus) {
		this.helperStatus = helperStatus;
	}

	public java.lang.String getHelperStatus() {
		return this.helperStatus;
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

}