package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.Member;

/**
 * <p>
 * 会员登录结果返回
 * </p>
 * 
 * @author:daodao
 * @version $Id: LoginResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class LoginResult extends ResultBase {

	private static final long serialVersionUID = -7170528761238487494L;

	/** 没有该注册用户 */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** 输入密码错误 */
	public static final String ERROR_PASS_ERROR = "ERROR_PASS_ERROR";

	/** 用户状态非正常（可能被禁用） */
	public static final String ERROR_MEMBER_STATUS = "ERROR_MEMBER_STATUS";

	// 登录会员信息
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_PASS_ERROR)
				|| StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "登录错误，会员帐号或者密码不正确";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_STATUS)) {
			return "当前状态非正常状态";
		}
		return super.getErrorMessage();
	}
}
