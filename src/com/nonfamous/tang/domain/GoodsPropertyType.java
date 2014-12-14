package com.nonfamous.tang.domain;

import java.util.List;

import com.nonfamous.tang.domain.base.DomainBase;



public class GoodsPropertyType extends DomainBase {
	private int id;
	private int valueType;
	private String name;
	private String displayName;
	
	private final static int MULTITYPE=1;
	private final static int SINGLETYPE=0;
	private List<GoodsProperty> propertys;	
	
	public List<GoodsProperty> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<GoodsProperty> propertys) {
		this.propertys = propertys;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValueType() {
		return valueType;
	}
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public static int getMULTITYPE() {
		return MULTITYPE;
	}
	public static int getSINGLETYPE() {
		return SINGLETYPE;
	} 
	
}
