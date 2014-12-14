package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: NewsType.java,v 1.2 2008/11/29 02:53:14 fred Exp $
 */
public class NewsType extends DomainBase{

	private static final long serialVersionUID = -404863358602525573L;
	//◊÷∂Œ√Ë ˆ:–≈œ¢±‡∫≈£∫01°¢02
	private java.lang.String newsType;
	//◊÷∂Œ√Ë ˆ:–≈œ¢√˚≥∆
	private java.lang.String typeName;
	
	//œ‘ æÀ≥–Ú
	private Integer showOrder;

	public void setNewsType(java.lang.String  newsType){
		this.newsType = newsType;	
	}
	
	public java.lang.String getNewsType(){
		return this.newsType;	
	}
	public void setTypeName(java.lang.String  typeName){
		this.typeName = typeName;	
	}
	
	public java.lang.String getTypeName(){
		return this.typeName;	
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	
}