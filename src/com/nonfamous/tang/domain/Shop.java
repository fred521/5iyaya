package com.nonfamous.tang.domain;

import java.util.Date;
import java.util.List;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: Shop.java,v 1.2 2009/04/18 07:07:30 andy Exp $
 */
public class Shop extends DomainBase {

	private static final long serialVersionUID = -6653530590236548760L;

	// �ֶ�����:���̱��
	private java.lang.String shopId;

	// �ֶ�����:��Ա���
	private java.lang.String memberId;

	// �ֶ�����:��Ա��½id
	private java.lang.String loginId;
	
	private java.lang.String videoId;
	
	private java.lang.String liveId;

	// �ֶ�����:��������
	private java.lang.String shopName;

	// �ֶ�����:��������
	private java.lang.String shopOwner;

	// �ֶ�����:��Ӫ��Ʒ
	private java.lang.String commodity;

	// �ֶ�����:�����г���ţ����е��г�����λ����
	private java.lang.String belongMarketId;
	
//	 �ֶ�����:�����г�����
	private java.lang.String belongMarketName;

	// �ֶ�����:���̵�ַ
	private java.lang.String address;

	// �ֶ�����:���ڹ̻�
	private java.lang.String phone;

	// �ֶ�����:��������
	private java.lang.String bank;

	// �ֶ�����:�����ʺ�
	private java.lang.String bankAccount;

	// �ֶ�����:����������
	private java.lang.String accountName;

	// �ֶ�����:����������
	private java.lang.Long goodsCount;

	// �ֶ�����:�Ƿ�����������ͷ��Y��N��
	private java.lang.String camera;

	// �ֶ�����:�Ƿ񼯳�web800��Y�ǣ�N��
	private java.lang.String webPhone;

	// �ֶ�����:logoͼƬ·��
	private java.lang.String logo;

	// �ֶ�����:������Ƭ
	private java.lang.String shopImg;

	// �ֶ�����:
	private java.util.Date gmtCreate;

	// �ֶ�����:
	private java.lang.String creator;

	// �ֶ�����:
	private java.util.Date gmtModify;

	// �ֶ�����:
	private java.lang.String modifier;

	// �ֶ�����:��ά��ַ/����ʱ¼��/��Ա�Լ������޸���ά��ַ
	private java.lang.String gisAddress;

	// ���̹���
	private ShopCommend shopCommend;
	
	//�����Ǽ�
	private int starNum;

	// �����е���Ʒ
	private List<GoodsBaseInfo> goods;

	public void setShopId(java.lang.String shopId) {
		this.shopId = shopId;
	}

	public java.lang.String getShopId() {
		return this.shopId;
	}

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}

	public java.lang.String getMemberId() {
		return this.memberId;
	}

	public void setLoginId(java.lang.String loginId) {
		this.loginId = loginId;
	}

	public java.lang.String getLoginId() {
		return this.loginId;
	}

	public void setShopName(java.lang.String shopName) {
		this.shopName = shopName;
	}

	public java.lang.String getShopName() {
		return this.shopName;
	}

	public void setShopOwner(java.lang.String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public java.lang.String getShopOwner() {
		return this.shopOwner;
	}

	public void setCommodity(java.lang.String commodity) {
		this.commodity = commodity;
	}

	public java.lang.String getCommodity() {
		return this.commodity;
	}

	public void setBelongMarketId(java.lang.String belongMarketId) {
		this.belongMarketId = belongMarketId;
	}

	public java.lang.String getBelongMarketId() {
		return this.belongMarketId;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setBank(java.lang.String bank) {
		this.bank = bank;
	}

	public java.lang.String getBank() {
		return this.bank;
	}

	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}

	public java.lang.String getAccountName() {
		return this.accountName;
	}

	public void setGoodsCount(java.lang.Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public java.lang.Long getGoodsCount() {
		return this.goodsCount;
	}

	public void setCamera(java.lang.String camera) {
		this.camera = camera;
	}

	public java.lang.String getCamera() {
		return this.camera;
	}

	public void setWebPhone(java.lang.String webPhone) {
		this.webPhone = webPhone;
	}

	public java.lang.String getWebPhone() {
		return this.webPhone;
	}

	public void setLogo(java.lang.String logo) {
		this.logo = logo;
	}

	public java.lang.String getLogo() {
		return this.logo;
	}

	public void setShopImg(java.lang.String shopImg) {
		this.shopImg = shopImg;
	}

	public java.lang.String getShopImg() {
		return this.shopImg;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
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

	public void setGisAddress(java.lang.String gisAddress) {
		this.gisAddress = gisAddress;
	}

	public java.lang.String getGisAddress() {
		return this.gisAddress;
	}

	public ShopCommend getShopCommend() {
		return shopCommend;
	}

	public void setShopCommend(ShopCommend shopCommend) {
		this.shopCommend = shopCommend;
	}

	public List<GoodsBaseInfo> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsBaseInfo> goods) {
		this.goods = goods;
	}
	
	public boolean isNotFull() {
		return (shopName == null || commodity == null || shopOwner == null
				|| belongMarketId == null || address == null || phone == null);
	}
	
	public String getGmtCreateFormat(){
		return DateUtils.dtSimpleChineseFormat(this.getGmtCreate());
	}

	public String getYMDDate(Date date){
		return DateUtils.dtSimpleFormat(this.getGmtCreate());
	}

	public java.lang.String getBelongMarketName() {
		return belongMarketName;
	}

	public void setBelongMarketName(java.lang.String belongMarketName) {
		this.belongMarketName = belongMarketName;
	}

	public java.lang.String getVideoId() {
		return videoId;
	}

	public void setVideoId(java.lang.String videoId) {
		this.videoId = videoId;
	}

	public java.lang.String getLiveId() {
		return liveId;
	}

	public void setLiveId(java.lang.String liveId) {
		this.liveId = liveId;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}
	
	
}