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

	// 字段描述:管理员编号
	private Long userId;

	// 字段描述:管理员登陆名称
	private String loginName;

	// 字段描述:管理员登陆密码,md5加密后的，也就是数据库存储的数据
	private String loginPassword;

	// 命名口令，加密前
	private String unencryptPassword;

	// 字段描述:管理员状态:N正常、P禁用
	private String userStatus = StatusNormal;

	// 字段描述:联系电话
	private String phone;

	// 字段描述:创建时间
	private Date gmtCreate;

	// 字段描述:创建人
	private String creator;

	// 字段描述:最后修改时间
	private Date gmtModify;

	// 字段描述:最后修改人
	private String modifier;

	/**
	 * 验证明文口令是否正确
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