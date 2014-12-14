package com.nonfamous.tang.domain;

import java.util.Date;

import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * 会员信息类
 * </pre>
 * 
 * @version $Id: Member.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class Member extends DomainBase {

	private static final long serialVersionUID = 33581860199036730L;

	public static final String STATUS_DISABLE = "P";

	public static final String STATUS_NORMAL = "N";

	public static final String STATUS_DELETED = "D";

	public static final String FLAG_BUYER = "B";

	public static final String FLAG_SELLER = "S";

	public static final String MOBILE_VALIDATE_PASS = "Y";

	public static final String MOBILE_VALIDATE_WAIT = "W";
	
	public static final String MOBILE_VALIDATE_AGAIN = "A";

	// 字段描述:最后登陆的ip
	private String lastLoginIp;

	// 字段描述:会员编号
	private String memberId;

	// 字段描述:昵称：显示用
	private String nick;

	// 字段描述:密码需要md5加密
	private String loginPassword;

	// 字段描述:手机号码不能为空，需要通过手机号码验证
	private String mobile;

	// 字段描述:电子邮件地址
	private String email;

	// 字段描述:真实姓名
	private String name;

	// 字段描述:会员类型：买家B，卖家S；买卖家有不同的会员权限
	private String memberType;

	// 字段描述:性别：M 男、F女
	private String sex;

	// 字段描述:所在地区：根据省、地区、市三级组合成一个地区编码
	private String areaCode;

	// 字段描述:固定电话
	private String phone;

	// 字段描述:邮政编码
	private String postCode;

	// 字段描述:联系地址
	private String address;

	// 字段描述:会员状态：禁用P，正常N,D删除；如果会员被禁用怎不能登陆
	private String status;

	// 字段描述:手机校验：通过Y，等待验证W
	private String phoneValidate;

	// 字段描述:注册时间
	private Date gmtRegister;

	// 字段描述:
	private Date gmtCreate;

	// 字段描述:
	private String creator;

	// 字段描述:
	private Date gmtModify;

	// 字段描述:
	private String modifier;

	// 字段描述:最后登陆时间
	private Date gmtLastLogin;

	// 字段描述:登陆次数
	private long loginCount;

	// 字段描述:会员登陆id、不能有中文、唯一不可修改
	private String loginId;

	// 字段描述:注册时的ip
	private String registerIp;

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public void setMD5LoginPassword(String loginPassword) {
		this.loginPassword = MD5Encrypt.encode(loginPassword);
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setPhoneValidate(String phoneValidate) {
		this.phoneValidate = phoneValidate;
	}

	public String getPhoneValidate() {
		return this.phoneValidate;
	}

	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}

	public Date getGmtRegister() {
		return this.gmtRegister;
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

	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}

	public Date getGmtLastLogin() {
		return this.gmtLastLogin;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	public long getLoginCount() {
		return this.loginCount;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getRegisterIp() {
		return this.registerIp;
	}
	
	public boolean isNotFull() {
		return (nick == null || mobile == null || email == null || name == null
				|| sex == null || areaCode == null || phone == null
				|| postCode == null || address == null );
	}

}