package com.nonfamous.tang.domain;

import java.util.Date;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * 管理员操作日志
 * 
 * @author: fish
 * @version $Id: AdminLog.java,v 1.1 2008/07/11 00:47:01 fred Exp $
 */
public class AdminLog extends DomainBase {

	private static final long serialVersionUID = -6081364563532748898L;

	private static final int ParameterBlock = 5;

	private static final int ParameterBlockSize = 1000;

	private Long id;

	private Long adminUserId;

	private String action;

	private Date create;

	private String actionIp;

	private String[] parameters = new String[ParameterBlock];

	public String getParamter0() {
		return parameters[0];
	}

	public String getParamter1() {
		return parameters[1];
	}

	public String getParamter2() {
		return parameters[2];
	}

	public String getParamter3() {
		return parameters[3];
	}

	public String getParamter4() {
		return parameters[4];
	}

	public void setParamter0(String s) {
		this.parameters[0] = s;
	}

	public void setParamter1(String s) {
		this.parameters[1] = s;
	}

	public void setParamter2(String s) {
		this.parameters[2] = s;
	}

	public void setParamter3(String s) {
		this.parameters[3] = s;
	}

	public void setParamter4(String s) {
		this.parameters[4] = s;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionIp() {
		return actionIp;
	}

	public void setActionIp(String actionIp) {
		this.actionIp = actionIp;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParameter() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ParameterBlock; i++) {
			if (this.parameters[i] != null) {
				sb.append(this.parameters[i]);
			}
		}
		return sb.toString();
	}

	public void setParameter(String parameter) {
		if (parameter == null) {
			return;
		}
		for (int i = 0; i < ParameterBlock; i++) {
			int start = i * ParameterBlockSize;
			if (start > parameter.length()) {
				break;
			}
			int end = Math
					.min((i + 1) * ParameterBlockSize, parameter.length());
			this.parameters[i] = parameter.substring(start, end);
		}
	}
}
