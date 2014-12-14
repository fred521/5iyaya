package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: CommendPosition.java,v 1.1 2008/07/11 00:46:59 fred Exp $
 */
public class CommendPosition extends DomainBase {

    private static final long serialVersionUID = 4154962718585556024L;
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
    //�ֶ�����:ͼƬ���
    private java.lang.Long    picWidth;
    //�ֶ�����:ͼƬ�߶�
    private java.lang.Long    picHeight;
    //�ֶ�����:���ֳ���
    private java.lang.Long    textLength;
    //�ֶ�����:�滻ͼƬ·��
    private java.lang.String  picPath;
    //�ֶ�����:�滻����
    private java.lang.String  replaceText;
    //�ֶ�����:����ʱ��
    private java.util.Date    gmtCreate;
    //�ֶ�����:������
    private java.lang.String  creator;
    //�ֶ�����:����޸�ʱ��
    private java.util.Date    gmtModify;
    //�ֶ�����:����޸���
    private java.lang.String  modifier;
    //λ��˳��
    private java.lang.Long    positionOrder;
    //1:��Ʒ�Ƽ�,2:�����Ƽ�,3:������Ϣ�Ƽ�
    private java.lang.String  commendType;

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

    public void setPicWidth(java.lang.Long picWidth) {
        this.picWidth = picWidth;
    }

    public java.lang.Long getPicWidth() {
        return this.picWidth;
    }

    public void setPicHeight(java.lang.Long picHeight) {
        this.picHeight = picHeight;
    }

    public java.lang.Long getPicHeight() {
        return this.picHeight;
    }

    public void setTextLength(java.lang.Long textLength) {
        this.textLength = textLength;
    }

    public java.lang.Long getTextLength() {
        return this.textLength;
    }

    public void setPicPath(java.lang.String picPath) {
        this.picPath = picPath;
    }

    public java.lang.String getPicPath() {
        return this.picPath;
    }

    public void setReplaceText(java.lang.String replaceText) {
        this.replaceText = replaceText;
    }

    public java.lang.String getReplaceText() {
        return this.replaceText;
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

    public java.lang.String getCommendType() {
        return commendType;
    }

    public void setCommendType(java.lang.String commendType) {
        this.commendType = commendType;
    }

    public java.lang.Long getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(java.lang.Long positionOrder) {
        this.positionOrder = positionOrder;
    }

}