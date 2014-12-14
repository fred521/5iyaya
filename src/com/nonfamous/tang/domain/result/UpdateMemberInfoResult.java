package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * 修改会员信息返回结果类
 * </p>
 * 
 * @author:daodao
 * @version $Id: UpdateMemberInfoResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class UpdateMemberInfoResult extends ResultBase {

	private static final long serialVersionUID = 4356727719782634110L;

	/** 没有该注册会员 */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** 该会员已经被删除 */
	public static final String ERROR_MEMBER_DELETED = "ERROR_MEMBER_DELETED";

	/** 不允许更改，具体原因以后再扩展 */
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";

	/** 旧密码不正确 */
	public static final String ERROR_OLD_PASSWORD = "ERROR_OLD_PASSWORD";

	/** 旧手机号码不正确 */
	public static final String ERROR_OLD_MOBILE = "ERROR_OLD_MOBILE";

	/** 手机已经被别人使用 */
	public static final String ERROR_MOBILE_EXIST = "ERROR_MOBILE_EXIST";

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "无该注册会员";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MEMBER_DELETED)) {
			return "会员已经被删除，不允许作任何修改";
		} else if (StringUtils.equals(getErrorCode(), ERROR_OLD_PASSWORD)) {
			return "旧密码不正确，请重新输入";
		} else if (StringUtils.equals(getErrorCode(), ERROR_OLD_MOBILE)) {
			return "旧手机号码不正确，请重新输入";
		} else if (StringUtils.equals(getErrorCode(), ERROR_MOBILE_EXIST)) {
			return "该手机已经被别人注册使用，请换一个手机号";
		}
		return super.getErrorMessage();
	}
}
