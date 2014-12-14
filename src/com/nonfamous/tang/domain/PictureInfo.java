package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

public class PictureInfo extends DomainBase {

    private static final long serialVersionUID = -1767507488898465056L;

    public static final String DELETE_STATUS = "D";

    public static final String NORMAL_STATUS = "N";

    // ×Ö¶ÎÃèÊö:Í¼Æ¬±àºÅ
    private String id;

    // ×Ö¶ÎÃèÊö:ÉÌÆ·±àºÅ
    private String goodsId;

    // ×Ö¶ÎÃèÊö:ÉÌÆ·Í¼Æ¬Â·¾¶
    private String path;

    private java.util.Date gmtModify;

    private java.util.Date gmtCreate;

    // ×Ö¶ÎÃèÊö:Í¼Æ¬×´Ì¬
    private String status = NORMAL_STATUS;

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getGoodsId() {
	return goodsId;
    }

    public void setGoodsId(String goodsId) {
	this.goodsId = goodsId;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public java.util.Date getGmtModify() {
	return gmtModify;
    }

    public void setGmtModify(java.util.Date gmtModify) {
	this.gmtModify = gmtModify;
    }

    public java.util.Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(java.util.Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

}
