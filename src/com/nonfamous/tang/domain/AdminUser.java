package com.nonfamous.tang.domain;

import java.util.Date;

import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * @version $Id: AdminUser.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class AdminUser extends DomainBase {

	private static final long serialVersionUID = -474994806553081972L;

	public static final String StatusNormal = "N";

	public static final String StatusProhibit = "P";

	// �ֶ�����:����Ա���
	private Long userId;

	// �ֶ�����:����Ա��½����
	private String loginName;

	// �ֶ�����:����Ա��½����,md5���ܺ�ģ�Ҳ�������ݿ�洢������
	private String loginPassword;

	// �����������ǰ
	private String unencryptPassword;

	// �ֶ�����:����Ա״̬:N������P����
	private String userStatus = StatusNormal;

	// �ֶ�����:��ϵ�绰
	private String phone;

	// �ֶ�����:����ʱ��
	private Date gmtCreate;

	// �ֶ�����:������
	private String creator;

	// �ֶ�����:����޸�ʱ��
	private Date gmtModify;

	// �ֶ�����:����޸���
	private String modifier;

	/**
	 * ��֤���Ŀ����Ƿ���ȷ
	 */
	public boolean isPasswordCorrect() {
		if (loginPassword == null || unencryptPassword == null) {
			throw new IllegalStateException(
					"password and unencryptPassword must be set");
		}
		String md5 = MD5Encrypt.encode(unencryptPassword);
		return md5.equals(loginPassword);
	}

	public String getLoginPassword() {
		if (this.loginPassword == null) {
			this.loginPassword = MD5Encrypt.encode(this.unencryptPassword);
		}
		return this.loginPassword;
	}

	public boolean isUserNormalStatus() {
		return this.userStatus != null && this.userStatus.equals(StatusNormal);
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return this.modifier;
	}

	public String getUnencryptPassword() {
		return unencryptPassword;
	}

	public void setUnencryptPassword(String unencryptPassword) {
		this.unencryptPassword = unencryptPassword;
	}

	public void setNewPassword(String string) {
		this.loginPassword = MD5Encrypt.encode(string);
	}

}