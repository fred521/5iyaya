package com.nonfamous.commom.util.web;

/**
 * @author eyeieye
 * 对string的一个封装，对此类来说，"" , " " 这样的字符串都等同于null. 
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
	 * 得到string值，如果RequestValue.isNull()，则返回null
	 * @return
	 */
	public String getString() {
		return value;
	}

	/**
	 * 得到int值，如果RequestValue.isNull()，则返回0
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
