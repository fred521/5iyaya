package com.nonfamous.tang.domain.trade;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * 银行支付domain类
 * </pre>
 * 
 * @version $IdTradePay.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradePay extends DomainBase {

	/** 支付成功 */
	public static final String PAY_STATUS_SUCCESS = "S";

	/** 支付失败 */
	public static final String PAY_STATUS_FAIL = "F";

	/** 初始化 */
	public static final String PAY_STATUS_INIT = "I";

	/** 转账初始化 */
	public static final String TRANS_STATUS_INIT = "I";

	/** 转账成功 */
	public static final String TRANS_STATUS_SUCCESS = "S";

	private static final long serialVersionUID = 7100756443819422661L;

	// 字段描述:采购单编号
	private Long id;

	// 字段描述:订单号
	private String orderNo;

	// 字段描述:买家姓名
	private String buyerName;

	// 字段描述:
	private String sellerName;

	// 店铺名称
	private String shopName;

	// 字段描述:买家银行帐号，目前系统中是没有买家银行帐号的，所以这个字段可以为空
	private String buyerAccount;

	// 字段描述:支付银行
	private String buyerBank;

	// 字段描述:卖家银行帐号：如果卖家没有银行帐号则不允许支付
	private String sellerAccount;

	// 字段描述:
	private String sellerBank;

	// 字段描述:买家编号
	private String buyerId;

	// 字段描述:卖家编号
	private String sellerId;

	// 字段描述:
	private Long payFee;

	// 字段描述:支付状态,I(初始化)支付成功订单成功S(成功)F( 失败)
	private String payStatus;

	// 字段描述:转帐状态：S(成功)I( 未转帐)
	private String transStatus;

	// 字段描述:
	private Date payDate;

	// 字段描述:
	private String bankNo;

	// 字段描述:创建人
	private String creator;

	// 字段描述:最后修改时间
	private Date gmtModify;

	// 字段描述:最后修改人
	private String modifier;

	// 字段描述:创建时间
	private Date gmtCreate;

	// 转账时间
	private Date transDate;

	// 支付成功时间
	private Date paySuccessDate;
	
	private String payBank;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerName() {
		return this.buyerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getBuyerAccount() {
		return this.buyerAccount;
	}

	public void setBuyerBank(String buyerBank) {
		this.buyerBank = buyerBank;
	}

	public String getBuyerBank() {
		return this.buyerBank;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String getSellerAccount() {
		return this.sellerAccount;
	}

	public void setSellerBank(String sellerBank) {
		this.sellerBank = sellerBank;
	}

	public String getSellerBank() {
		return this.sellerBank;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerId() {
		return this.buyerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}

	public Long getPayFee() {
		return this.payFee;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransStatus() {
		return this.transStatus;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public Date getPaySuccessDate() {
		return paySuccessDate;
	}

	public void setPaySuccessDate(Date paySuccessDate) {
		this.paySuccessDate = paySuccessDate;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPayBank() {
		return payBank;
	}

	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	
}