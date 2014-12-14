package com.nonfamous.tang.bank.comm;

import com.nonfamous.tang.domain.base.DomainBase;
import com.nonfamous.tang.domain.trade.TradeOrder;

public class BankCommData extends DomainBase {

	private static final long serialVersionUID = 6812692405725494258L;

	private static final String Separator = "|";

	private static final String EmptyString = "";

	String interfaceVersion = "1.0.0.0";

	String merID;

	String orderid;

	String orderDate;

	String orderTime;

	String tranType = "0";

	String amount;

	String curType = "CNY";

	String orderContent;

	String orderMono;

	String phdFlag = "0";

	String notifyType = "1";

	String merURL;

	String goodsURL;

	String jumpSeconds = "5";

	String payBatchNo;

	String proxyMerName;

	String proxyMerType;

	String proxyMerCredentials;

	String netType = "0";

	public BankCommData() {
		super();
	}

	public BankCommData(TradeOrder order) {
		super();
		this.amount = order.getMoney(order.getPayFee()).toString();
		this.orderid = order.getOrderNo();
		this.orderDate = order.getOrderDate();
	}

	public String getSourceMsg() {
		StringBuilder sb = new StringBuilder();
		sb.append(interfaceVersion).append(Separator);
		sb.append(merID).append(Separator);
		sb.append(orderid).append(Separator);
		sb.append(orderDate).append(Separator);
		sb.append(defaultEmpty(orderTime)).append(Separator);
		sb.append(tranType).append(Separator);
		sb.append(amount).append(Separator);
		sb.append(curType).append(Separator);
		sb.append(defaultEmpty(orderContent)).append(Separator);
		sb.append(defaultEmpty(orderMono)).append(Separator);
		sb.append(defaultEmpty(phdFlag)).append(Separator);
		sb.append(notifyType).append(Separator);
		sb.append(defaultEmpty(merURL)).append(Separator);
		sb.append(defaultEmpty(goodsURL)).append(Separator);
		sb.append(defaultEmpty(jumpSeconds)).append(Separator);
		sb.append(defaultEmpty(payBatchNo)).append(Separator);
		sb.append(defaultEmpty(proxyMerName)).append(Separator);
		sb.append(defaultEmpty(proxyMerType)).append(Separator);
		sb.append(defaultEmpty(proxyMerCredentials)).append(Separator);
		sb.append(netType);
		return sb.toString();
	}

	public String defaultEmpty(String s) {
		if (s == null) {
			return EmptyString;
		}
		return s;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getGoodsURL() {
		return goodsURL;
	}

	public void setGoodsURL(String goodsURL) {
		this.goodsURL = goodsURL;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getJumpSeconds() {
		return jumpSeconds;
	}

	public void setJumpSeconds(String jumpSeconds) {
		this.jumpSeconds = jumpSeconds;
	}

	public String getMerID() {
		return merID;
	}

	public void setMerID(String merID) {
		this.merID = merID;
	}

	public String getMerURL() {
		return merURL;
	}

	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderMono() {
		return orderMono;
	}

	public void setOrderMono(String orderMono) {
		this.orderMono = orderMono;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayBatchNo() {
		return payBatchNo;
	}

	public void setPayBatchNo(String payBatchNo) {
		this.payBatchNo = payBatchNo;
	}

	public String getPhdFlag() {
		return phdFlag;
	}

	public void setPhdFlag(String phdFlag) {
		this.phdFlag = phdFlag;
	}

	public String getProxyMerCredentials() {
		return proxyMerCredentials;
	}

	public void setProxyMerCredentials(String proxyMerCredentials) {
		this.proxyMerCredentials = proxyMerCredentials;
	}

	public String getProxyMerName() {
		return proxyMerName;
	}

	public void setProxyMerName(String proxyMerName) {
		this.proxyMerName = proxyMerName;
	}

	public String getProxyMerType() {
		return proxyMerType;
	}

	public void setProxyMerType(String proxyMerType) {
		this.proxyMerType = proxyMerType;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
}
