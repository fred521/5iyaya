package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: SearchKeyWord.java,v 1.1 2008/07/11 00:47:00 fred Exp $
 */
public class SearchKeyWord extends DomainBase {

	public static final String KeyTypeGoods = "goods";

	public static final String KeyTypeShop = "shop";

	public static final String KeyTypeMember = "member";

	public static final String KeyTypeNews = "news";

	private static final long serialVersionUID = 6355981846031021109L;

	// �ֶ�����:�����ؼ�������/ҳ����������Ҫ������������
	private java.lang.String keyName;

	// �ֶ�����:�����ؼ�������:1����Ʒ��2�����̡�3��������4����Ѷ��Ϣ
	private java.lang.String keyType;

	// �ֶ�����:�ùؼ�����������
	private java.lang.Long searchCount;

	// �ֶ�����:�ؼ��ֱ��
	private java.lang.Long keyId;

	public void setKeyName(java.lang.String keyName) {
		this.keyName = keyName;
	}

	public java.lang.String getKeyName() {
		return this.keyName;
	}

	public void setKeyType(java.lang.String keyType) {
		this.keyType = keyType;
	}

	public java.lang.String getKeyType() {
		return this.keyType;
	}

	public void setSearchCount(java.lang.Long searchCount) {
		this.searchCount = searchCount;
	}

	public java.lang.Long getSearchCount() {
		return this.searchCount;
	}

	public void setKeyId(java.lang.Long keyId) {
		this.keyId = keyId;
	}

	public java.lang.Long getKeyId() {
		return this.keyId;
	}

}