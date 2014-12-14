package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: ShopCommend.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class ShopCommend extends DomainBase{

	private static final long serialVersionUID = 799497937436288941L;
	//×Ö¶ÎÃèÊö:µêÆÌ±àºÅ
	private java.lang.String shopId;
	//×Ö¶ÎÃèÊö:µêÆÌ¹«¸æ
	private java.lang.String commend;

	public void setShopId(java.lang.String  shopId){
		this.shopId = shopId;	
	}
	
	public java.lang.String getShopId(){
		return this.shopId;	
	}
	public void setCommend(java.lang.String  commend){
		this.commend = commend;	
	}
	
	public java.lang.String getCommend(){
		return this.commend;	
	}

}