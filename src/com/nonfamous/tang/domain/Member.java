package com.nonfamous.tang.domain;

import java.util.Date;

import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * ��Ա��Ϣ��
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

	// �ֶ�����:����½��ip
	private String lastLoginIp;

	// �ֶ�����:��Ա���
	private String memberId;

	// �ֶ�����:�ǳƣ���ʾ��
	private String nick;

	// �ֶ�����:������Ҫmd5����
	private String loginPassword;

	// �ֶ�����:�ֻ����벻��Ϊ�գ���Ҫͨ���ֻ�������֤
	private String mobile;

	// �ֶ�����:�����ʼ���ַ
	private String email;

	// �ֶ�����:��ʵ����
	private String name;

	// �ֶ�����:��Ա���ͣ����B������S���������в�ͬ�Ļ�ԱȨ��
	private String memberType;

	// �ֶ�����:�Ա�M �С�FŮ
	private String sex;

	// �ֶ�����:���ڵ���������ʡ����������������ϳ�һ����������
	private String areaCode;

	// �ֶ�����:�̶��绰
	private String phone;

	// �ֶ�����:��������
	private String postCode;

	// �ֶ�����:��ϵ��ַ
	private String address;

	// �ֶ�����:��Ա״̬������P������N,Dɾ���������Ա�����������ܵ�½
	private String status;

	// �ֶ�����:�ֻ�У�飺ͨ��Y���ȴ���֤W
	private String phoneValidate;

	// �ֶ�����:ע��ʱ��
	private Date gmtRegister;

	// �ֶ�����:
	private Date gmtCreate;

	// �ֶ�����:
	private String creator;

	// �ֶ�����:
	private Date gmtModify;

	// �ֶ�����:
	private String modifier;

	// �ֶ�����:����½ʱ��
	private Date gmtLastLogin;

	// �ֶ�����:��½����
	private long loginCount;

	// �ֶ�����:��Ա��½id�����������ġ�Ψһ�����޸�
	private String loginId;

	// �ֶ�����:ע��ʱ��ip
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