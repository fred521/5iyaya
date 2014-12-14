package com.nonfamous.tang.domain.trade;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeOrderLog.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradeOrderLog extends DomainBase {

	public static final String TypeBuyer = "B";

	public static final String TypeSeller = "S";

	private static final long serialVersionUID = 5767249793450092681L;

	// �ֶ�����:��Ա���
	private java.lang.Long id;

	// �ֶ�����:��Ʒid
	private java.lang.String orderNo;

	// �ֶ�����:��ǰ����״̬���������ڼ�¼������ʷ״̬
	private java.lang.String status;

	// �ֶ�����:�����������B������S����ʶ�����һ�����޸�
	private java.lang.String memberType;

	// �ֶ�����:�޸�ǰ�Ľ��
	private java.lang.Long payFee;

	// �ֶ�����:�޸���������
	private java.lang.String memo;

	// �ֶ�����:������
	private java.lang.String creator;

	// �ֶ�����:����޸�ʱ��
	private java.util.Date gmtModify;

	// �ֶ�����:����޸���
	private java.lang.String modifier;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	public TradeOrderLog() {
		super();
	}

	public TradeOrderLog(TradeOrder order, String memberId, String log) {
		super();
		this.setCreator(memberId);
		this.setMemberType(order.isBuyer(memberId) ? TradeOrderLog.TypeBuyer
				: TradeOrderLog.TypeSeller);
		this.setMemo(log);
		this.setOrderNo(order.getOrderNo());
		this.setPayFee(order.getPayFee());
		this.setStatus(order.getStatus());
	}

	public boolean isBuyerModify() {
		return this.memberType != null && this.memberType.equals(TypeBuyer);
	}

	public boolean isSellerModify() {
		return this.memberType != null && this.memberType.equals(TypeSeller);
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	public java.lang.String getOrderNo() {
		return this.orderNo;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public void setMemberType(java.lang.String memberType) {
		this.memberType = memberType;
	}

	public java.lang.String getMemberType() {
		return this.memberType;
	}

	public void setPayFee(java.lang.Long payFee) {
		this.payFee = payFee;
	}

	public java.lang.Long getPayFee() {
		return this.payFee;
	}

	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}

	public java.lang.String getMemo() {
		return this.memo;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	public java.lang.String getCreator() {
		return this.creator;
	}

	public void setGmtModify(java.util.Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public java.util.Date getGmtModify() {
		return this.gmtModify;
	}

	public void setModifier(java.lang.String modifier) {
		this.modifier = modifier;
	}

	public java.lang.String getModifier() {
		return this.modifier;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}

}