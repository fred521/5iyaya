package com.nonfamous.tang.domain.trade;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $IdTradeOrderItem.java Thu Sep 13 23:20:41 CST 2007 fred $
 */
public class TradeOrderItem extends DomainBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5140045607052800308L;

	// �ֶ�����:
	private java.lang.Long id;

	// �ֶ�����:
	private java.lang.String orderNo;

	// �ֶ�����:��Ʒid
	private java.lang.String goodsId;

	// �ֶ�����:����id
	private java.lang.String shopId;

	// �ֶ�����:��Ʒ����
	private java.lang.String goodsName;

	// �ֶ�����:��Ʒ�����򵥸����ۣ���λ��
	private java.lang.Long batchPrice;

	// �ֶ�����:������Ʒ����
	private java.lang.Long totalNum;

	// �ֶ�����:����Ʒ������=(��Ʒ����/ ��Ʒ��������)*��Ʒ�����򵥸��۸�
	private java.lang.Long totalFree;

	// �ֶ�����:������
	private java.lang.String creator;

	// �ֶ�����:����޸�ʱ��
	private java.util.Date gmtModify;

	// �ֶ�����:����޸���
	private java.lang.String modifier;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;
	
	//trade_order ��Ķ���״̬
	private String orderStatus;
	//��ҵ�ID
	private String buyerLoginId;
	//�µ�ʱ��
	private String orderDate;


	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	private static final boolean nullAbleLongCompare(Long a, Long b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.longValue() == b.intValue();
	}

	private static final String PointeTo = "->";

	/**
	 * ����item�������ԣ���������б仯������Ҫ��¼�仯����������ҷ��أ����û�б仯������null
	 * 
	 * @param newTotalNum
	 * @param newBatchPrice
	 * @param newTotalFree
	 * @return
	 */
	public String getLog(Long newTotalNum, Long newBatchPrice, Long newTotalFree) {
		StringBuilder sb = new StringBuilder("��Ʒ:[")
				.append(this.getGoodsName());
		boolean changed = false;
		if (!nullAbleLongCompare(this.totalNum, newTotalNum)) {
			changed = true;
			sb.append(" ����").append(this.totalNum == null ? 0L : this.totalNum);
			sb.append(PointeTo).append(newTotalNum == null ? 0L : newTotalNum);
			this.totalNum = newTotalNum;
		}
		if (!nullAbleLongCompare(this.batchPrice, newBatchPrice)) {
			changed = true;
			sb.append(" ����").append(
					this.batchPrice == null ? 0L : getMoney(this.batchPrice).toString());
			sb.append(PointeTo).append(
					newBatchPrice == null ? 0L : getMoney(newBatchPrice).toString());
			this.batchPrice = newBatchPrice;
		}
		if (!nullAbleLongCompare(this.totalFree, newTotalFree)) {
			changed = true;
			sb.append(" ���").append(
					this.totalFree == null ? 0L : getMoney(this.totalFree));
			sb.append(PointeTo)
					.append(newTotalFree == null ? 0L : getMoney(newTotalFree));
			this.totalFree = newTotalFree;
		}
		if (!changed) {
			return null;
		}
		sb.append(']');
		return sb.toString();
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

	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}

	public java.lang.String getGoodsId() {
		return this.goodsId;
	}

	public void setShopId(java.lang.String shopId) {
		this.shopId = shopId;
	}

	public java.lang.String getShopId() {
		return this.shopId;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	public java.lang.String getGoodsName() {
		return this.goodsName;
	}

	public void setBatchPrice(java.lang.Long batchPrice) {
		this.batchPrice = batchPrice;
	}

	public java.lang.Long getBatchPrice() {
		return this.batchPrice;
	}

	public void setTotalNum(java.lang.Long totalNum) {
		this.totalNum = totalNum;
	}

	public java.lang.Long getTotalNum() {
		return this.totalNum;
	}

	public void setTotalFree(java.lang.Long totalFree) {
		this.totalFree = totalFree;
	}

	public java.lang.Long getTotalFree() {
		return this.totalFree;
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

	public String getBuyerLoginId() {
		return buyerLoginId;
	}

	public void setBuyerLoginId(String buyerLoginId) {
		this.buyerLoginId = buyerLoginId;
	}


}