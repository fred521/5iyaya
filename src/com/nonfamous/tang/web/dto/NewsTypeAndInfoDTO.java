//===================================================================
// Created on Jun 25, 2007
//===================================================================
package com.nonfamous.tang.web.dto;

import java.io.Serializable;
import java.util.List;

import com.nonfamous.tang.domain.NewsType;

/**
 * <p>
 *  咨询首页需要显示的信息
 * </p>
 * @author jacky
 * @version $Id: NewsTypeAndInfoDTO.java,v 1.1 2008/07/11 00:47:18 fred Exp $
 */

public class NewsTypeAndInfoDTO implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4205663512677105102L;
    private List              list;

    private NewsType          newsType;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
