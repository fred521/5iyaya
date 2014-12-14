package com.nonfamous.commom.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ַ��Ϣ
 * 
 * @author daodao
 * @version $Id: AddressInfo.java,v 1.1 2008/07/11 00:47:11 fred Exp $
 */
public class AddressInfo implements Serializable {
	private static final long serialVersionUID = 7954794226778186143L;

	/** �������� */
	private String postcode;

	/** �������룬Ψһ��ʶһ�������� */
	private String areaCode;

	/** ��һ���������� */
	private String parentAreaCode;

	/** ʡ�� */
	private String name;

	/** ���������������ǰ��ʡ������ֶ�Ϊ���е��У������ǰ���У�����ֶ�Ϊ���е��� */
	private List<AddressInfo> childList;

	/** �õ����Ƿ���� */
	private boolean disabled = false;

	/**
	 * ��������
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
	 * ����һ�������ĵ���
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
