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

	// 字段描述:搜索关键字名称/页面搜索中需要控制文字数量
	private java.lang.String keyName;

	// 字段描述:搜索关键字类型:1、商品、2、店铺、3、店主、4、资讯信息
	private java.lang.String keyType;

	// 字段描述:该关键字搜索次数
	private java.lang.Long searchCount;

	// 字段描述:关键字编号
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