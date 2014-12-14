package com.nonfamous.tang.domain;

import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: GoodsContent.java,v 1.2 2008/11/29 02:53:14 fred Exp $
 */
public class GoodsContent extends DomainBase{

	private static final long serialVersionUID = 5010162394191038949L;
	//×Ö¶ÎÃèÊö:ÉÌÆ·±àºÅ
	private java.lang.String goodsId;
	//×Ö¶ÎÃèÊö:ÉÌÆ·ÃèÊöÄÚÈİ
	private java.lang.String content;
	
	public String getContentWithoutHtml(){
		return HtmlUtils.parseHtml(this.getContent());
	}

	public void setGoodsId(java.lang.String  goodsId){
		this.goodsId = goodsId;	
	}
	
	public java.lang.String getGoodsId(){
		return this.goodsId;	
	}
	public void setContent(java.lang.String  content){
		this.content = content;	
	}
	
	public java.lang.String getContent(){
		return this.content;	
	}

}