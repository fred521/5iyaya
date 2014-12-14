//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.dao.query;

/**
 * <p>
 *  推荐位置管理查询
 * </p>
 * @author jacky
 * @version $Id: CommendPositionQuery.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */

public class CommendPositionQuery extends QueryBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8947466432545874637L;

    //字段描述:推荐位置编号
    private java.lang.Long    commendId;
    //字段描述:推荐位置识别码：只能用数字或名称，一旦建立，则不能修改
    private java.lang.String  commendCode;
    //字段描述:所属推荐页面：0：首页1：咨询页面、2：商品页面、3：三维首页
    private java.lang.Long    commendPage;
    //字段描述:推荐位置名称
    private java.lang.String  commendName;
    //字段描述:推荐内容类型：1、图片与文字、2、光图片、3、光文字
    private java.lang.String  commendContentType;
    //字段描述:创建时间
    private java.util.Date    gmtCreate;
    //字段描述:创建人
    private java.lang.String  creator;
    //字段描述:最后修改时间
    private java.util.Date    gmtModify;
    //字段描述:最后修改人
    private java.lang.String  modifier;

    public void setCommendId(java.lang.Long commendId) {
        this.commendId = commendId;
    }

    public java.lang.Long getCommendId() {
        return this.commendId;
    }

    public void setCommendCode(java.lang.String commendCode) {
        this.commendCode = commendCode;
    }

    public java.lang.String getCommendCode() {
        return this.commendCode;
    }

    public void setCommendPage(java.lang.Long commendPage) {
        this.commendPage = commendPage;
    }

    public java.lang.Long getCommendPage() {
        return this.commendPage;
    }

    public void setCommendName(java.lang.String commendName) {
        this.commendName = commendName;
    }

    public java.lang.String getCommendName() {
        return this.commendName;
    }

    public void setCommendContentType(java.lang.String commendContentType) {
        this.commendContentType = commendContentType;
    }

    public java.lang.String getCommendContentType() {
        return this.commendContentType;
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

}
