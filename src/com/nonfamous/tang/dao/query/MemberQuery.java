package com.nonfamous.tang.dao.query;

/**
 * <p>
 * ����������Ա��Ϣ��Query��
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberQuery.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */
public class MemberQuery extends QueryBase {
	private static final long serialVersionUID = 1167758376079352200L;

	// ��Ա�û���
	private String loginId;

	// ��Ա��ʵ����
	private String realName;

	// ��Ա�ǳ�
	private String nick;

	// �ֻ�����
	private String mobile;

	// ��Ա��ַ��������
	private String area;

	// �ֻ���֤״̬
	private String phoneValidate;

	// �Ա�
	private String sex;

	// ��Ա���ͣ���� or ���ң�
	private String memberType;

	// ��������
	private String shopName;

	// ��������
	private String shopOwner;

	// �����г�
	private String belongMarketId;
	
	private String email;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getBlurLoginId() {
		return this.getSQLBlurValue(loginId);
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getMobile() {
		return mobile;
	}
	
	public String getBlurMobile() {
		return this.getSQLBlurValue(mobile);
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNick() {
		return nick;
	}

	public String getBlurNick() {
		return this.getSQLBlurValue(nick);
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhoneValidate() {
		return phoneValidate;
	}

	public void setPhoneValidate(String phoneValidate) {
		this.phoneValidate = phoneValidate;
	}

	public String getRealName() {
		return realName;
	}

	public String getBlurRealName() {
		return this.getSQLBlurValue(realName);
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBelongMarketId() {
		return belongMarketId;
	}

	public void setBelongMarketId(String belongMarketId) {
		this.belongMarketId = belongMarketId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getShopName() {
		return shopName;
	}

	public String getBlurShopName() {
		return this.getSQLBlurValue(shopName);
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopOwner() {
		return shopOwner;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBlurShopOwner() {
		return this.getSQLBlurValue(shopOwner);
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public String getHasShopQuery() {
		if ((shopName != null || shopOwner != null || belongMarketId != null)) {
			return "Y";
		}
		return "N";
	}

	public boolean hasMemberQueryInfo() {
		return ((loginId != null) || (nick != null) || (realName != null));
	}

	public boolean hasShopQueryInfo() {
		return (shopName != null);
	}
	
	
}
