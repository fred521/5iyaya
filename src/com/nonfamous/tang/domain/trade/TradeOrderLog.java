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

	// 字段描述:会员编号
	private java.lang.Long id;

	// 字段描述:商品id
	private java.lang.String orderNo;

	// 字段描述:当前订单状态，可以用于记录订单历史状态
	private java.lang.String status;

	// 字段描述:买卖方：买家B，卖家S；标识是卖家或买家修改
	private java.lang.String memberType;

	// 字段描述:修改前的金额
	private java.lang.Long payFee;

	// 字段描述:修改内容内容
	private java.lang.String memo;

	// 字段描述:创建人
	private java.lang.String creator;

	// 字段描述:最后修改时间
	private java.util.Date gmtModify;

	// 字段描述:最后修改人
	private java.lang.String modifier;

	// 字段描述:创建时间
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