//===================================================================
// Created on 2007-9-19
//===================================================================
package com.nonfamous.tang.dao.query;

import java.util.List;

import com.nonfamous.tang.domain.Shop;

/**
 * <p>
 *  签约店铺查询
 * </p>
 * @author HGS-gonglei
 * @version $Id: SignShopQuery.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */

public class SignShopQuery extends QueryBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2550325013790256191L;

    //会员id
    private java.lang.String  memberId;
    //查询结果集
    private List<Shop>        items;

    public List<Shop> getItems() {
        return items;
    }

    public void setItems(List<Shop> items) {
        this.items = items;
    }

    public java.lang.String getMemberId() {
        return memberId;
    }

    public void setMemberId(java.lang.String memberId) {
        this.memberId = memberId;
    }

}
