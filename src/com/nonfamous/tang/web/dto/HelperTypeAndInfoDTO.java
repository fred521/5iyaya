//===================================================================
// Created on Jun 25, 2007
//===================================================================
package com.nonfamous.tang.web.dto;

import java.io.Serializable;
import java.util.List;

import com.nonfamous.tang.domain.HelperType;

/**
 * <p>
 *  咨询首页需要显示的信息
 * </p>
 * @author fred
 * @version $Id: HelperTypeAndInfoDTO.java,v 1.1 2008/09/15 13:44:59 fred Exp $
 */

public class HelperTypeAndInfoDTO implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4205663512677105102L;
    private List              list;

    private HelperType          helperType;

    public HelperType getHelperType() {
		return this.helperType;
	}
	public void setHelperType(HelperType helperType) {
		this.helperType = helperType;
	}
	public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
