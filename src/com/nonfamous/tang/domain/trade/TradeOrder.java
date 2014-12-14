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
 * @version $IdTradeOrder.java Thu Sep 13 23:20:41 CST 2007 fred $
 */
public class TradeOrder extends DomainBase {

	public static final String StatusInit = "ORDER_INIT";

	public static final String StatusWaitSellerConfirm = "WAIT_SELLER_CONFIRM";

	public static final String StatusWaitBuyerConfirm = "WAIT_BUYER_CONFIRM";

	public static final String StatusOrderClose = "ORDER_CLOSE";

	public static final String StatusWaitPay = "WAIT_PAY";

	public static final String StatusShipping = "SHIPPING";
	
	public static final String StatusOrderSuccess = "ORDER_SUCCESS";

	/**
	 * ���ٶ���
	 */
	public static final String TypeQuick = "F";

	/**
	 * ���׶���
	 */
	public static final String TypeTrade = "T";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5948187550441088251L;

	// �ֶ�����:������20070910(����)+8Ϊ���к�
	private String orderNo;

	// �ֶ�����:����id
	private String shopId;

	// �ֶ�����:��������
	private String shopName;

	// �ֶ�����:��������
	private Long payFee;
	
	private String address;

	/*
	 * �ֶ�����:
	 * ����״̬�� 
	 * 	������ʼ״̬ ORDER_INIT 
	 * 	�ȴ�����ȷ�� WAIT_SELLER_CONFIRM 
	 * 	�ȴ����ȷ�� WAIT_BUYER_CONFIRM 
	 * 	�������� ORDER_CLOSE 
	 * 	˫����ȷ�� WAIT_PAY 
	 * 	����ɹ�,���ҷ����� WAIT_SHIPPING 
	 * 	�����Ѿ�֧�� ORDER_SUCCESS
	 */

	private String status;

	// �ֶ�����:�������ڣ�20070909
	private String orderDate;

	// �ֶ�����:��ұ��
	private String buyerId;

	// �ֶ�����:���ұ��
	private String sellerId;

	// �ֶ�����:��������:F�����ٶ�����T�����׶��������ٶ�������Ʒ��Ϣ
	private String orderType;

	// �ֶ�����:
	private String memo;

	// �ֶ�����:���login_id
	private String buyerLoginId;

	// �ֶ�����:����login_id
	private String sellerLoginId;

	// �ֶ�����:������
	private String creator;

	// �ֶ�����:����޸�ʱ��
	private Date gmtModify;

	// �ֶ�����:����޸���
	private String modifier;

	// �ֶ�����:����ʱ��
	private Date gmtCreate;
	
	// �ֶ��������˷�
	private Long logisticsFee;
	
	
	public String getStatusString(){
		if(this.StatusInit.equals(this.status)){
			return "��������";
		}else if(this.StatusOrderClose.equals(this.status)){
			return "�����ر�";
		}else if(this.StatusOrderSuccess.equals(this.status)){
			return "���׳ɹ�";
		}else if(this.StatusShipping.equals(this.status)){
			return "�ȴ�����";
		}else if(this.StatusWaitBuyerConfirm.equals(this.status)){
			return "�ȴ����ȷ��";
		}
		else if(this.StatusWaitPay.equals(this.status)){
			return "�ȴ���Ҹ���";
		}
		else if(this.StatusWaitSellerConfirm.equals(this.status)){
			return "�ȴ�����ȷ��";
		}
		return "����״̬����ȷ";
		
	}

	/**
	 * �Ƿ����ڵ���ȷ��
	 * 
	 * @param myId
	 * @return
	 */
	public boolean waitForMeConfirm(String myId) {
		if (this.status == null) {// this can't be real happen...
			return false;
		}
		if (this.status.equals(StatusInit) && this.isBuyer(myId)) {
			return true;
		}
		if (this.status.equals(StatusWaitSellerConfirm)
				&& this.sellerId.equals(myId)) {
			return true;
		}
		if (this.status.equals(StatusWaitBuyerConfirm)
				&& this.buyerId.equals(myId)) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�ȴ��Է�ȷ��
	 * 
	 * @param myId
	 * @return
	 */
	public boolean waitForHimConfirm(String myId) {
		if (this.status == null) {// this can't be real happen...
			return false;
		}
		if (this.status.equals(StatusWaitSellerConfirm)
				&& this.buyerId.equals(myId)) {
			return true;
		}
		if (this.status.equals(StatusWaitBuyerConfirm)
				&& this.sellerId.equals(myId)) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ��Ѿ��ر�(����)
	 * 
	 * @return
	 */
	public boolean isClosed() {
		return this.status != null && this.status.equals(StatusOrderClose);
	}

	/**
	 * �Ƿ񶩵��Ѿ��ɹ�
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return this.status != null && this.status.equals(StatusOrderSuccess);
	}

	/**
	 * �Ƿ����ȴ���(buyer)����
	 * 
	 * @param myId
	 * @return
	 */
	public boolean isWaitMyPay(String myId) {
		if (this.status == null || !this.status.equals(StatusWaitPay)) {
			return false;
		}
		if (this.isBuyer(myId)) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�ȴ��Է�(buyer)����
	 * 
	 * @param myId
	 * @return
	 */
	public boolean isWaitHimPay(String myId) {
		if (this.status == null || !this.status.equals(StatusWaitPay)) {
			return false;
		}
		if (this.isSeller(myId)) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�ȴ���(seller)����
	 * @param myId
	 * @return
	 */
	public boolean isWaitMyShipping(String myId) {
		if(this.status != null && !this.status.equals(StatusShipping)){
			return false;
		}
		if(this.isSeller(myId)){
			return true;
		}
		return false;
	}
	
	/**
	 * �Ƿ�ȴ���(buyer)ȷ���ջ�
	 * @param myId
	 * @return
	 */
	public boolean isWaitMyConfirmShipping(String myId){
		if(this.status != null && !this.status.equals(StatusShipping)){
			return false;
		}
		if(this.isBuyer(myId)){
			return true;
		}
		return false;
	}
	
	/**
	 * ���Ƿ����е���
	 * 
	 * @param myId
	 * @return
	 */
	public boolean isBuyer(String myId) {
		return this.buyerId.equals(myId);
	}

	/**
	 * ���Ƿ����е�����
	 * 
	 * @param myId
	 * @return
	 */
	public boolean isSeller(String myId) {
		return this.sellerId.equals(myId);
	}

	public boolean isBuyerOrSeller(String myId) {
		return isBuyer(myId) || isSeller(myId);
	}

	/**
	 * �Ƿ���Թر�(����)����
	 * 
	 * @return
	 */
	public boolean canClose() {
		return this.status != null && !this.status.equals(StatusOrderClose)
				&& !this.status.equals(StatusOrderSuccess);
	}

	public boolean close(String memberId) {
		if (!canClose()) {
			return false;
		}
		if (this.status == null) {
			return false;
		}
		if (!this.isBuyerOrSeller(memberId)) {
			return false;
		}
		this.setStatus(StatusOrderClose);
		this.setModifier(memberId);
		return true;
	}

	/**
	 * �Ƿ���ȷ�Ͻ���
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean canConfirm(String memberId) {
		return waitForMeConfirm(memberId);
	}

	/**
	 * �û�ȷ�϶���
	 * 
	 * @param memberId
	 */
	public boolean confirm(String memberId) {
		if (!canConfirm(memberId)) {
			return false;
		}
		if (this.status == null) {
			return false;
		}

		if (this.status != null && this.status.equals(StatusInit)) {
			if (this.isBuyer(memberId)) {
				this.setStatus(StatusWaitSellerConfirm);
			}
			if (this.isSeller(memberId)) {
				this.setStatus(StatusWaitBuyerConfirm);
			}
			this.setModifier(memberId);
			return true;
		}

		if (this.status != null && this.status.equals(StatusWaitSellerConfirm)
				&& this.isSeller(memberId)) {
			if (this.imLastModifer(memberId)) {
				this.setStatus(StatusWaitBuyerConfirm);
			} else {
				this.setStatus(StatusWaitPay);
			}
			this.setModifier(memberId);
			return true;
		}

		if (this.status != null && this.status.equals(StatusWaitBuyerConfirm)
				&& this.isBuyer(memberId)) {
			if (this.imLastModifer(memberId)) {
				this.setStatus(StatusWaitSellerConfirm);
			} else {
				this.setStatus(StatusWaitPay);
			}
			this.setModifier(memberId);
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ��ɾ�������е���Ʒ
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean canRemoveItem(String memberId) {
		return this.waitForMeConfirm(memberId);
	}

	/**
	 * �Ƿ�����޸���Ʒ״̬Ϊ������
	 * @return
	 */
	public boolean canSetShipping() {
		if(this.status == null){
			return false;
		}
		return this.status.equals(StatusWaitPay);
	}
	
	public boolean setShipping(String adminUserId){
		if(!canSetShipping()){
			return false;
		}
		this.setModifier(adminUserId);
		this.setStatus(StatusShipping);
		return true;
	}
	
	/**
	 * �Ƿ�����޸���Ʒ״̬Ϊ�ɹ�
	 * 
	 * @return
	 */
	public boolean canSetSuccess() {
		if (this.status == null) {
			return false;
		}
		return this.status.equals(StatusShipping);
	}

	public boolean setSuccess(String adminUserId) {
		if (!canSetSuccess()) {
			return false;
		}
		this.setModifier(adminUserId);
		this.setStatus(StatusOrderSuccess);
		return true;
	}

	/**
	 * �Ƿ������һ���޸���
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean imLastModifer(String memberId) {
		return this.modifier != null && this.modifier.equals(memberId);
	}

	/**
	 * �Ƿ���ٶ���
	 * 
	 * @return
	 */
	public boolean isFastOrder() {
		return this.orderType != null && this.orderType.equals(TypeQuick);
	}

	/**
	 * �Ƿ���Ʒ���׶���
	 * 
	 * @return
	 */
	public boolean isTradeOrder() {
		return this.orderType != null && this.orderType.equals(TypeTrade);
	}

	public boolean imNotLastModifer(String memberId) {
		return !imLastModifer(memberId);
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopId() {
		return this.shopId;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}

	public Long getPayFee() {
		return this.payFee;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDate() {
		return this.orderDate;
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

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setBuyerLoginId(String buyerLoginId) {
		this.buyerLoginId = buyerLoginId;
	}

	public String getBuyerLoginId() {
		return this.buyerLoginId;
	}

	public void setSellerLoginId(String sellerLoginId) {
		this.sellerLoginId = sellerLoginId;
	}

	public String getSellerLoginId() {
		return this.sellerLoginId;
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
	
	public void setLogisticsFee(Long fee){
		this.logisticsFee = fee;
	}
	
	public Long getLogisticsFee(){
		return this.logisticsFee;
	}
	
	private static final boolean nullAbleLongCompare(Long a, Long b) {
		if (a == null && b == null) {
			return true;
		}
		a=a==null?0L:a;
		b=b==null?0L:b;
		return a.longValue() == b.longValue();
	}
	
	public String getLog(Long logisticsFee){
		StringBuilder sb = new StringBuilder("����:[");
		boolean changed = false;
		if(!nullAbleLongCompare(this.logisticsFee,logisticsFee)){
			changed = true;
			sb.append(" �˷�").append(this.logisticsFee == null ? 0L : getMoney(this.logisticsFee));
			sb.append(PointeTo).append(logisticsFee == null ? 0L : getMoney(logisticsFee));
			this.logisticsFee = logisticsFee;
			
		}
		if (!changed){
			return null;
		}
		sb.append(']');
		return sb.toString();
	}
	private static final String PointeTo = "->";


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}