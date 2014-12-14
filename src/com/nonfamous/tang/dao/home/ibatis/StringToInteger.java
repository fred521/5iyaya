package com.nonfamous.tang.dao.home.ibatis;

public class StringToInteger {
	private String key;

	private Integer value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public StringToInteger(String key, Integer value) {
		super();
		this.key = key;
		this.value = value;
	}
}