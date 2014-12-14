package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * 加入新会员处理结果类
 * </p>
 * 
 * @author:daodao
 * @version $Id: NewMemberResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class NewMemberResult extends ResultBase {

	private static final long serialVersionUID = -7581422513923152767L;

	/** 该用户名的用户已经存在 */
	public static final String ERROR_MEMBER_EXIST = "ERROR_MEMBER_EXIST";

	/** 该用户名的手机已经被注册过 */
	public static final String ERROR_MOBILE_EXIST = "ERROR_MOBILE_EXIST";

	/** 增加新会员后唯一标识该会员的id */
	public String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_EXIST)) {
			return "该用户名已经被别人注册使用,请换一个用户名";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MOBILE_EXIST)) {
			return "该手机已经被别人注册使用，请换一个手机号";
		}
		return super.getErrorMessage();
	}
}
