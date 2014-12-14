package com.nonfamous.tang.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * 买家地址
 * 
 * @author: liujun
 */
public class Address extends DomainBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4750928593214594343L;
	
	//地址状态
	public static final String NORMAL_STATUS = "N";
	
	public static final String TEMP_STATUS = "T";
	
	//字段描述:地址编号
	private String addressId;
	// 字段描述:会员编号
	private String memberId;
	//字段描述:收货人
	private String consignee;
	//字段描述:收货地址
	private String streetAddress;
	//字段描述:所在地区代号
	private String areaCode;
	//字段描述:所在地区
	private String areaCodeStr;
	
	//字段描述:电话号码
	private String phone;
	//字段描述:手机号码
	private String mobile;
	//字段描述:地址状态，'T' 代表临时地址 'N' 代表正常的地址地址
	private String status;
	//字段描述:邮政编码
	private String postCode;
	
	// 字段描述:
	private Date gmtCreate;
	// 字段描述:
	private Date gmtModify;
	// 字段描述:
	private String modifier;
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	public String getAreaCodeStr() {
		return areaCodeStr;
	}
	public void setAreaCodeStr(String areaCodeStr) {
		this.areaCodeStr = areaCodeStr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(13, 37)
			.append(this.addressId.hashCode())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(!(o instanceof Address))
			return false;
		
		Address other = (Address)o;
		return new EqualsBuilder()
			.append(this.addressId, other.addressId)
			.isEquals();
			
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this)
			.append(this.addressId)
			.append(this.areaCode)
			.append(this.streetAddress)
			.append(this.consignee)
			.append(this.postCode)
			.append(this.phone)
			.append(this.mobile)
			.append(this.status)
			.toString();
	}
	
	public String getFullAddress(){
		return new StringBuilder()
		    .append(this.consignee + " " + "手机：")
		    .append(this.mobile + " ")
			.append(this.areaCodeStr + " ")
			.append(this.streetAddress + "(")
			.append(this.areaCode + ")")
			.toString();
	}
	
	public String convertAddressToOrderFormat(){
		return new StringBuilder()
		    .append(this.consignee + "|" + "手机：")
		    .append(this.mobile + "|")
			.append(this.areaCodeStr + "|")
			.append(this.streetAddress + "|")
			.append(this.areaCode)
			.toString();
	}
	

	
}
