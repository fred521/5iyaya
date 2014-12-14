package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: HelperType.java,v 1.1 2008/09/15 13:44:59 fred Exp $
 */
public class HelperType extends DomainBase{

	private static final long serialVersionUID = -404863358602525573L;
	//×Ö¶ÎÃèÊö:ĞÅÏ¢±àºÅ£º01¡¢02
	private java.lang.Integer helperType;
	//×Ö¶ÎÃèÊö:ĞÅÏ¢Ãû³Æ
	private java.lang.String typeName;
	
	//ÏÔÊ¾Ë³Ğò
	private Integer showOrder;

	public java.lang.Integer getHelperType() {
		return this.helperType;
	}

	public void setHelperType(java.lang.Integer helperType) {
		this.helperType = helperType;
	}

	public java.lang.String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public Integer getShowOrder() {
		return this.showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}


	
}