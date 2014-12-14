package com.nonfamous.commom.util.web;

/**
 * @author eyeieye
 * ��string��һ����װ���Դ�����˵��"" , " " �������ַ�������ͬ��null. 
 * this.value = value.trim
 */
public class RequestValue {
	private String value;

	RequestValue(String value) {
		super();
		initValue(value);
	}

	private void initValue(String v) {
		if (v == null) {
			return;
		}
		v = v.trim();
		if (v.length() == 0) {
			return;
		}
		this.value = v;
	}

	public boolean isNotNull() {
		return this.value != null;
	}

	public boolean isNull() {
		return this.value == null;
	}

	/**
	 * �õ�stringֵ�����RequestValue.isNull()���򷵻�null
	 * @return
	 */
	public String getString() {
		return value;
	}

	/**
	 * �õ�intֵ�����RequestValue.isNull()���򷵻�0
	 * @return
	 */
	public int getInt() {
		if(this.isNull()){
			return 0;
		}
		return Integer.parseInt(this.value);
	}
	
	public long getLong(){
		if(this.isNull()){
			return 0;
		}
		return Long.parseLong(this.value);
	}
	
	public double getDouble(){
		if(this.isNull()){
			return 0;
		}
		return Double.parseDouble(this.value);
	}
}
