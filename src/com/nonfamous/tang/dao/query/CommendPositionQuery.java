//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.dao.query;

/**
 * <p>
 *  �Ƽ�λ�ù����ѯ
 * </p>
 * @author jacky
 * @version $Id: CommendPositionQuery.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */

public class CommendPositionQuery extends QueryBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8947466432545874637L;

    //�ֶ�����:�Ƽ�λ�ñ��
    private java.lang.Long    commendId;
    //�ֶ�����:�Ƽ�λ��ʶ���룺ֻ�������ֻ����ƣ�һ�������������޸�
    private java.lang.String  commendCode;
    //�ֶ�����:�����Ƽ�ҳ�棺0����ҳ1����ѯҳ�桢2����Ʒҳ�桢3����ά��ҳ
    private java.lang.Long    commendPage;
    //�ֶ�����:�Ƽ�λ������
    private java.lang.String  commendName;
    //�ֶ�����:�Ƽ��������ͣ�1��ͼƬ�����֡�2����ͼƬ��3��������
    private java.lang.String  commendContentType;
    //�ֶ�����:����ʱ��
    private java.util.Date    gmtCreate;
    //�ֶ�����:������
    private java.lang.String  creator;
    //�ֶ�����:����޸�ʱ��
    private java.util.Date    gmtModify;
    //�ֶ�����:����޸���
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
