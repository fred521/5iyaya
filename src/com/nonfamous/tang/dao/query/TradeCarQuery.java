//===================================================================
// Created on 2007-9-18
//===================================================================
package com.nonfamous.tang.dao.query;

import java.util.List;

import com.nonfamous.tang.domain.trade.TradeCarItem;

/**
 * <p>
 *  ���ﳵ��Ʒ��ѯ
 * </p>
 * @author HGS-gonglei
 * @version $Id: TradeCarQuery.java,v 1.1 2008/07/11 00:46:57 fred Exp $
 */

public class TradeCarQuery extends QueryBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = 2107789068206088019L;

    //������id
    private String             owner;

    //��ѯ�����
    private List<TradeCarItem> items;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<TradeCarItem> getItems() {
        return items;
    }

    public void setItems(List<TradeCarItem> items) {
        this.items = items;
    }

}
