package com.nonfamous.tang.domain;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: GoodsBaseInfo.java,v 1.9 2009/09/12 05:12:58 fred Exp $
 */
public class GoodsBaseInfo extends DomainBase {

	private static final long serialVersionUID = -9049204732147007898L;

	public static final String DELETE_STATUS = "D";

	public static final String NORMAL_STATUS = "N";

	public static final String DOWN_STATUS = "E";
	
	/*�����Ʒ���� N Ϊ��������Ʒ, T Ϊ�Ź�����Ʒ, MΪ����+�Ź���Ʒ*/
	public static final String NORMAL_TYPE="N";
	
	public static final String TEAM_TYPE="T";

	public static final String MIXED_TYPE="M";
	
	// �ֶ�����:��Ʒ���
	private java.lang.String goodsId;

	// �ֶ�����:���̱��
	private java.lang.String shopId;

	// �ֶ�����:��Ա���
	private java.lang.String memberId;

	// �ֶ�����:��Ʒ����
	private java.lang.String goodsName;

	// �ֶ�����:��ƷĿǰΪ2�����࣬���ֶδ洢���������ࣺ��һ������Ϊ001�����������Ϊ001001 or 001002
	// ������Ҫ����һ�������ѯʱ����Ҫ��ȡ���еĶ���������в�ѯ
	private java.lang.String goodsCat;

	// �ֶ�����:��Ʒ�����۸񣬼۸񵽷�
	private java.lang.Long batchPrice;
	
	//�г��۸�
	private java.lang.Long marketPrice;

	// �ֶ�����:��Ʒ��������
	private java.lang.Long batchNum;
	// �Ź� ��Ʒ�����µ�����
	private java.lang.Long groupNum = 0L;
	// �ֶ�����:��Ʒ�Ź��۸񣬼۸񵽷�
	private java.lang.Long groupPrice;
	// �ֶ�����:��Ʒ��������
	private java.lang.Long groupDefaultNum;
	
	// �ֶ�����:�۸�����
	private java.lang.String priceDes = "";

	// �ֶ�����:��Ʒ�����
	private java.lang.Long goodsNum = new Long(0);

	//��Ʒ�Ƽ��Ǽ�
	private java.lang.Integer starNum=new Integer(0);
	
	//�Ƿ�������Ʒ
	private Boolean isNew= new Boolean(false);
	
	public Boolean getIsNew() {
		return isNew;
		
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	//��Ʒ��ɫ
	private String colors = "";
	
	//��Ʒ�ߴ�
	private String size = "";
	
	//�������ԣ���ֵ����ʽ
	private String properties;

	private HashMap<String,String> propertiesMap;

	private static final String DIVIDER = ",";
    
    private static final String SEPARATOR = ":";

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public java.lang.Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(java.lang.Integer starNum) {
		this.starNum = starNum;
	}

	// �ֶ�����:��ƷͼƬ·��
	private java.lang.String goodsPic;

	// �ֶ�����:��Ʒ��������
	private java.lang.Long abandonDays;

	// �ֶ�����:��Ʒ����������������Ʒ������������Ʒ����ʱ�����õ�����Ʒ�ֶ��¼������ʱ��Ϊ�¼�ʱ��
	private java.util.Date gmtAbandon;

	// �ֶ�����:N������Dɾ����E�����ڣ���Ʒһ��������Ϊ��������Ʒ�ϼ���Ϊ��������Ҫ���¼������ʱ��
	private java.lang.String goodsStatus;
	
	private java.lang.String goodsType;

	// �ֶ�����:��Ʒ���������ÿ�������һ
	private java.lang.Long viewCount;

	// �ֶ�����:
	private java.lang.String creator;

	// �ֶ�����:
	private java.util.Date gmtModify;

	// �ֶ�����:
	private java.lang.String modifier;

	// �ֶ�����:����ʱ��
	private java.util.Date gmtCreate;

	// �ֶ�����:����·��
	private java.lang.String catPath;
	
	private List<PictureInfo> pictures;

	private GoodsContent goodsContent = new GoodsContent();

	private static final DecimalFormat priceFormat = new DecimalFormat("0.00");

	public String getGoodsBatchPriceInFormat() {
		if (this.getBatchPrice() == null) {
			return null;
		}
		double price = this.getBatchPrice().doubleValue() / 100;

		return priceFormat.format(price);
	}
	
	public String getGoodsMarketPriceInFormat() {
		if (this.getMarketPrice() == null) {
			return null;
		}
		double price = this.getMarketPrice().doubleValue() / 100;

		return priceFormat.format(price);
	}

	public Date getGmtAbandonByAbandonDays(Long abandonDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, this.getAbandonDays().intValue());
		return cal.getTime();
	}

	public String getYMDDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(getGmtAbandonByAbandonDays(abandonDays));
	}

	/**
	 * �õ��������Ʒ����id
	 * 
	 * @return
	 */
	public List<String> getCatalogIds() {
		List<String> cats = new ArrayList<String>();
		if (StringUtils.isNotBlank(this.catPath)) {
			String[] catArray = StringUtils.split(this.catPath,
					GoodsCat.PathSeparator);
			for (int i = 0; i < catArray.length; i++) {
				cats.add(catArray[i]);
			}
		}
		return cats;
	}

	public GoodsContent getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(GoodsContent context) {
		this.goodsContent = context;
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

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}

	public java.lang.String getMemberId() {
		return this.memberId;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	public java.lang.String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsCat(java.lang.String goodsCat) {
		this.goodsCat = goodsCat;
	}

	public java.lang.String getGoodsCat() {
		return this.goodsCat;
	}

	public void setBatchPrice(java.lang.Long batchPrice) {
		this.batchPrice = batchPrice;
	}

	public java.lang.Long getBatchPrice() {
		return this.batchPrice;
	}

	public void setBatchNum(java.lang.Long batchNum) {
		this.batchNum = batchNum;
	}

	public java.lang.Long getBatchNum() {
		return this.batchNum;
	}

	public java.lang.Long getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(java.lang.Long groupPrice) {
		this.groupPrice = groupPrice;
	}

	public java.lang.Long getGroupDefaultNum() {
		return groupDefaultNum;
	}

	public void setGroupDefaultNum(java.lang.Long groupDefaultNum) {
		this.groupDefaultNum = groupDefaultNum;
	}

	public void setPriceDes(java.lang.String priceDes) {
		this.priceDes = priceDes;
	}

	public java.lang.String getPriceDes() {
		return this.priceDes;
	}

	public void setGoodsNum(java.lang.Long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public java.lang.Long getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsPic(java.lang.String goodsPic) {
		this.goodsPic = goodsPic;
	}

	public java.lang.String getGoodsPic() {
		return this.goodsPic;
	}

	public void setAbandonDays(java.lang.Long abandonDays) {
		this.abandonDays = abandonDays;
	}

	public java.lang.Long getAbandonDays() {
		return this.abandonDays;
	}

	public void setGmtAbandon(java.util.Date gmtAbandon) {
		this.gmtAbandon = gmtAbandon;
	}

	public java.util.Date getGmtAbandon() {
		return this.gmtAbandon;
	}

	public void setGoodsStatus(java.lang.String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public java.lang.String getGoodsStatus() {
		return this.goodsStatus;
	}

	public void setViewCount(java.lang.Long viewCout) {
		this.viewCount = viewCout;
	}

	public java.lang.Long getViewCount() {
		return this.viewCount;
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

	public void setCatPath(java.lang.String catPath) {
		this.catPath = catPath;
	}

	public java.lang.String getCatPath() {
		return this.catPath;
	}
	public java.lang.String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(java.lang.String goodsType) {
		this.goodsType = goodsType;
	}

	public java.lang.Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(java.lang.Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public java.lang.Long getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(java.lang.Long groupNum) {
		this.groupNum = groupNum;
	}

	public List<PictureInfo> getPictures() {
	    return pictures;
	}

	public void setPictures(List<PictureInfo> pictures) {
	    this.pictures = pictures;
	}

	public String getProperties() {
		Iterator it = this.propertiesMap.entrySet().iterator();
    	StringBuffer sb = new StringBuffer(24);
    	while(it.hasNext()){
    		Map.Entry element = (Map.Entry) it.next();
    		String strKey = (String)element.getKey(); //��ֵ
    		String strValue = (String)element.getValue(); //valueֵ
    		if(sb.length()!=0){
    			sb.append(this.DIVIDER);
    		}
    		sb.append(strKey);
    		sb.append(this.SEPARATOR);
    		sb.append(strValue);
    	}
    	this.properties = sb.toString();
        return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties == null ? null : properties.trim();
        if(properties==null||properties.equals("")){
        	return;
        }
        String[] keyValues = this.properties.split(this.DIVIDER);
        for(String keyValuePair: keyValues){
        	String[] keyValueArray = keyValuePair.split(this.SEPARATOR);
        	getPropertiesMap().put(keyValueArray[0], keyValueArray[1]);
        }
	}

	public HashMap<String, String> getPropertiesMap() {
		if(this.propertiesMap==null){
			propertiesMap = new HashMap<String,String>();
		}
		return propertiesMap;
	}

	public void setPropertiesMap(HashMap<String, String> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
}