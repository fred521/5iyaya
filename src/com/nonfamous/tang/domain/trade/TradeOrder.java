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
	 * 快速订单
	 */
	public static final String TypeQuick = "F";

	/**
	 * 交易订单
	 */
	public static final String TypeTrade = "T";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5948187550441088251L;

	// 字段描述:订单号20070910(日期)+8为序列号
	private String orderNo;

	// 字段描述:店铺id
	private String shopId;

	// 字段描述:店铺名称
	private String shopName;

	// 字段描述:订单费用
	private Long payFee;
	
	private String address;

	/*
	 * 字段描述:
	 * 订单状态： 
	 * 	订单初始状态 ORDER_INIT 
	 * 	等待卖家确认 WAIT_SELLER_CONFIRM 
	 * 	等待买家确认 WAIT_BUYER_CONFIRM 
	 * 	订单作废 ORDER_CLOSE 
	 * 	双方已确认 WAIT_PAY 
	 * 	付款成功,卖家发货中 WAIT_SHIPPING 
	 * 	订单已经支付 ORDER_SUCCESS
	 */

	private String status;

	// 字段描述:订单日期：20070909
	private String orderDate;

	// 字段描述:买家编号
	private String buyerId;

	// 字段描述:卖家编号
	private String sellerId;

	// 字段描述:订单类型:F（快速订单）T（交易订单）快速订单无商品信息
	private String orderType;

	// 字段描述:
	private String memo;

	// 字段描述:买家login_id
	private String buyerLoginId;

	// 字段描述:卖家login_id
	private String sellerLoginId;

	// 字段描述:创建人
	private String creator;

	// 字段描述:最后修改时间
	private Date gmtModify;

	// 字段描述:最后修改人
	private String modifier;

	// 字段描述:创建时间
	private Date gmtCreate;
	
	// 字段描述：运费
	private Long logisticsFee;
	
	
	public String getStatusString(){
		if(this.StatusInit.equals(this.status)){
			return "订单创建";
		}else if(this.StatusOrderClose.equals(this.status)){
			return "订单关闭";
		}else if(this.StatusOrderSuccess.equals(this.status)){
			return "交易成功";
		}else if(this.StatusShipping.equals(this.status)){
			return "等待发货";
		}else if(this.StatusWaitBuyerConfirm.equals(this.status)){
			return "等待买家确认";
		}
		else if(this.StatusWaitPay.equals(this.status)){
			return "等待买家付款";
		}
		else if(this.StatusWaitSellerConfirm.equals(this.status)){
			return "等待卖家确认";
		}
		return "订单状态不明确";
		
	}

	/**
	 * 是否正在等我确认
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
	 * 是否等待对方确认
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
	 * 是否已经关闭(作废)
	 * 
	 * @return
	 */
	public boolean isClosed() {
		return this.status != null && this.status.equals(StatusOrderClose);
	}

	/**
	 * 是否订单已经成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return this.status != null && this.status.equals(StatusOrderSuccess);
	}

	/**
	 * 是否正等待我(buyer)付款
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
	 * 是否等待对方(buyer)付款
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
	 * 是否等待我(seller)发货
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
	 * 是否等待我(buyer)确认收货
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
	 * 我是否交易中的买方
	 * 
	 * @param myId
	 * @return
	 */
	public boolean isBuyer(String myId) {
		return this.buyerId.equals(myId);
	}

	/**
	 * 我是否交易中的卖方
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
	 * 是否可以关闭(作废)交易
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
	 * 是否能确认交易
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean canConfirm(String memberId) {
		return waitForMeConfirm(memberId);
	}

	/**
	 * 用户确认订单
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
	 * 是否可删除订单中的商品
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean canRemoveItem(String memberId) {
		return this.waitForMeConfirm(memberId);
	}

	/**
	 * 是否可以修改商品状态为发货中
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
	 * 是否可以修改商品状态为成功
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
	 * 是否是最后一个修改人
	 * 
	 * @param memberId
	 * @return
	 */
	public boolean imLastModifer(String memberId) {
		return this.modifier != null && this.modifier.equals(memberId);
	}

	/**
	 * 是否快速订单
	 * 
	 * @return
	 */
	public boolean isFastOrder() {
		return this.orderType != null && this.orderType.equals(TypeQuick);
	}

	/**
	 * 是否商品交易订单
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
		StringBuilder sb = new StringBuilder("订单:[");
		boolean changed = false;
		if(!nullAbleLongCompare(this.logisticsFee,logisticsFee)){
			changed = true;
			sb.append(" 运费").append(this.logisticsFee == null ? 0L : getMoney(this.logisticsFee));
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