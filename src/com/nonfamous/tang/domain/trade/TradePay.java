package com.nonfamous.tang.domain.trade;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * ����֧��domain��
 * </pre>
 * 
 * @version $IdTradePay.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradePay extends DomainBase {

	/** ֧���ɹ� */
	public static final String PAY_STATUS_SUCCESS = "S";

	/** ֧��ʧ�� */
	public static final String PAY_STATUS_FAIL = "F";

	/** ��ʼ�� */
	public static final String PAY_STATUS_INIT = "I";

	/** ת�˳�ʼ�� */
	public static final String TRANS_STATUS_INIT = "I";

	/** ת�˳ɹ� */
	public static final String TRANS_STATUS_SUCCESS = "S";

	private static final long serialVersionUID = 7100756443819422661L;

	// �ֶ�����:�ɹ������
	private Long id;

	// �ֶ�����:������
	private String orderNo;

	// �ֶ�����:�������
	private String buyerName;

	// �ֶ�����:
	private String sellerName;

	// ��������
	private String shopName;

	// �ֶ�����:��������ʺţ�Ŀǰϵͳ����û����������ʺŵģ���������ֶο���Ϊ��
	private String buyerAccount;

	// �ֶ�����:֧������
	private String buyerBank;

	// �ֶ�����:���������ʺţ��������û�������ʺ�������֧��
	private String sellerAccount;

	// �ֶ�����:
	private String sellerBank;

	// �ֶ�����:��ұ��
	private String buyerId;

	// �ֶ�����:���ұ��
	private String sellerId;

	// �ֶ�����:
	private Long payFee;

	// �ֶ�����:֧��״̬,I(��ʼ��)֧���ɹ������ɹ�S(�ɹ�)F( ʧ��)
	private String payStatus;

	// �ֶ�����:ת��״̬��S(�ɹ�)I( δת��)
	private String transStatus;

	// �ֶ�����:
	private Date payDate;

	// �ֶ�����:
	private String bankNo;

	// �ֶ�����:������
	private String creator;

	// �ֶ�����:����޸�ʱ��
	private Date gmtModify;

	// �ֶ�����:����޸���
	private String modifier;

	// �ֶ�����:����ʱ��
	private Date gmtCreate;

	// ת��ʱ��
	private Date transDate;

	// ֧���ɹ�ʱ��
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