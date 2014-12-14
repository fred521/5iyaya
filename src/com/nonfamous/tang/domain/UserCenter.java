package com.nonfamous.tang.domain;

import java.util.Date;

public class UserCenter {
    // * Database column [DB2INST1.USER_CENTER.VERIFY_CODE]
    private String verifyCode;

    // * Database column [DB2INST1.USER_CENTER.FK_MEMBER]
    private String fkMember;

    // * Database column [DB2INST1.USER_CENTER.MEMBER_TYPE]
    private String memberType;

    // * Database column [DB2INST1.USER_CENTER.REQUEST_IP]
    private String requestIp;

    // * Database column [DB2INST1.USER_CENTER.GMT_CREATE]
    private Date gmtCreate;
    
    public final static String BUYER = "B";
    
    public final static String SUPPLIER = "S";

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public String getFkMember() {
        return fkMember;
    }

    public void setFkMember(String fkMember) {
        this.fkMember = fkMember;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp == null ? null : requestIp.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}