package com.nonfamous.tang.domain.group;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;


/**��ṹ
 * `goods_id` varchar(32) NOT NULL,
  `seller_id` varchar(32) NOT NULL,
  `buyer_id` varchar(32) NOT NULL,
  `gmt_modify` datetime default NULL,
  `gmt_create` datetime NOT NULL,
  `real_price` int(11) default NULL,
  `checked_nums` int(10) default NULL COMMENT 'ʵ���µ�����',
  `order_status` varchar(32) default NULL COMMENT '���׶�����״̬',
 * @author fred
 *
 */
public class GroupActivity extends DomainBase {
	
	private static final long serialVersionUID = 1L;

	private java.lang.String goodsId;
	
	// �ֶ�����:��ұ��
	private String buyerId;

	// �ֶ�����:���ұ��
	private String sellerId;
	// �����Ľ���״̬
	private String orderStatus;
	// �ֶ�����:������
	private String creator;

	// �ֶ�����:����޸�ʱ��
	private Date gmtModify;

	// �ֶ�����:����޸���
	private String modifier;

	// �ֶ�����:����ʱ��
	private Date gmtCreate;
	
	// �����µ��ļ۸�
	private java.lang.Long realPrice;
	//һ���Ź������µļ���
	private java.lang.Long checkedNum;
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public java.lang.Long getCheckedNum() {
		return checkedNum;
	}
	public void setCheckedNum(java.lang.Long checkedNum) {
		this.checkedNum = checkedNum;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
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
	public java.lang.String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public java.lang.Long getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(java.lang.Long realPrice) {
		this.realPrice = realPrice;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	

}
