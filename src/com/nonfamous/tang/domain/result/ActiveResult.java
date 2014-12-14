package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 * 激活会员结果返回
 * </p>
 * 
 * @author:daodao
 * @version $Id: ActiveResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class ActiveResult extends ResultBase {

	private static final long serialVersionUID = 7623393416413453518L;

	/** 校验码错误 */
	public static final String ERROR_INVALID_CHECK_CODE = "ERROR_INVALID_CHECK_CODE";

	/** 用户手机已激活 */
	public static final String ERROR_HAS_ACTIVE = "ERROR_HAS_ACTIVE";

	/** 没有该注册用户 */
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	/** 不允许更改，具体原因以后再扩展 */
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";

	@Override
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_INVALID_CHECK_CODE)) {
			return "校验码错误,请重新输入";
		} else if (StringUtils.equals(getErrorCode(), ERROR_HAS_ACTIVE)) {
			return "手机已经被激活";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_MEMBER)) {
			return "无该注册用户";
		}
		return super.getErrorMessage();
	}

}
