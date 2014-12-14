package com.nonfamous.commom.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址信息
 * 
 * @author daodao
 * @version $Id: AddressInfo.java,v 1.1 2008/07/11 00:47:11 fred Exp $
 */
public class AddressInfo implements Serializable {
	private static final long serialVersionUID = 7954794226778186143L;

	/** 邮政编码 */
	private String postcode;

	/** 地区编码，唯一标识一个地区的 */
	private String areaCode;

	/** 上一级地区编码 */
	private String parentAreaCode;

	/** 省份 */
	private String name;

	/** 下属的区域，如果当前是省，则该字段为所有的市，如果当前是市，则该字段为所有的区 */
	private List<AddressInfo> childList;

	/** 该地区是否可用 */
	private boolean disabled = false;

	/**
	 * 构造器。
	 * 
	 * @param postcode
	 * @param province
	 * @param city
	 * @param logistics
	 */
	public AddressInfo(String areaCode, String name, String parent,
			String postcode) {
		super();

		this.postcode = postcode;
		this.name = name;
		this.parentAreaCode = parent;
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List getChildList() {
		return childList;
	}

	public void setChildList(List<AddressInfo> childList) {
		this.childList = childList;
	}

	/**
	 * 增加一个下属的地区
	 * 
	 * @param child
	 */
	public void addChild(AddressInfo child) {
		if (childList == null) {
			childList = new ArrayList<AddressInfo>();
		}
		childList.add(child);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentAreaCode() {
		return parentAreaCode;
	}

	public void setParentAreaCode(String parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
