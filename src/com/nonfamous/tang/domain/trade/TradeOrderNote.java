package com.nonfamous.tang.domain.trade;

import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeOrderNote.java Thu Sep 13 23:20:41 CST 2007 alan $
 */
public class TradeOrderNote extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7889099834579566469L;

	public static final String TypeBuyer = "B";

	public static final String TypeSeller = "S";

	// �ֶ�����:
	private java.lang.Long id;

	// �ֶ�����:��Ʒid
	private java.lang.String orderNo;

	// �ֶ�����:�����������B������S����ʶ�����һ��������
	private java.lang.String memberType;

	// �ֶ�����:��������
	private java.lang.String memo;

	// �ֶ�����:������
	private java.lang.String creator;

	// �ֶ�����:����޸�ʱ��
	private java.util.Date gmtModify;

	// �ֶ�����:����޸���
	private java.lang.String modifier;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	public TradeOrderNote() {
		super();
	}

	public TradeOrderNote(TradeOrder order, String memberId, String note) {
		super();
		this.setCreator(memberId);
		this.setMemberType(order.isBuyer(memberId) ? TradeOrderNote.TypeBuyer
				: TradeOrderNote.TypeSeller);
		this.setMemo(HtmlUtils.parseHtml(note));
		this.setOrderNo(order.getOrderNo());
	}

	/**
	 * �Ƿ��������
	 * 
	 * @return
	 */
	public boolean isBuyer() {
		return this.memberType != null && this.memberType.equals(TypeBuyer);
	}

	/**
	 * �Ƿ���������
	 * 
	 * @return
	 */
	public boolean isSeller() {
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

	public void setMemberType(java.lang.String memberType) {
		this.memberType = memberType;
	}

	public java.lang.String getMemberType() {
		return this.memberType;
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