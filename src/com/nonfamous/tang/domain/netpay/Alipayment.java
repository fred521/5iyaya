package com.nonfamous.tang.domain.netpay;

public class Alipayment extends NetPayBase {

	/**
	 * alipay entities
	 */
	private static final long serialVersionUID = -6731464908458215047L;

	private String payGateway;

	private String service;

	private String signType;

	private String outTradeNo;

	private String inputCharset;

	private String partner;

	private String key;

	private String sellerEmail;

	private String body;

	private String subject;

	private String price;

	private String quantity;

	private String showUrl;

	private String paymentType;

	private String discount;

	private String logisticsType;

	private String logisticsFee;

	private String logisticsPayment;

	private String returnUrl;

	private String notifyUrl;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(String logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	public String getLogisticsPayment() {
		return logisticsPayment;
	}

	public void setLogisticsPayment(String logisticsPayment) {
		this.logisticsPayment = logisticsPayment;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPayGateway() {
		return payGateway;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
